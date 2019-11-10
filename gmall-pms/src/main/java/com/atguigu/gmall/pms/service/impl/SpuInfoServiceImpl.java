package com.atguigu.gmall.pms.service.impl;

import com.atguigu.core.bean.QueryCondition;
import com.atguigu.gmall.pms.dao.*;
import com.atguigu.gmall.pms.entity.SkuImagesEntity;
import com.atguigu.gmall.pms.entity.SkuInfoEntity;
import com.atguigu.gmall.pms.entity.SkuSaleAttrValueEntity;
import com.atguigu.gmall.pms.entity.SpuInfoDescEntity;
import com.atguigu.gmall.pms.feign.GmallSmsClient;
import com.atguigu.gmall.pms.vo.ProductAttrValueVO;
import com.atguigu.gmall.pms.vo.SkuInfoVO;
import com.atguigu.gmall.pms.vo.SpuInfoVO;
import com.atguigu.gmall.sms.vo.SaleVO;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;

import com.atguigu.gmall.pms.entity.SpuInfoEntity;
import com.atguigu.gmall.pms.service.SpuInfoService;
import org.springframework.util.CollectionUtils;

@Log
@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescDao spuInfoDescDao;

    @Autowired
    private ProductAttrValueDao productAttrValueDao;

    @Autowired
    private SkuInfoDao skuInfoDao;

    @Autowired
    private SkuImagesDao skuImagesDao;

    @Autowired
    private AttrDao attrDao;

    @Autowired
    private SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Autowired
    private GmallSmsClient gmallSmsClient;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo querySpuInfo(QueryCondition condition, Long catId) {


        // 封装查询条件
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();

        // 如果分类id不为0，要根据分类id查，否则查全部
        if (catId != 0) {
            wrapper.eq("catalog_id", catId);
        }

        // 如果用户输入了检索条件，根据检索条件查
        String key = condition.getKey();
        if (StringUtils.isNotBlank(key)) {
            wrapper.and(t -> t.eq("id", key)).or().like("spu_name", key);
        }

        // 封装分页条件

        IPage<SpuInfoEntity> page = this.page(new Query<SpuInfoEntity>().getPage(condition), wrapper);

        return new PageVo(page);
    }

    @Override
    public void saveSpuInfoVO(SpuInfoVO spuInfoVO) {
        // 1.保存spu相关
        // 1.1. 保存spu基本信息 spu_info
        Long spuId = saveSpuInfo(spuInfoVO);

        // 1.2. 保存spu的描述信息 spu_info_desc
        // 注意：spu_info_desc表的主键是spu_id,需要在实体类中配置该主键不是自增主键
        saveSpuInfoDesc(spuInfoVO, spuId);

        // 1.3. 保存spu的规格参数信息
        saveBaseAttr(spuInfoVO, spuId);


        // 2. 保存sku相关信息
        saveSku(spuInfoVO, spuId);

        //发送消息
        sendMsg(spuId, "insert");

    }

    private void sendMsg(Long spuId, String type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", spuId);
        map.put("type", type);
        try {
            this.amqpTemplate.convertAndSend("GMALL-ITEM-EXCHANGE", "item." + type, map);
        } catch (AmqpException e) {
            System.out.println("商品消息发送异常，商品id：{ " + spuId + "}");
        }
    }

    private void saveSku(SpuInfoVO spuInfoVO, Long spuId) {
        List<SkuInfoVO> skus = spuInfoVO.getSkus();
        if (CollectionUtils.isEmpty(skus)) {
            return;
        }
        skus.forEach(skuInfoVO -> {
            // 2.1. 保存sku基本信息
            SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
            BeanUtils.copyProperties(skuInfoVO, skuInfoEntity);
            skuInfoEntity.setSpuId(spuId);
            // 获取随机的uuid作为sku的编码
            skuInfoEntity.setSkuCode(UUID.randomUUID().toString());
            // 品牌和分类的id需要从spuInfo中获取
            skuInfoEntity.setCatalogId(spuInfoVO.getCatalogId());
            skuInfoEntity.setBrandId(spuInfoVO.getBrandId());
            // 获取图片列表,并设置第一张图片为默认图片
            List<String> images = skuInfoVO.getImages();
            if (!CollectionUtils.isEmpty(images)) {
                skuInfoEntity.setSkuDefaultImg(skuInfoEntity.getSkuDefaultImg() == null ? images.get(0) : skuInfoEntity.getSkuDefaultImg());
            }
            this.skuInfoDao.insert(skuInfoEntity);
            // 获取skuId
            Long skuId = skuInfoEntity.getSkuId();

            // 2.2. 保存sku图片信息
            if (!CollectionUtils.isEmpty(images)) {
                images.forEach(image -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setDefaultImg(StringUtils.equals(image, skuInfoEntity.getSkuDefaultImg()) ? 1 : 0);
                    skuImagesEntity.setImgSort(0);
                    skuImagesEntity.setImgUrl(image);
                    this.skuImagesDao.insert(skuImagesEntity);
                });
            }

            // 2.3. 保存sku的规格参数（销售属性）
            List<SkuSaleAttrValueEntity> saleAttrs = skuInfoVO.getSaleAttrs();
            saleAttrs.forEach(saleAttr -> {
                saleAttr.setSkuId(skuId);
                saleAttr.setAttrSort(0);
                // 设置属性名，需要根据id查询AttrEntity
                saleAttr.setAttrName(this.attrDao.selectById(saleAttr.getAttrId()).getAttrName());
                this.skuSaleAttrValueDao.insert(saleAttr);
            });

            // 3. 保存营销相关信息，需要远程调用gmall-sms
            // 3.1. 积分优惠
            // 3.2. 满减优惠
            // 3.3. 数量折扣
            SaleVO saleVO = new SaleVO();
            BeanUtils.copyProperties(skuInfoVO, saleVO);
            saleVO.setSkuId(skuId);
            this.gmallSmsClient.saveSkuSaleInfo(saleVO);
        });
    }

    private void saveBaseAttr(SpuInfoVO spuInfoVO, Long spuId) {
        List<ProductAttrValueVO> baseAttrs = spuInfoVO.getBaseAttrs();
        if (!CollectionUtils.isEmpty(baseAttrs)) {
            baseAttrs.forEach(baseAttr -> {
                baseAttr.setSpuId(spuId);
                baseAttr.setAttrSort(0);
                baseAttr.setQuickShow(1);
                this.productAttrValueDao.insert(baseAttr);
            });
        }
    }

    private void saveSpuInfoDesc(SpuInfoVO spuInfoVO, Long spuId) {
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuId);
        // 把商品的图片描述，保存到spu详情中，图片地址以逗号进行分割
        spuInfoDescEntity.setDecript(StringUtils.join(spuInfoVO.getSpuImages(), ","));
        this.spuInfoDescDao.insert(spuInfoDescEntity);
    }

    private Long saveSpuInfo(SpuInfoVO spuInfoVO) {
        spuInfoVO.setPublishStatus(1);// 默认是已上架
        spuInfoVO.setCreateTime(new Date());
        spuInfoVO.setUodateTime(spuInfoVO.getCreateTime());
        this.save(spuInfoVO);
        return spuInfoVO.getId();
    }


}
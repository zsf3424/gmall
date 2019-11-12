package com.atguigu.gmall.item.service.impl;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.item.feign.GmallPmsClient;
import com.atguigu.gmall.item.feign.GmallSmsClient;
import com.atguigu.gmall.item.feign.GmallWmsClient;
import com.atguigu.gmall.item.service.ItemService;
import com.atguigu.gmall.item.vo.ItemVO;
import com.atguigu.gmall.pms.api.GmallPmsApi;
import com.atguigu.gmall.pms.entity.BrandEntity;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.entity.SkuInfoEntity;
import com.atguigu.gmall.pms.entity.SkuSaleAttrValueEntity;
import com.atguigu.gmall.pms.entity.SpuInfoDescEntity;
import com.atguigu.gmall.pms.entity.SpuInfoEntity;
import com.atguigu.gmall.pms.vo.GroupVO;
import com.atguigu.gmall.sms.api.GmallSmsApi;
import com.atguigu.gmall.sms.vo.ItemSaleVO;
import com.atguigu.gmall.wms.api.GmallWmsApi;
import com.atguigu.gmall.wms.entity.WareSkuEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zsf
 * @create 2019-11-12 16:21
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private GmallPmsClient gmallPmsClient;
    @Autowired
    private GmallWmsClient gmallWmsClient;
    @Autowired
    private GmallSmsClient gmallSmsClient;

    public ItemVO item(Long skuId) {

        ItemVO itemVO = new ItemVO();

        // 1. 查询sku信息
        Resp<SkuInfoEntity> skuInfoEntityResp = this.gmallPmsClient.querySkuById(skuId);
        SkuInfoEntity skuInfoEntity = skuInfoEntityResp.getData();
        BeanUtils.copyProperties(skuInfoEntity, itemVO);
        Long spuId = skuInfoEntity.getSpuId();

        // 2.品牌
        Resp<BrandEntity> brandEntityResp = this.gmallPmsClient.queryBrandById(skuInfoEntity.getBrandId());
        itemVO.setBrand(brandEntityResp.getData());

        // 3.分类
        Resp<CategoryEntity> categoryEntityResp = this.gmallPmsClient.queryCategoryById(skuInfoEntity.getCatalogId());
        itemVO.setCategory(categoryEntityResp.getData());

        // 4.spu信息
        Resp<SpuInfoEntity> spuInfoEntityResp = this.gmallPmsClient.querySpuById(spuId);
        itemVO.setSpuInfo(spuInfoEntityResp.getData());

        // 5.设置图片信息
        Resp<List<String>> picsResp = this.gmallPmsClient.queryPicsBySkuId(skuId);
        itemVO.setPics(picsResp.getData());

        // 6.营销信息
        Resp<List<ItemSaleVO>> itemSaleResp = this.gmallSmsClient.queryItemSaleVOs(skuId);
        itemVO.setSales(itemSaleResp.getData());

        // 7.是否有货
        Resp<List<WareSkuEntity>> wareSkuResp = this.gmallWmsClient.queryWareSkuBySkuId(skuId);
        List<WareSkuEntity> wareSkuEntities = wareSkuResp.getData();
        itemVO.setStore(wareSkuEntities.stream().anyMatch(t -> t.getStock() > 0));

        // 8.spu所有的销售属性
        Resp<List<SkuSaleAttrValueEntity>> saleAttrValueResp = this.gmallPmsClient.querySaleAttrValues(spuId);
        itemVO.setSkuSales(saleAttrValueResp.getData());

        // 9.spu的描述信息
        Resp<SpuInfoDescEntity> spuInfoDescEntityResp = this.gmallPmsClient.queryDescById(spuId);
        itemVO.setDesc(spuInfoDescEntityResp.getData());

        // 10.规格属性分组及组下的规格参数及值
        Resp<List<GroupVO>> listResp = this.gmallPmsClient.queryGroupVoByCid(skuInfoEntity.getCatalogId(), spuId);
        itemVO.setGroups(listResp.getData());

        return itemVO;
    }
}

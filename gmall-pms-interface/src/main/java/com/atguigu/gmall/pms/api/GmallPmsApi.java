package com.atguigu.gmall.pms.api;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.entity.BrandEntity;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.entity.SkuInfoEntity;
import com.atguigu.gmall.pms.vo.SpuAttributeValueVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author zsf
 * @create 2019-11-05 18:02
 */
public interface GmallPmsApi {

    //分页查询spu
    //@ApiOperation("分页查询(排序)")
    @GetMapping("pms/spuinfo/list")
    public Resp<PageVo> list(QueryCondition queryCondition);

    //根据spuId查询sku
    //@ApiOperation("查询spu下的sku")
    @GetMapping("pms/skuinfo/{spuId}")
    public Resp<List<SkuInfoEntity>>  querySkuBySpuId(@PathVariable("spuId") Long spuId);

    //根据brandId查询品牌
    //@ApiOperation("详情查询")
    @GetMapping("pms/brand/info/{brandId}")
    public Resp<BrandEntity> queryBrandById(@PathVariable("brandId") Long brandId);

    //根据categoryId查询分类
    //@ApiOperation("详情查询")
    @GetMapping("pms/category/info/{catId}")
    public Resp<CategoryEntity> categoryById(@PathVariable("catId") Long catId);

    //根据spuId查询检索属性
    //@ApiOperation("根据spuId查询检索属性及值")
    @GetMapping("pms/productattrvalue/{spuId}")
    public Resp<List<SpuAttributeValueVO>> querySearchAttrValue(@PathVariable("spuId")Long spuId);




}

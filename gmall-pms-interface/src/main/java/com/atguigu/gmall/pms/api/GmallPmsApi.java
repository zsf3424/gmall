package com.atguigu.gmall.pms.api;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.entity.BrandEntity;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.entity.SkuInfoEntity;
import com.atguigu.gmall.pms.entity.SkuSaleAttrValueEntity;
import com.atguigu.gmall.pms.entity.SpuInfoDescEntity;
import com.atguigu.gmall.pms.entity.SpuInfoEntity;
import com.atguigu.gmall.pms.vo.CategroyVO;
import com.atguigu.gmall.pms.vo.GroupVO;
import com.atguigu.gmall.pms.vo.SpuAttributeValueVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zsf
 * @create 2019-11-05 18:02
 */
public interface GmallPmsApi {

    //根据skuId查询sku的销售属性
    @GetMapping("pms/skusaleattrvalue/sku/{skuId}")
    public Resp<List<SkuSaleAttrValueEntity>> querySaleAttrBySkuId(@PathVariable("skuId") Long skuId);

    //分页查询spu
    //@ApiOperation("分页查询(排序)")
    @PostMapping("pms/spuinfo/list")
    public Resp<List<SpuInfoEntity>> querySpuPage(@RequestBody QueryCondition queryCondition);


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
    public Resp<CategoryEntity> queryCategoryById(@PathVariable("catId") Long catId);

    //根据spuId查询检索属性
    //@ApiOperation("根据spuId查询检索属性及值")
    @GetMapping("pms/productattrvalue/{spuId}")
    public Resp<List<SpuAttributeValueVO>> querySearchAttrValue(@PathVariable("spuId")Long spuId);

    //@ApiOperation("根据分类等级或者父id查询分类")
    @GetMapping("pms/category")
    public Resp<List<CategoryEntity>> queryCategory(@RequestParam(value = "level",defaultValue = "0") Integer level, @RequestParam(value = "parentCid",required = false) Long parentCid);

    //@ApiOperation("根据一级分类id查询二级、三级分类")
    @GetMapping("pms/category/{pid}")
    public Resp<List<CategroyVO>> queryCategroyWithSub(@PathVariable("pid")Long pid);

    //根据skuId查询skuInfo
    //@ApiOperation("详情查询")
    @GetMapping("pms/skuinfo/info/{skuId}")
    public Resp<SkuInfoEntity> querySkuById(@PathVariable("skuId") Long skuId);

    //根据spuId查询spuInfo
    //@ApiOperation("详情查询")
    @GetMapping("pms/spuinfo/info/{id}")
    public Resp<SpuInfoEntity> querySpuById(@PathVariable("id") Long id);

    //根据skuId查询图片集合
    @GetMapping("pms/skuimages/{skuId}")
    public Resp<List<String>> queryPicsBySkuId(@PathVariable("skuId")Long skuId);

    //根据spuId查询销售属性集合
    @GetMapping("pms/skusaleattrvalue/{spuId}")
    public Resp<List<SkuSaleAttrValueEntity>> querySaleAttrValues(@PathVariable("spuId") Long spuId);

    //根据spuId查询描述信息
    //@ApiOperation("详情查询")
    @GetMapping("pms/spuinfodesc/info/{spuId}")
    public Resp<SpuInfoDescEntity> queryDescById(@PathVariable("spuId") Long spuId);

    //根据spuId和cid查询
    @GetMapping("pms/attrgroup/item/group/{cid}/{spuId}")
    public Resp<List<GroupVO>> queryGroupVoByCid(@PathVariable("cid")Long cid, @PathVariable("spuId")Long spuId);


    }

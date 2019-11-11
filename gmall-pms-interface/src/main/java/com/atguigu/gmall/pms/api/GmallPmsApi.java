package com.atguigu.gmall.pms.api;

import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;
import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.pms.entity.BrandEntity;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.entity.SkuInfoEntity;
import com.atguigu.gmall.pms.entity.SpuInfoEntity;
import com.atguigu.gmall.pms.vo.CategroyVO;
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

    }

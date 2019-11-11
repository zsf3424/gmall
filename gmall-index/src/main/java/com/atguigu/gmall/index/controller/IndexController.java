package com.atguigu.gmall.index.controller;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.index.service.IndexService;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.vo.CategroyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zsf
 * @create 2019-11-10 15:21
 */
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping("cates")
    public Resp<List<CategoryEntity>> queryLevelFirstCategroy(){

        List<CategoryEntity> categoryEntities = this.indexService.queryLevelFirstCategroy();

        return Resp.ok(categoryEntities);
    }

    @GetMapping("cates/{pid}")
    public Resp<List<CategroyVO>> queryCategroyVO(@PathVariable("pid")Long pid){

        List<CategroyVO> categroyVOS = this.indexService.queryCategroyVO(pid);

        return Resp.ok(categroyVOS);
    }


}

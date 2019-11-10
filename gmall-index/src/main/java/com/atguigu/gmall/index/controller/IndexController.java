package com.atguigu.gmall.index.controller;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.index.service.IndexService;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author zsf
 * @create 2019-11-10 15:21
 */
@Controller
@RequestMapping("index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    public Resp<List<CategoryEntity>> queryLevelFirstCategroy(){

        List<CategoryEntity> categoryEntities = this.indexService.queryLevelFirstCategroy();

        return Resp.ok(categoryEntities);
    }
}

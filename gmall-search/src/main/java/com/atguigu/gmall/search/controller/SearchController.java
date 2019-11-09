package com.atguigu.gmall.search.controller;


import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.search.service.SearchService;
import com.atguigu.gmall.search.vo.SearchParamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zsf
 * @create 2019-11-08 19:22
 */
@RequestMapping("search")
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public Resp<Object> search(SearchParamVO searchParamVO){

        this.searchService.search(searchParamVO);

        return Resp.ok(null);
    }

}

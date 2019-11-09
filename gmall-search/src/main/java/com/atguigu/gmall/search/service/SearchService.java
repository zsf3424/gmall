package com.atguigu.gmall.search.service;

import com.atguigu.gmall.search.vo.SearchParamVO;
import com.atguigu.gmall.search.vo.SearchResponse;

/**
 * @author zsf
 * @create 2019-11-08 19:28
 */
public interface SearchService {

    public SearchResponse search(SearchParamVO searchParamVO);

}

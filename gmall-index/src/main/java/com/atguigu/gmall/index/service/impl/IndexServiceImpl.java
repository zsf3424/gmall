package com.atguigu.gmall.index.service.impl;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.index.feign.GmallPmsClient;
import com.atguigu.gmall.index.service.IndexService;
import com.atguigu.gmall.pms.entity.CategoryEntity;
import com.atguigu.gmall.pms.vo.CategroyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zsf
 * @create 2019-11-10 15:25
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private GmallPmsClient gmallPmsClient;

    @Override
    public List<CategoryEntity> queryLevelFirstCategroy() {

        Resp<List<CategoryEntity>> listResp = this.gmallPmsClient.queryCategory(1, null);

        return listResp.getData();
    }

    @Override
    public List<CategroyVO> queryCategroyVO(Long pid) {

        Resp<List<CategroyVO>> listResp = this.gmallPmsClient.queryCategroyWithSub(pid);

        return listResp.getData();
    }
}

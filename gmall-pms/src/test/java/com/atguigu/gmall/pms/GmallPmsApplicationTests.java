package com.atguigu.gmall.pms;

import com.atguigu.gmall.pms.dao.BrandDao;
import com.atguigu.gmall.pms.entity.BrandEntity;
import com.atguigu.gmall.pms.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class GmallPmsApplicationTests {

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private BrandService brandService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testMybatis(){

        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setDescript("尚硅谷真好");
        brandEntity.setLogo("www.baidu.com/log.gif");
        brandEntity.setName("尚硅谷");
        brandEntity.setFirstLetter("S");
        brandEntity.setShowStatus(0);
        brandEntity.setSort(1);
        this.brandDao.insert(brandEntity);
    }




}

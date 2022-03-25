package com.kaxin.qkcustomermanage;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kaxin.qkcustomermanage.entity.VO.QkDeptVO;
import com.kaxin.qkcustomermanage.entity.VO.QkUserVO;
import com.kaxin.qkcustomermanage.service.QkCustmInfoService;
import com.kaxin.qkcustomermanage.service.QkCustomerService;
import com.kaxin.qkcustomermanage.service.QkUserService;
import com.kaxin.qkcustomermanage.util.RecursionUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class KxQkCustomerManageApplicationTests {

    @Autowired
    private QkUserService qkUserService;
    @Autowired
    private QkCustmInfoService qkCustmInfoService;

    @Test
    void contextLoads() {
        qkCustmInfoService.allocation();
    }

}

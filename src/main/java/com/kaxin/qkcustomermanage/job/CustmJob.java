package com.kaxin.qkcustomermanage.job;

import com.kaxin.qkcustomermanage.service.QkCustmInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 功能描述
 *
 * @author tangdj
 * @date 2022/3/25
 */
@Component
public class CustmJob {

    @Autowired
    private QkCustmInfoService qkCustmInfoService;

    @Scheduled(cron = "0 */5 * * * ?")
    public void allocationCustm() {
        qkCustmInfoService.allocation();
    }
}

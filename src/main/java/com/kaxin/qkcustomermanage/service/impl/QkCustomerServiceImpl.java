package com.kaxin.qkcustomermanage.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kaxin.qkcustomermanage.constant.enums.SystemCodeEnum;
import com.kaxin.qkcustomermanage.entity.DTO.QkCustctrolDTO;
import com.kaxin.qkcustomermanage.entity.DTO.QkCustomerInfoDTO;
import com.kaxin.qkcustomermanage.entity.DTO.QkFollowerDTO;
import com.kaxin.qkcustomermanage.exception.KaXinException;
import com.kaxin.qkcustomermanage.exception.QianKeException;
import com.kaxin.qkcustomermanage.service.QkCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述
 *
 * @author tangdj
 * @date 2022/3/24
 */
@Service
@Slf4j
public class QkCustomerServiceImpl implements QkCustomerService {

    private static final String QC_REQUEST_URL = "https://api.saas-work.cn/cgi-bin";

    private static String accessToken;

    private static String getAccessToken() {
        if (accessToken != null) {
            return accessToken;
        }
        try {
            String result = HttpUtil.get(QC_REQUEST_URL + "/oauth/access_token?appid=65616&did=1426215&secret=57b5ed49619e4895", 5000);
            log.info("仟客获取access_token接口查询结果：{}", result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            Long resultCode = jsonObject.getLong("result");
            if (resultCode != 0) {
                String resultMsg = jsonObject.getString("errmsg");
                throw new QianKeException(Math.toIntExact(resultCode), resultMsg);
            }
            accessToken = jsonObject.getString("access_token");
            return accessToken;
        } catch (Exception e) {
            log.error("仟客获取access_token接口调用失败：{}", e.getMessage());
            throw new QianKeException(String.format("仟客接口调用异常：%s", e.getMessage()));
        }
    }

    private static JSONObject parseResult(String result) {
        JSONObject jsonObject = JSONObject.parseObject(result);
        Long resultCode = jsonObject.getLong("result");
        if (resultCode != 0) {
            String resultMsg = jsonObject.getString("errmsg");
            throw new QianKeException(Math.toIntExact(resultCode), resultMsg);
        }
        return jsonObject;
    }

    /**
     * 查询部门信息
     *
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject queryDeptInfo() {
        String accessToken = getAccessToken();
        String result;
        try {
            Map<String, Object> params = new HashMap<>(1);
            params.put("department_id", 1);
            params.put("fetch_child", 1);
            log.info("查询客户列表接口参数：{}", params);
            HttpRequest request = HttpUtil.createGet(QC_REQUEST_URL + "/roster/department/list?access_token=" + accessToken);
            request.form(params);
            request.timeout(5000);
            HttpResponse response = request.execute();
            result = response.body();
        } catch (Exception e) {
            log.error("仟客查询部门信息接口调用失败：{}", e.getMessage());
            throw new QianKeException(String.format("仟客接口调用异常：%s", e.getMessage()));
        }
        log.info("仟客查询部门信息接口结果：{}", result);
        return parseResult(result);
    }

    /**
     * 查询部门员工信息
     *
     * @param deptId 部门ID
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject queryDeptEmpInfo(Integer deptId) {
        String accessToken = getAccessToken();
        String result;
        try {
            Map<String, Object> params = new HashMap<>(1);
            params.put("department_id", deptId);
            params.put("fetch_child", 1);
            log.info("仟客查询部门员工信息接口参数：{}", params);
            HttpRequest request = HttpUtil.createGet(QC_REQUEST_URL + "/roster/department/get_member?access_token=" + accessToken);
            request.form(params);
            request.timeout(5000);
            HttpResponse response = request.execute();
            result = response.body();
        } catch (Exception e) {
            log.error("仟客查询部门员工信息接口调用失败：{}", e.getMessage());
            throw new QianKeException(String.format("仟客接口调用异常：%s", e.getMessage()));
        }
        log.info("仟客查询部门员工信息接口结果：{}", result);
        return parseResult(result);
    }

    @Override
    public JSONObject queryCustmInfoList(int start, int count) {
        String accessToken = getAccessToken();
        String result;
        try {
            Map<String, Object> params = new HashMap<>(2);
            params.put("start", start);
            params.put("count", count);
            log.info("查询客户列表接口地址：{}，参数：{}", QC_REQUEST_URL + "/customer/export?access_token=" + accessToken, params);
            result = HttpUtil.get(QC_REQUEST_URL + "/customer/export?access_token=" + accessToken, params, 5000);
        } catch (Exception e) {
            log.error("仟客查询客户信息接口调用失败：{}", e.getMessage());
            throw new QianKeException(String.format("仟客接口调用异常：%s", e.getMessage()));
        }
        return parseResult(result);

    }

    /**
     * 修改客户跟进人
     *
     * @param custmId   客户ID
     * @param pid       跟进人ID（修改人）
     * @param followers 跟进人列表
     * @return void
     */
    @Override
    public void updateCustmFollowerId(Integer custmId, Integer pid, List<QkFollowerDTO> followers) {
        QkCustomerInfoDTO qkCustomerInfoDTO = new QkCustomerInfoDTO();
        qkCustomerInfoDTO.setFollowers(followers);
        qkCustomerInfoDTO.setCustmid(custmId);
        String accessToken = getAccessToken();
        String result;
        try {
            Map<String, Object> params = new HashMap<>(3);
            params.put("custm", qkCustomerInfoDTO);
            params.put("custctrol", new QkCustctrolDTO());
            params.put("op_pid", pid);
            String body = JSON.toJSONString(params);
            log.info("仟客修改客户跟进人接口查询参数：{}", body);
            HttpRequest request = HttpUtil.createPost(QC_REQUEST_URL + "/customer/modify?access_token=" + accessToken);
            request.body(body);
            request.timeout(5000);
            HttpResponse response = request.execute();
            result = response.body();
        } catch (Exception e) {
            log.error("仟客修改客户跟进人接口调用失败：{}", e.getMessage());
            throw new QianKeException(String.format("仟客接口调用异常：%s", e.getMessage()));
        }
        log.info("仟客修改客户跟进人接口结果：{}", result);
        parseResult(result);
    }


}

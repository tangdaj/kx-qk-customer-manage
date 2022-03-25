package com.kaxin.qkcustomermanage.constant;

import java.io.Serializable;

/**
 * 默认配置常量信息
 *
 * @author tangdj
 */
public class SystemConstants implements Serializable {

    /**
     * 项目版本
     */
    public static final String PROJECT_VERSION = "1.0.0";

    /**
     * 默认分隔符
     */
    public static final String SEPARATOR = ",";

    /**
     * 查询数据权限递归次数,可以通过继承这个类修改
     */
    public static final int AUTH_DATA_RECURSION_NUM = 20;

    /**
     * 业务token在header中的名称
     */
    public static final String TOKEN_NAME = "Authorization";

    /**
     * 默认编码
     */
    public static final String DEFAULT_CONTENT_TYPE = "application/json;charset=UTF-8";

    /**
     * sql最大查询条数限制,以及字段数量限制
     */
    public static final Integer QUERY_MAX_SIZE = 100;

    /**
     * PC端登录的userKey
     */
    public static final String USER_TOKEN = "KX:USER:TOKEN:";

    /**
     * 用户token的最长过期时间
     */
    public static final Integer MAX_USER_EXIST_TIME = 3600 * 24 * 7;

    /**
     * 导入最大条数
     */
    public static final int SAVE_MAX_SIZE = 5000;

    /**
     * 批量保存的条数
     */
    public static final int BATCH_SAVE_SIZE = 200;

    /**
     * 企业信息缓存KEY
     */
    public static final String COMPANY_MANAGE_CACHE_NAME = "COMPANY:MANAGE:STATUS:";

    /**
     * 用户名称缓存key
     */
    public static final String ADMIN_USER_NAME_CACHE_NAME = "ADMIN:USER:CACHE:";

    /**
     * 部门名称缓存key
     */
    public static final String ADMIN_DEPT_NAME_CACHE_NAME = "ADMIN:DEPT:CACHE:";

    /**
     * 默认的企业人数
     */
    public static final Integer DEFAULT_COMPANY_NUMBER = 2;

    /**
     * 系统默认密码
     */
    public static final String INIT_PWD = "123456";

    /**
     * 卡信公司ID
     */
    public static final Integer KAXIN_ID = 0;
    /**
     * 默认的上传文件路径
     */
    public final static String UPLOAD_PATH = isWindows() ? "D:\\upload\\" : "/usr/local/upload/";

    /**
     * 获取当前是否是windows系统
     *
     * @return true代表为真
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

}

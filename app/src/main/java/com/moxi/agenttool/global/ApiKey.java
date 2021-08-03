package com.moxi.agenttool.global;

/**
 * @Package: com.benwunet.base.global
 * @ClassName: ApiKey
 * @Description: api接口常量
 * @Author: feng wen jun
 * @Since: 2020/10/20 0020 17:41
 * @Version: 1.0
 */

public interface ApiKey {

    /**
     * user/sms/send/{mobile}/{smsType}
     * 发送短信验证码
     */
    String USER_SMS_SEND = "user/sms/send";
    /**
     * 验证码登陆接口
     */
    String USER_lOGIN_CODE = "user/login/code";

    /**
     * 查询重点客户
     */
    String CILENT_QUERY_IMPORTANT = "client/query/important";

    /**
     * 过滤客户的历史房源
     */
    String SEARCH_FILTER_CLIENT_HOUSE_HISTORY = "search/filter/client/house/history";


    /**
     *
     * 新增客户收藏房源
     */
    String HOUSE_COLLOECTION_INSERT = "house/collection/insert";

    /**
     * 查询客户的收藏房源
     */
    String HOUSE_COLLOECTION_QUERY = "house/collection/query";

    /**
     * 根据ID集合删除房源收藏记录
     */
    String HOUSE_DELETE = "house/delete";

    /**
     * 新增客户历史房源
     */
    String HOUSE_HISTORY_INSERT= "house/history/insert";


    /**
     * 查询客户历史房源
     */
    String SEARCH_HOUSE_HISTORY= "search/house/history";

    /**
     * 查询用户信息
     */
    String USER_USERIFO= "user/userInfo";

    /**
     * 新增客户
     */
    String CLIENT_INSERT= "client/insert";


}

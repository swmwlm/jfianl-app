package com.langmy.jFinal.common;

import com.langmy.jFinal.model.SysConfig;

/**
 * Created by wangrenhui on 13-12-31.
 */
public class AppConstants {
  //默认字符编码
  public static final String DEFAULT_ENCODING = "UTF-8";
  //数据源
  public static final String DEFAULT_DATESOURCE = "db/migration/default";
  //验证码
  public static final String CAPTCHA_NAME = "captcha";
  //保存用户信息
  public static final String LOGIN_USER_NAME = "username";

  public static final String TEMP_USER = "tempUser";

  public static final String CURRENT_USER = "currentUser";


  // 接口返回状态码
  public static final String CODE_SUCCESS = "200";
  public static final String CODE_FAILURE = "201";

  // 接口返回描述
  public static final String DESC_SUCCESS = "success";
  public static final String DESC_FAILURE = "error";
  public static final String OP_ERROR_MESSAGE = "非法操作";
  public static final String DELETE_FAILURE = "删除失败";

  // http请求方式
  public static final String GET = "get";
  public static final String POST = "post";



  public static final String DEFAULT_CACHENAME = "defaultCache";

  public static final String SESSION_ADMIN_USERNAME = "admin_username";
  //sysconfig
  public static final String SYSCONFIGCACHEKEY = "sysconfigcachekey";



  // 缓存名称
  public static final String SECTIONCACHE = "sectioncache";
  public static final String LINKCACHE = "linkcache";
  public static final String SYSCONFIGCACHE = "sysconfigcache";
  public static final String TOPICCACHE = "topiccache";
  public static final String SHIROCACHE = "shirocache";

  // 缓存KEY
  // section
  public static final String SECTIONLISTKEY = "sectionlistkey";
  public static final String SECTIONBYTABKEY = "sectionbytabkey";
  public static final String SECTIONSHOWLISTKEY = "sectionshowlistkey";
  public static final String DEFAULTSECTIONKEY = "defaultsectionkey";
  //role
  public static final String ROLECACHEKEY = "rolecachekey";
  public static final String PERMISSIONCACHEKEY = "permissioncachekey";

  public static String getValue(String key) {
    return SysConfig.dao.findByKey(key);
  }
}

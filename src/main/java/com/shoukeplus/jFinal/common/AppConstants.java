package com.shoukeplus.jFinal.common;

import com.shoukeplus.jFinal.common.model.SysConfig;

public class AppConstants {
  //默认字符编码
  public static final String DEFAULT_ENCODING = "UTF-8";
  //验证码
  public static final String CAPTCHA_NAME = "captcha";
  //保存用户信息
  public static final String LOGIN_USER_NAME = "username";

  public static final String TEMP_USER = "tempUser";

  public static final String CURRENT_USER = "currentUser";


  // 系统变量KEY
//    public static final String UPLOAD_DIR = "/home/www/jfinalbbs/static/upload";
  //public static final String UPLOAD_DIR = PathKit.getWebRootPath() + "/static/upload";
  public static final String IMG_HOSTURL="http://localhost:8080/uploads";
  public static final String UPLOAD_DIR= "/Users/wangguanping/sk/JFinalLearn-new-upload";
  public static final String UPLOAD_DIR_AVATAR = "avatar";
  public static final String UPLOAD_DIR_LABEL = "label";
  public static final String UPLOAD_DIR_LINK = "link";
  public static final String UPLOAD_DIR_NEWS = "news";
  public static final String UPLOAD_DIR_EDITOR = "editor";
  //上传异常
  public static final String UPLOAD_ERROR_MESSAGE = "非法路径参数";

  public static final String NOTIFICATION_MESSAGE = "有人@你";
  public static final String NOTIFICATION_MESSAGE1 = "回复了你的话题";
  public static final String NOTIFICATION_PRIVATE_MESSAGE = "给你发了一条私信";


  // COOKIE SESSION变量KEY
  public static final String USER_COOKIE = "user_token_v2";
  public static final String USER_SESSION = "user";
  public static final String BEFORE_URL = "before_url";
  public static final String ADMIN_BEFORE_URL = "admin_before_url";
  public static final String COOKIE_ADMIN_TOKEN = "admin_user_token";

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
  //link
  public static final String LINKLISTKEY = "linklistkey";
  //sysconfig
  public static final String SYSCONFIGCACHEKEY = "sysconfigcachekey";
  //topic
  public static final String TOPICCACHEKEY = "topiccachekey";



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

  // 第三方
  public static final String QQ = "qq";
  public static final String SINA = "sina";

  // 验证码类型
  public static final String FORGET_PWD = "forgetpwd";
  public static final String REG = "reg";

  public static String getValue(String key) {
    return SysConfig.dao.findByKey(key);
  }
}

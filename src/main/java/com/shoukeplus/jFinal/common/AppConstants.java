package com.shoukeplus.jFinal.common;

import com.shoukeplus.jFinal.common.model.SysConfig;

public class AppConstants {
  //默认字符编码
  public static final String DEFAULT_ENCODING = "UTF-8";

  public static final String CURRENT_USER = "currentUser";
  public static final String CURRENT_ADMIN_USER = "currrent_admin_user";
  public static final String SESSION_FORCE_LOGOUT_KEY = "session.force.logout";

  //CSRF TOKEN
  public static final String CSRF_TOKEN="token";

  // 系统变量KEY
  //public static final String UPLOAD_DIR = PathKit.getWebRootPath() + "/static/upload";

  public static final String UPLOAD_DIR_AVATAR = "avatar";
  public static final String UPLOAD_DIR_LABEL = "label";
  public static final String UPLOAD_DIR_LINK = "link";
  public static final String UPLOAD_DIR_CONTENT = "content";
  public static final String UPLOAD_DIR_ROLLIMAGES = "rollimages";
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


  public static final String DEFAULT_CACHE_NAME = "defaultCache";

  //link
  public static final String LINKLISTKEY = "linklistkey";
  //sysconfig
  public static final String SYSCONFIGCACHEKEY = "sysconfigcachekey";
  //topic
  public static final String TOPICCACHEKEY = "topiccachekey";

  //action cache
  public static final String ACTION_CACHE_NAME="actionCache";

  // 缓存名称
  public static final String SECTIONCACHE = "sectioncache";
  public static final String LINKCACHE = "linkCache";
  public static final String SYSCONFIGCACHE = "sysconfigcache";
  public static final String TOPICCACHE = "topiccache";
  public static final String SHIROCACHE = "shirocache";
  public static final String CURRENTADMINUSERCACHE = "currentadminusercache";

  // 缓存KEY
  // section
  public static final String SECTIONLISTKEY = "sectionlistkey";
  public static final String SECTIONBYTABKEY = "sectionbytabkey";
  public static final String SECTIONSHOWLISTKEY = "sectionshowlistkey";
  public static final String DEFAULTSECTIONKEY = "defaultsectionkey";
  public static final String CURRENTADMINUSERCACHEKEY = "currentadminusercachekey";
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

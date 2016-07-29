package com.test.model;

import com.jfinal.kit.JsonKit;
import com.shoukeplus.jFinal.common.model.AdminUser;
import com.test.common.DBInit;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserTest {

  @Before
  public void setUp() throws Exception {
    new DBInit().init();
  }

  @Test
  public void testFindInfoBy() throws Exception {
    List<AdminUser> adminUsers=AdminUser.dao.find("select * from sk_admin_user");
    //System.out.println(JsonKit.toJson(u));
    System.out.println(JsonKit.toJson(adminUsers));
//    PropertiesKit.me().loadPropertyFile("application.properties");
//    PropertiesKit.me().loadPropertyFile("application.properties");
//    System.out.println(JSON.toJSONString(u));
//    Assert.assertNotNull(u);
//    System.out.println(PathKit.getRootClassPath());
//    System.out.println(UserTest.class.getResource("application.properties"));

//    Enumeration<URL> urls = UserTest.class.getClassLoader().getResources("/quartz/quartz.properties");
//    while (urls.hasMoreElements()) {
//      URL url = urls.nextElement();
//      System.out.println(url.getPath());
//    }
//    System.out.println("--"+UserTest.class.getResource("/db/migration"));
//    Mailer.sendHtml("测试", "<a href='www.dreampie.cn'>Dreampie</a>", "173956022@qq.com");
//    Mailer.me().sendHtml("Dreampie.cn-梦想派",
//        MailerTemplate.me().set("full_name", "先生/女士").set("safe_url", "/aa")
//            .getText("mails/signup_email.ftl"), "173956022@qq.com");
//    System.out.println(PathKit.getWebRootPath());

//    System.out.println(URLDecoder.decode("D:\\Program%20Files", "UTF-8"));

  }
}
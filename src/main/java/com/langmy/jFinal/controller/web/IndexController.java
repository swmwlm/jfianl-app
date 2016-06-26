package com.langmy.jFinal.controller.web;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheName;
import com.langmy.jFinal.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class IndexController extends Controller{
	
	public static Logger LOG  = LoggerFactory.getLogger(IndexController.class);
	
	public void index(){
		//renderFreeMarker("/index.html");
//		List<ZcUser> users = ZcUser.dao.find("select * from zc_user");

		//      List<User> users = User.dao.find("select * from user where id=?",2);
		//分页查询
//      Page<User> users = User.dao.paginate(1, 10, "select *","from user where id=?",2);
//		for(ZcUser user :users)
//			System.out.println(user.toString()+user.getStr("name"));

		//新增一条User数据
        /*User user = new User();
        user.set("user_acc", "accNew");
        user.set("name", "nameNew");
        boolean flag = user.save();
        System.out.println(flag);*/


//		List<User> users=User.dao.find("select * from sec_user");
//		List<User> users=User.dao.find("select * from sec_user");
//		List<Record> users= Db.find("select * from sec_user");


//触发调用quartz
//		QuartzKey quartzKey = new QuartzKey(1, "test", "test");
//		new QuartzCronJob(quartzKey, "*/1 * * * * ?", DemoJob.class).addParam("name", "quartz").start();
		List<User> users= User.dao.find("select * from sec_user");
		setAttr("users", users);
		render("front/index.html");
	}
	
	public void showText(){
		renderText("Show Text");
	}
	
	@ActionKey("actionKey")
	public void testActionKey(){
		renderText("Test ActionKey");
	}

	/**
	 * 加入缓存;
	 * @Before(CacheInterceptor.class)
	 * @CacheName("userList")
	 * 清除缓存:
	 * @Before(EvictInterceptor.class)
	 * @CacheName("userList")
	 */
	@Before(CacheInterceptor.class)
	@CacheName("userList")
	public void testDB() {
		List<User> users = User.dao.find("select * from user");
//		List<User> users = User.dao.find("select * from user where id=?",2);
//		Page<User> users = User.dao.paginate(1, 10, "select *","from user where id=?",2);
//		for(User user :users.getList()){
//			System.out.println(user.toString()+user.getStr("user_acc"));
//		}
		/*User user = new User();
		user.set("user_acc", "accNew");
		user.set("name", "nameNew");
		boolean flag = user.save();
		System.out.println(flag);*/
		if(LOG.isDebugEnabled()){
			LOG.debug(users.toString());
		}
		setAttr("user", users);
		renderFreeMarker("/test.html");
	}
	
	public void covertJson(){
		renderJson(new User().set("user_acc", "acc"));
	}

}

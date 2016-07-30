package com.shoukeplus.jFinal.module.index;

import cn.dreampie.mail.Mailer;
import cn.dreampie.template.freemarker.FreemarkerLoader;
import com.jfinal.kit.HashKit;
import com.jfinal.plugin.activerecord.Page;
import com.shoukeplus.jFinal.cache.ActionCache;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.*;
import com.shoukeplus.jFinal.common.utils.DateUtil;
import com.shoukeplus.jFinal.common.utils.FileUploadUtil;
import com.shoukeplus.jFinal.common.utils.StrUtil;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import com.shoukeplus.jFinal.plugin.message.Actions;
import com.shoukeplus.jFinal.plugin.message.MessageKit;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.mail.EmailException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@ControllerBind(controllerKey = "/", viewPath = "/WEB-INF/pages")
public class IndexController extends BaseController {

	/**
	 * 首页
	 */
	@ActionCache
	public void index() {
		String tab = getPara("tab", "all");
		String q = getPara("q");
		Integer l = getParaToInt("l", 0);
		if (l != null && l > 0) {
			tab = "all";
			setAttr("_label", Label.dao.findById(l));
		}
		if (tab.equals("all") || tab.equals("good")) {
			setAttr("sectionName", "板块");
		} else {
			Section section = Section.dao.findByTab(tab);
			setAttr("sectionName", section != null ? section.get("name") : "板块");
		}
		Page<Topic> page = Topic.dao.paginate(getParaToInt("p", 1),
				getParaToInt("size", defaultPageSize()), tab, q, 1, l);
		setAttr("page", page);
		List<User> scoreTopTen = User.dao.findBySize(10);
		setAttr("scoreTopTen", scoreTopTen);
		setAttr("tab", tab);
		setAttr("q", q);
		setAttr("l", l);
		//查询无人回复的话题
		List<Topic> notReplyTopics = Topic.dao.findNotReply(5);
		setAttr("notReplyTopics", notReplyTopics);
		//社区运行状态
		int userCount = User.dao.countUsers();
		int topicCount = Topic.dao.topicCount();
		int replyCount = Reply.dao.replyCount();
		setAttr("userCount", userCount);
		setAttr("topicCount", topicCount);
		setAttr("replyCount", replyCount);
		render("front/index.ftl");
	}

	/**
	 * 登出
	 */
	public void logout() {
		removeCookie(AppConstants.USER_COOKIE);
		removeSessionAttr(AppConstants.USER_SESSION);
		SecurityUtils.getSubject().logout();
		redirect("/");
	}

	/**
	 * 后台管理登录
	 * 默认账号admin
	 * 默认密码123123
	 * 对应表 admin_user
	 */
	public void adminlogin() {
		String method = getRequest().getMethod();
		if (method.equalsIgnoreCase(AppConstants.GET)) {
			//setAttr("retryCount",0);
			render("front/adminlogin.ftl");
		} else if (method.equalsIgnoreCase(AppConstants.POST)) {
			String username = getPara("username");
			String password = getPara("password");
			//String rememberMe = getPara("rememberMe");
			//boolean isRememberMe="true".equals(rememberMe)? true:false;

			CacheManager cacheManager = CacheManager.create(CacheManager.class.getClassLoader().getResource("ehcache-shiro.xml"));
			Ehcache passwordRetryCache = cacheManager.getCache("passwordRetryCache");
			Element element = passwordRetryCache.get(username);

			int retryCount = element == null ? 0 : ((AtomicInteger) element.getObjectValue()).intValue();
			retryCount++;
			setAttr("retryCount", retryCount);

			if (retryCount > 3) {
				if (!validateCaptcha("captcha")) {
					setAttr("error", "验证码校验失败");
					render("front/adminlogin.ftl");
					return;
				}
			}

			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
			try {
				//该功能开启会导致安全问题,还是不开启的好..http://my.oschina.net/ayao/blog/420764
				//usernamePasswordToken.setRememberMe(isRememberMe);
				subject.login(usernamePasswordToken);

				// 在使用RememberMe功能时,需要配合相应的拦截器实现相应的功能,用错了拦截器可能就满足不了需求.
				// /aa.jsp=auth 表示访问该地址必须用户身份验证通过; /**=user 表示访问该地址必须是身份验证通过或者RememberMe登录.
				// 在需要 使用该功能的地方,加上如下代码进行操作;
				// 例如: 可以先通过cookie获取用户名密码,然后进行new UsernamePasswordToken,进行subject.login登录;
				/*if(subject.isRemembered()){
					//isRememberMe,记住me
					System.out.println("---------isRememberMe---------");
				}*/



				//用户登录成功,发送消息,消息驱动,
				MessageKit.sendMessage(Actions.USER_LOGINED, AdminUser.dao.findByUsername(username));
				setSessionAttr(AppConstants.SESSION_ADMIN_USERNAME, username);
				redirect("/admin/index");
			} catch (ExcessiveAttemptsException e) {
				e.printStackTrace();
				setAttr("error", "多次登陆失败,10分钟后重试");
				render("front/adminlogin.ftl");
			} catch (AuthenticationException e) {
				e.printStackTrace();

				setAttr("error", "用户名或密码错误,重试次数:" + retryCount + "/5");
				render("front/adminlogin.ftl");
			}
		}
	}

	public void login() {
		String method = getRequest().getMethod();
		if (method.equalsIgnoreCase(AppConstants.GET)) {
			render("front/user/login.ftl");
		} else if (method.equalsIgnoreCase(AppConstants.POST)) {
			String email = getPara("email");
			String password = getPara("password");
			if (StrUtil.isBlank(email) || StrUtil.isBlank(password)) {
				error("邮箱/密码都不能为空");
			} else {
				User user = User.dao.localLogin(email, HashKit.md5(password));
				if (user == null) {
					error("邮箱/密码错误");
				} else {
					setSessionAttr(AppConstants.USER_SESSION, user);
					setCookie(AppConstants.USER_COOKIE, StrUtil.getEncryptionToken(user.getStr("token")), 30 * 24 * 60 * 60);
					success();
				}
			}
		}
	}

	public void reg() {
		String method = getRequest().getMethod();
		if (method.equalsIgnoreCase(AppConstants.GET)) {
			String third = getPara("third");
			if (StrUtil.isBlank(third)) {
				removeSessionAttr("open_id");
				removeSessionAttr("thirdlogin_type");
				removeSessionAttr("unsave_user");
			}
			render("front/user/reg.ftl");
		} else if (method.equalsIgnoreCase(AppConstants.POST)) {
			String email = getPara("reg_email");
			String password = getPara("reg_password");
			String nickname = getPara("reg_nickname");
			String valicode = getPara("valicode");
			String open_id = (String) getSession().getAttribute("open_id");
			if (StrUtil.isBlank(email) || StrUtil.isBlank(password) || StrUtil.isBlank(nickname) || StrUtil.isBlank(valicode)) {
				error("请完善注册信息");
			} else {
				if (!StrUtil.isEmail(email)) {
					error("请输入正确的邮箱地址");
				} else {
					Valicode code = Valicode.dao.findByCodeAndEmail(valicode, email, AppConstants.REG);
					if (code == null) {
						error("验证码不存在或已使用(已过期)");
					} else {
						User user = User.dao.findByEmail(email);
						if (user != null) {
							error("邮箱已经注册，请直接登录");
						} else if (User.dao.findByNickname(nickname) != null) {
							error("昵称已经被注册，请更换其他昵称");
						} else {
							String uuid = StrUtil.getUUID();
							String token = StrUtil.getUUID();
							Date date = new Date();
							if (StrUtil.isBlank(open_id)) {
								user = new User();
								user.set("id", uuid)
										.set("nickname", StrUtil.noHtml(nickname).trim())
										.set("password", HashKit.md5(password))
										.set("score", 0)
										.set("mission", date)
										.set("in_time", date)
										.set("email", email)
										.set("token", token)
										//.set("avatar", "/static/img/default_avatar.png")
										.save();
							} else {
								user = getSessionAttr("unsave_user");
								if (user == null) {
									user = new User();
									user.set("id", uuid)
											.set("nickname", StrUtil.noHtml(nickname).trim())
											.set("password", HashKit.md5(password))
											.set("score", 0)
											.set("mission", date)
											.set("in_time", date)
											.set("email", email)
											.set("token", token)
											//.set("avatar", "/static/img/default_avatar.png")
											.save();
								} else {
									user.set("nickname", StrUtil.noHtml(nickname).trim())
											.set("password", HashKit.md5(password))
											.set("mission", date)
											.set("email", email)
											.set("token", token)
											.set("in_time", date)
											.set("score", 0)
											//.set("avatar", "/static/img/default_avatar.png")
											.save();
								}
								removeSessionAttr("unsave_user");
								removeSessionAttr("open_id");
								removeSessionAttr("thirdlogin_type");
							}
							setSessionAttr(AppConstants.USER_SESSION, user);
							setCookie(AppConstants.USER_COOKIE, StrUtil.getEncryptionToken(user.getStr("token")), 30 * 24 * 60 * 60);
							//更新验证状态
							code.set("status", 1).update();
							success();
						}
					}
				}
			}
		}
	}

	public void sendValiCode() {
		int minute = 30;
		final String email = getPara("email");
		if (StrUtil.isBlank(email)) {
			error("邮箱不能为空");
		} else if (!StrUtil.isEmail(email)) {
			error("邮箱格式不正确");
		} else {
			String type = getPara("type");
			String valicode = StrUtil.randomString(6);
			if (type.equalsIgnoreCase(AppConstants.FORGET_PWD)) {
				User user = User.dao.findByEmail(email);
				if (user == null) {
					error("该邮箱未被注册，请先注册");
				} else {
					Valicode code = new Valicode();
					code.set("code", valicode)
							.set("type", type)
							.set("in_time", new Date())
							.set("status", 0)
							.set("expire_time", DateUtil.getMinuteAfter(new Date(), minute))
							.set("target", email)
							.save();
					final String sendHtml = new FreemarkerLoader("template/mails/retrieve_email.ftl").setValue("code", valicode).setValue("minute", minute).getHtml();
					/*
					//同步发送,页面会一直等待邮件发送完毕才能操作,不太合适
					try {
						Mailer.sendHtml("shoukeApp-找回密码", sendHtml, email);
						success();
					} catch (EmailException e) {
						e.printStackTrace();
					}*/
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Mailer.sendHtml("shoukeApp-找回密码", sendHtml, email);
							} catch (EmailException e) {
								e.printStackTrace();
							}
						}
					}).start();
					success();
				}
			} else if (type.equalsIgnoreCase(AppConstants.REG)) {
				User user = User.dao.findByEmail(email);
				if (user != null) {
					error("邮箱已经注册，请直接登录");
				} else {
					Valicode code = new Valicode();
					code.set("code", valicode)
							.set("type", type)
							.set("in_time", new Date())
							.set("status", 0)
							.set("expire_time", DateUtil.getMinuteAfter(new Date(), minute))
							.set("target", email)
							.save();
					String sendHtml = new FreemarkerLoader("template/mails/signup_email.ftl").setValue("code", valicode).setValue("minute", minute).getHtml();
					try {
						Mailer.sendHtml("shoukeApp-账号注册", sendHtml, email);
						success();
					} catch (EmailException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void forgetpwd() {
		String method = getRequest().getMethod();
		if (method.equalsIgnoreCase(AppConstants.GET)) {
			render("front/user/forgetpwd.ftl");
		} else if (method.equalsIgnoreCase(AppConstants.POST)) {
			String email = getPara("email");
			String valicode = getPara("valicode");
			String newpwd = getPara("newpwd");
			if (StrUtil.isBlank(email) || StrUtil.isBlank(valicode) || StrUtil.isBlank(newpwd)) {
				error("请完善信息");
			} else {
				Valicode code = Valicode.dao.findByCodeAndEmail(valicode, email, AppConstants.FORGET_PWD);
				if (code == null) {
					error("验证码不存在或已使用(已过期)");
				} else {
					User user = User.dao.findByEmail(email);
					if (user == null) {
						error("该邮箱未被注册，请先注册");
					} else {
						user.set("password", HashKit.md5(newpwd)).update();
						code.set("status", 1).update();
						success();
					}
				}
			}
		}
	}

	/**
	 * 富客户端
	 * 返回绝对路径
	 */
	public void upload() {
		String paramPath = getPara(0).trim();
		//获取图片的相对路径
		String relativePath = FileUploadUtil.upload(paramPath, getFiles(paramPath));
		//富编辑器里需要加上图片访问路径 ,因为有XSS过滤,只有src的protocol 为http或者https 的才能保存;
		renderText(AppConstants.IMG_HOSTURL + relativePath);
	}

	/**
	 * plupload
	 * 返回相对路径
	 */
	public void uploadPl() {
		String paramPath = getPara(0).trim();
		//获取图片的相对路径
		String relativePath = FileUploadUtil.upload(paramPath, getFiles(paramPath));
		//富编辑器里需要加上图片访问路径 ,因为有XSS过滤,只有src的protocol 为http或者https 的才能保存;
		renderText(relativePath);
	}

	public void api() {
		render("front/api.ftl");
	}

	public void service() {
		render("front/service.ftl");
	}

	public void donate() {
		List<Donate> donates = Donate.dao.find("select * from sk_donate order by in_time desc");
		setAttr("donates", donates);
		render("front/donate.ftl");
	}
}
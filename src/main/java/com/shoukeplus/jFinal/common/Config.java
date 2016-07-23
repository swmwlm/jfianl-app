package com.shoukeplus.jFinal.common;

import cn.dreampie.mail.MailerPlugin;
import cn.dreampie.quartz.QuartzPlugin;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jagregory.shiro.freemarker.ShiroTags;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.HashKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.tx.TxByMethodRegex;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.ViewType;
import com.shoukeplus.jFinal.common.model._MappingKit;
import com.shoukeplus.jFinal.common.utils.ext.plugin.shiro.ShiroInterceptor;
import com.shoukeplus.jFinal.common.utils.ext.plugin.shiro.ShiroPlugin;
import com.shoukeplus.jFinal.common.utils.ext.route.AutoBindRoutes;
import com.shoukeplus.jFinal.handler.FakeStaticHandler;
import com.shoukeplus.jFinal.handler.ResourceHandler;
import com.shoukeplus.jFinal.handler.SkipHandler;
import com.shoukeplus.jFinal.handler.xss.AttackHandler;
import com.shoukeplus.jFinal.interceptor.csrf.CSRFInterceptor;
import com.shoukeplus.jFinal.interceptor.CommonInterceptor;
import com.shoukeplus.jFinal.interceptor.ExceptionAndLogInterceptor;
import freemarker.template.TemplateModelException;

import java.util.Properties;

public class Config extends JFinalConfig {

	/**
	 * 供Shiro插件使用。
	 */
	private Routes routes;

	public Properties loadProp(String pro, String dev) {
		try {
			return loadPropertyFile(pro);
		} catch (Exception e) {
			return loadPropertyFile(dev);
		}
	}

	@Override
	public void configConstant(Constants me) {
		// 如果生产环境配置文件存在，则优先加载该配置，否则加载开发环境配置文件
		loadProp("application_pro.properties", "application.properties");
		//loadPropertyFile("a_little_config.txt");
		me.setDevMode(getPropertyToBoolean("devMode"));

		//配置模板
		//me.setMainRenderFactory(new MyBeetlRenderFactory());
		//获取GroupTemplate模板，可以设置共享变量操作
		//GroupTemplate groupTemplate = MyBeetlRenderFactory.groupTemplate;
		//使用beetl Format 防御xss
		//groupTemplate.registerFormat("xss", new XSSDefenseFormat());

		me.setViewType(ViewType.FREE_MARKER);
		me.setEncoding("UTF-8");
		me.setFreeMarkerViewExtension("ftl");
		me.setBaseUploadPath(AppConstants.UPLOAD_DIR);
		me.setMaxPostSize(2048000);
		me.setFreeMarkerTemplateUpdateDelay(0);

		me.setError401View("/WEB-INF/pages/html/401.html");//没登录
		me.setError403View("/WEB-INF/pages/html/403.html");//没权限
		me.setError404View("/WEB-INF/pages/html/404.html");
		me.setError500View("/WEB-INF/pages/html/500.html");

		//shiro 对 freemarker 的支持
		FreeMarkerRender.getConfiguration().setSharedVariable("shiro", new ShiroTags());
		try {
			FreeMarkerRender.getConfiguration().setSharedVariable("imgPath", AppConstants.IMG_HOSTURL);
		} catch (TemplateModelException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("path"));

		//me.add(new AccessDeniedHandler("/**/*.ftl"));
		//me.add(new ResourceHandler("/static/**", "/images/**", "/css/**", "/lib/**", "/**/*.html"));

		me.add(new ResourceHandler("/static/**"));
		me.add(new FakeStaticHandler());
		me.add(new SkipHandler("/static/**"));
		//防xss攻击
		me.add(new AttackHandler());

		DruidStatViewHandler dvh = new DruidStatViewHandler("/admin/druid");
		me.add(dvh);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new TxByMethodRegex("(.*save.*|.*update.*|.*modify.*|.*insert.*)"));
		me.add(new CSRFInterceptor());
		//让视图freemarker,beetl可以使用session
		me.add(new SessionInViewInterceptor());
		//shiro权限拦截器配置
		me.add(new ShiroInterceptor());
		me.add(new CommonInterceptor());

		//异常日志统一处理
		me.add(new ExceptionAndLogInterceptor());
		//有缺陷
		//me.add(new UserInterceptor());
	}

	@Override
	public void configPlugin(Plugins me) {

		//C3p0数据源插件
//		C3p0Plugin c3p0 = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
//		me.add(c3p0);

		// 配置Druid数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(getProperty("jdbc.url"),
				getProperty("jdbc.username"),
				getProperty("jdbc.password"),
				getProperty("jdbc.driver"));

		// 防御sql注入攻击
		WallFilter wall = new WallFilter();
		wall.setDbType(getProperty("jdbc.dbType"));
		//druidPlugin.setFilters("stat,wall");
		druidPlugin.addFilter(wall);
		druidPlugin.addFilter(new StatFilter());

		druidPlugin.setInitialSize(getPropertyToInt("db.default.poolInitialSize"));
		druidPlugin.setMaxPoolPreparedStatementPerConnectionSize(getPropertyToInt("db.default.poolMaxSize"));
		druidPlugin.setTimeBetweenConnectErrorMillis(getPropertyToInt("db.default.connectionTimeoutMillis"));
		me.add(druidPlugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 所有配置在 MappingKit 中搞定
		// 每次sql表有变动,可以使用GeneratorDemo搞定
		_MappingKit.mapping(arp);
		me.add(arp);
		//单独映射表和实体关系
		//arp.addMapping("zc_user",ZcUser.class);


		//启用本地缓存
		me.add(new EhCachePlugin());

		//shiro权限框架
		ShiroPlugin shiroPlugin = new ShiroPlugin(routes);
		me.add(shiroPlugin);

		//emailer插件
		me.add(new MailerPlugin());

		//quartz 任务
		me.add(new QuartzPlugin());
	}

	@Override
	public void configRoute(Routes me) {
		this.routes = me;
		me.add(new AutoBindRoutes());
	}

	@Override
	public void afterJFinalStart() {
		//设置全局变量
		//JFinal.me().getServletContext().setAttribute("imgPath",AppConstants.IMG_HOSTURL);
		//FreeMarkerRender.getConfiguration().setSharedVariable("imgPath",AppConstants.IMG_HOSTURL);
		//super.afterJFinalStart();
	}

	/**
	 * 启动完毕所做的处理
	 */
//    @Override
//    public void afterJFinalStart() {
//        try {
//            String qq_properties = "qqconnectconfig.properties";
//            Properties qq_prop = PropKit.use(qq_properties).getProperties();
//            qq_prop.setProperty("app_ID", com.jfinalbbs.common.Constants.getValue("qq_appId"));
//            qq_prop.setProperty("app_KEY", com.jfinalbbs.common.Constants.getValue("qq_appKey"));
//            qq_prop.setProperty("redirect_URI", com.jfinalbbs.common.Constants.getValue("qq_redirect_URI"));
//            qq_prop.store(new FileOutputStream(PathKit.getRootClassPath() + "/" + qq_properties), "read db config write to prop file");
//
//            String sina_properties = "weiboconfig.properties";
//            Properties sina_prop = PropKit.use(sina_properties).getProperties();
//            sina_prop.setProperty("client_ID", com.jfinalbbs.common.Constants.getValue("sina_clientId"));
//            sina_prop.setProperty("client_SERCRET", com.jfinalbbs.common.Constants.getValue("sina_clientSercret"));
//            sina_prop.setProperty("redirect_URI", com.jfinalbbs.common.Constants.getValue("sina_redirect_URI"));
//            sina_prop.store(new FileOutputStream(PathKit.getRootClassPath() + "/" + sina_properties), "read db config write to prop file");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		System.out.println(HashKit.md5("123456"));
		JFinal.start("src/main/webapp", 80, "/", 5);
	}
}

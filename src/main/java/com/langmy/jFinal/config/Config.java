package com.langmy.jFinal.config;

import cn.dreampie.log.Slf4jLogFactory;
import cn.dreampie.mail.MailerPlugin;
import cn.dreampie.quartz.QuartzPlugin;
import cn.dreampie.shiro.core.ShiroPlugin;
import cn.dreampie.sqlinxml.SqlInXmlPlugin;
import cn.dreampie.web.render.JsonErrorRenderFactory;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.langmy.jFinal.beetl.MyBeetlRenderFactory;
import com.langmy.jFinal.common.handler.HtmlHandler;
import com.langmy.jFinal.controller.HtmlController;
import com.langmy.jFinal.controller.db.AutoGenController;
import com.langmy.jFinal.model._MappingKit;
import org.beetl.core.GroupTemplate;

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
        loadProp("a_little_config_pro.txt", "a_little_config.txt");
        //loadPropertyFile("a_little_config.txt");
        me.setDevMode(getPropertyToBoolean("devMode"));

        //		视图模板使用Freemarker
//		me.setViewType(ViewType.FREE_MARKER);
        me.setError401View("/html/401.html");//没登录
        me.setError403View("/html/403.html");//没权限
        me.setError404View("/html/404.html");
        me.setError500View("/html/500.html");
        //配置模板
        me.setMainRenderFactory(new MyBeetlRenderFactory());
        //获取GroupTemplate模板，可以设置共享变量操作
        GroupTemplate groupTemplate = MyBeetlRenderFactory.groupTemplate;

        me.setViewType(ViewType.JSP);
        me.setEncoding("UTF-8");
        //set log to slf4j
        Logger.setLoggerFactory(new Slf4jLogFactory());
        me.setErrorRenderFactory(new JsonErrorRenderFactory());
    }

    @Override
    public void configHandler(Handlers me) {
        me.add(new ContextPathHandler("path"));
        DruidStatViewHandler dvh = new DruidStatViewHandler("/druid");
        me.add(dvh);

        me.add(new HtmlHandler());
    }

    @Override
    public void configInterceptor(Interceptors me) {
        //shiro权限拦截器配置
        //me.add(new ShiroInterceptor());
        //开发时不用开启  避免不能实时看到数据效果
//    me.add(new CacheRemoveInterceptor());
//    me.add(new CacheInterceptor());
        //让freemarker可以使用session
        //me.add(new SessionInViewInterceptor());
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
        druidPlugin.addFilter(wall);

        druidPlugin.addFilter(new StatFilter());
        druidPlugin.setInitialSize(getPropertyToInt("db.default.poolInitialSize"));
        druidPlugin.setMaxPoolPreparedStatementPerConnectionSize(getPropertyToInt("db.default.poolMaxSize"));
        druidPlugin.setTimeBetweenConnectErrorMillis(getPropertyToInt("db.default.connectionTimeoutMillis"));
        me.add(druidPlugin);
        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        me.add(arp);
        //单独映射表和实体关系
        //arp.addMapping("zc_user",ZcUser.class);

        // 所有配置在 MappingKit 中搞定
        // 每次sql表有变动,可以使用GeneratorDemo搞定
        _MappingKit.mapping(arp);

        //sql语句plugin
        me.add(new SqlInXmlPlugin());

        //启用本地缓存
        EhCachePlugin ecp = new EhCachePlugin();
        me.add(ecp);

        //shiro权限框架
        //me.add(new ShiroPlugin(routes, new MyJdbcAuthzService()));
        ShiroPlugin shiroPlugin = new ShiroPlugin(routes);
        me.add(shiroPlugin);

        //quartz 任务
        me.add(new QuartzPlugin());

        //emailer插件
        me.add(new MailerPlugin());

    }

    @Override
    public void configRoute(Routes me) {
        this.routes = me;
        // 第三个参数为该Controller的视图存放路径
        // 第三个参数省略时默认与第一个参数值相同，在此即为 "/gencode"
        me.add("/jhtml", HtmlController.class);
        //  /gencode?db_list=table
        me.add("/gencode", AutoGenController.class, "/gencode");
        me.add(new FrontRoutes());
        me.add(new AdminRoutes());

//        暂不使用自动绑定功能 ,jfinal2.2开发了generator插件
//        RouteBind routeBind = new RouteBind();
//        routes.add(routeBind);
    }

    @Override
    public void afterJFinalStart() {
        super.afterJFinalStart();
    }

/**
 * 建议使用 JFinal 手册推荐的方式启动项目
 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
 */
//    public static void main(String[] args) {
//        JFinal.start("src/main/webapp", 80, "/", 5);
//    }
}

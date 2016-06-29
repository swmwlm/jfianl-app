package com.test.common;

import cn.dreampie.PropertiesKit;
import cn.dreampie.mail.MailerPlugin;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.langmy.jFinal.common.model._MappingKit;

import java.sql.Connection;
import java.sql.SQLException;

public class DBInit {
  private static final String DB_CONFIG = "application.properties";

  private DruidPlugin druidPlugin;


  public void init() {
    druidPlugin = initDruid();

    ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
    _MappingKit.mapping(arp);

    arp.start();
    initMailer();
  }


  private DruidPlugin initDruid() {
    PropertiesKit.me().loadPropertyFile(DB_CONFIG);

    DruidPlugin druidPlugin = new DruidPlugin(PropertiesKit.me().getProperty("jdbc.url"),
            PropertiesKit.me().getProperty("jdbc.username"),
            PropertiesKit.me().getProperty("jdbc.password"),
            PropertiesKit.me().getProperty("jdbc.driver"));
    // StatFilter提供JDBC层的统计信息
    druidPlugin.addFilter(new StatFilter());
    // WallFilter的功能是防御SQL注入攻击
    WallFilter wallFilter = new WallFilter();
    wallFilter.setDbType("mysql");
    druidPlugin.addFilter(wallFilter);
    druidPlugin.start();
    return druidPlugin;
  }



  private MailerPlugin initMailer() {
    MailerPlugin mailerPlugin = new MailerPlugin();
    mailerPlugin.start();
    return mailerPlugin;
  }

  public Connection getConnection() throws SQLException {
    return druidPlugin.getDataSource().getConnection();
  }

  public void shutdown() throws SQLException {
    druidPlugin.stop();
  }

}
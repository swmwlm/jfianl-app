package com.langmy.jFinal.common.controller;

import cn.dreampie.tree.TreeNodeKit;
import com.jfinal.plugin.ehcache.CacheName;
import com.langmy.jFinal.common.model.Area;
import com.langmy.jFinal.config.AppConstants;

/**
 * Created by wangrenhui on 14-1-3.
 */
public class AreaController extends Controller {

  @CacheName(AppConstants.DEFAULT_CACHENAME)
  public void query() {
    String where = "";
    Integer pid = getParaToInt("pid");
    if (pid != null && pid > 0) {
      where += " `area`.pid =" + pid;
    }
    Boolean isdelete = getParaToBoolean("isdelete");
    if (isdelete != null && isdelete) {
      where += " AND `area`.deleted_at is NULL";
    }

    Boolean istree = getParaToBoolean("istree");
    if (istree != null && istree) {
      setAttr("areas", TreeNodeKit.toTree(Area.dao.findBy(where)));
    } else {
      setAttr("areas", Area.dao.findBy(where));
    }
  }
}

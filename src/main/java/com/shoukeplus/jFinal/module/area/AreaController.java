package com.shoukeplus.jFinal.module.area;

import cn.dreampie.tree.TreeNodeKit;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Area;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;

import java.util.List;

@ControllerBind(controllerKey = "/admin/area", viewPath = "/WEB-INF/pages/admin/area")
public class AreaController extends BaseController {

	public void query() {
		//setAttr("areas", Area.dao.find("select * from sk_area "));
		List<Area> areas = TreeNodeKit.toTree(Area.dao.find("select * from sk_area "));
		setAttr("areas", areas);
	}
}
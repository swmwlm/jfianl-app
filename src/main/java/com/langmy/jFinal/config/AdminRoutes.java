package com.langmy.jFinal.config;

import com.jfinal.config.Routes;
import com.langmy.jFinal.controller.admin.AdminController;


public class AdminRoutes extends Routes {

	@Override
	public void config() {
		add("/admin", AdminController.class);
	}

}

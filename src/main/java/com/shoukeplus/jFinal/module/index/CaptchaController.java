package com.shoukeplus.jFinal.module.index;

import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;

@ControllerBind(controllerKey = "/captcha")
public class CaptchaController extends BaseController {
	public void index(){
		renderCaptcha();
	}
}

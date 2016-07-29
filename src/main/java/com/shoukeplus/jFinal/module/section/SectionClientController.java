package com.shoukeplus.jFinal.module.section;

import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Section;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;

@ControllerBind(controllerKey = "/api/section")
public class SectionClientController extends BaseController {

    public void index() {
        success(Section.dao.findShow());
    }
}

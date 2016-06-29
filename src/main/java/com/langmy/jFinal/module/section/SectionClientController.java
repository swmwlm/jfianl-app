package com.langmy.jFinal.module.section;

import com.langmy.jFinal.common.BaseController;
import com.langmy.jFinal.common.model.Section;
import com.langmy.jFinal.common.utils.ext.route.ControllerBind;

@ControllerBind(controllerKey = "/api/section")
public class SectionClientController extends BaseController {

    public void index() {
        success(Section.dao.findShow());
    }
}

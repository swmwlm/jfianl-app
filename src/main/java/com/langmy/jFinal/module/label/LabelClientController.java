package com.langmy.jFinal.module.label;

import com.jfinal.plugin.activerecord.Page;
import com.langmy.jFinal.common.BaseController;
import com.langmy.jFinal.common.model.Label;
import com.langmy.jFinal.common.model.Topic;
import com.langmy.jFinal.common.utils.StrUtil;
import com.langmy.jFinal.common.utils.ext.route.ControllerBind;

import java.util.List;

@ControllerBind(controllerKey = "/api/label")
public class LabelClientController extends BaseController {

    public void index() {
        String name = getPara("name");
        if (StrUtil.isBlank(name)) {
            List<Label> labels = Label.dao.findAll();
            setAttr("labels", labels);
            success(labels);
        } else {
            Label label = Label.dao.findByName(name);
            Page<Topic> page = null;
            if (label != null) {
                page = Topic.dao.paginate(getParaToInt("p", 1),
                        getParaToInt("size", defaultPageSize()), null, null, 1, label.getInt("id"));
            }
            success(page);
        }
    }

}

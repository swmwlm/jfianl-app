package com.shoukeplus.jFinal.module.label;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Label;
import com.shoukeplus.jFinal.common.model.Topic;
import com.shoukeplus.jFinal.common.utils.StrUtil;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import com.shoukeplus.jFinal.interceptor.ClientInterceptor;

import java.util.List;

@ControllerBind(controllerKey = "/api/label")
public class LabelClientController extends BaseController {
    //需要使用令牌验证通过
    @Before(ClientInterceptor.class)
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

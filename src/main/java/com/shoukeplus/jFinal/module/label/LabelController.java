package com.shoukeplus.jFinal.module.label;

import com.jfinal.plugin.activerecord.Page;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.Label;
import com.shoukeplus.jFinal.common.model.Topic;
import com.shoukeplus.jFinal.common.utils.StrUtil;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@ControllerBind(controllerKey = "/label", viewPath = "/WEB-INF/pages")
public class LabelController extends BaseController {

    public void index() throws UnsupportedEncodingException {
        String name = getPara(0);
        if (StrUtil.isBlank(name)) {
            List<Label> labels = Label.dao.findAll();
            setAttr("labels", labels);
            render("front/label/list.ftl");
        } else {
            name = URLDecoder.decode(name, "UTF-8");
            Label label = Label.dao.findByName(name);
            setAttr("label", label);
            if (label != null) {
                Page<Topic> page = Topic.dao.paginate(getParaToInt("p", 1),
                        getParaToInt("size", defaultPageSize()), null, null, 1, label.getInt("id"));
                setAttr("page", page);
            }
            render("front/label/index.ftl");
        }
    }

    public void search() {
        String q = getPara("q");
        if (StrUtil.isBlank(q)) {
            renderJson(new ArrayList<String>());
        } else {
            List<Label> labels = Label.dao.findByNameLike(q);
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < labels.size(); i++) {
                list.add(labels.get(i).getStr("name"));
            }
            renderJson(list);
        }
    }

}

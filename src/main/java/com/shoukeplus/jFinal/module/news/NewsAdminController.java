package com.shoukeplus.jFinal.module.news;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.*;
import com.shoukeplus.jFinal.common.utils.DateUtil;
import com.shoukeplus.jFinal.common.utils.StrUtil;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.Date;
import java.util.List;

@ControllerBind(controllerKey = "/admin/news", viewPath = "/WEB-INF/pages/admin/news")
public class NewsAdminController extends BaseController {

    @RequiresPermissions("menu:news")
    public void index() {
        String value = getPara("name");
        String dictId = getPara("dictId");
        setAttr("page", News.dao.page(getParaToInt("p", 1), defaultPageSize(),value,dictId));
        setAttr("name", value);
        setAttr("dictId", dictId);
        setAttr("newsCategory", Dict.dao.getList4Type("news"));
        render("index.ftl");
    }

    @RequiresPermissions("news:add")
    public void add() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            setAttr("newsCategory", Dict.dao.getList4Type("news"));
            setAttr("targetCategory", Dict.dao.getList4Type("target"));
            render("add.ftl");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            News news=getModel(News.class);
            news.setCreatedTime(DateUtil.getCurrentDateTime());
            news.save();
            Integer newsId=news.getId();
            List<NewsImages> newsImagesList=getModels(NewsImages.class,"newsImages");
            if(CollectionUtils.isNotEmpty(newsImagesList)){
                for(NewsImages img:newsImagesList){
                    img.setNewsId(newsId);
                    img.setCreatedTime(DateUtil.getCurrentDateTime());
                    img.save();
                }
            }
            redirect("/admin/news");
        }
    }

    @RequiresPermissions("news:delete")
    @Before(Tx.class)
    public void delete() {
        String id = getPara("id");
        News news = News.dao.findById(id);
        //删除资讯（非物理删除）
        news.setIsDeleted(1);
        news.update();
        
        //记录日志
        AdminUser adminUser = getAdminUser();
        AdminLog adminLog = new AdminLog();
        adminLog.set("uid", adminUser.getInt("id"))
                .set("target_id", id)
                .set("source", "news")
                .set("in_time", new Date())
                .set("action", "delete")
                .set("message", null)
                .save();
        success();
    }

    @RequiresPermissions("news:edit")
    public void edit() {
        String method = getRequest().getMethod();
        String id = getPara(0);
        if (StrUtil.isBlank(id)) {
            renderText(AppConstants.OP_ERROR_MESSAGE);
        } else {
            News news = News.dao.findById(id);
            if (news == null) {
                renderText("资讯不存在");
            } else {
                if (method.equalsIgnoreCase(AppConstants.GET)) {
                    setAttr("newsCategory", Dict.dao.getList4Type("news"));
                    setAttr("targetCategory", Dict.dao.getList4Type("target"));
                    setAttr("news",news);
                    setAttr("newsImages",NewsImages.dao.findByDictId(id));
                    render("edit.ftl");
                } else if (method.equalsIgnoreCase(AppConstants.POST)) {
                    News newsEdit=getModel(News.class);
                    newsEdit.setUpdatedTime(DateUtil.getCurrentDateTime());
                    newsEdit.update();
                    Integer newsId=newsEdit.getId();
                    List<NewsImages> newsImagesList=getModels(NewsImages.class,"newsImages");
                    if(CollectionUtils.isNotEmpty(newsImagesList)){
                        for(NewsImages img:newsImagesList){
                            img.setNewsId(newsId);
                            img.setCreatedTime(DateUtil.getCurrentDateTime());
                            img.update();
                        }
                    }

                    //记录日志
                    AdminUser adminUser = getAdminUser();
                    AdminLog adminLog = new AdminLog();
                    adminLog.set("uid", adminUser.getInt("id"))
                            .set("target_id", id)
                            .set("source", "news")
                            .set("in_time", new Date())
                            .set("action", "edit")
                            .set("message", null)
                            .save();
                    redirect("/admin/news");                }
            }
        }
    }
}

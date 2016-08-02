package com.shoukeplus.jFinal.module.content;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.BaseController;
import com.shoukeplus.jFinal.common.model.*;
import com.shoukeplus.jFinal.common.utils.DateUtil;
import com.shoukeplus.jFinal.common.utils.StrUtil;
import com.shoukeplus.jFinal.common.utils.ext.route.ControllerBind;
import com.shoukeplus.jFinal.render.MyFileRender;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerBind(controllerKey = "/admin/content", viewPath = "/WEB-INF/pages/admin/content")
public class ContentAdminController extends BaseController {

    @RequiresPermissions("menu:content")
    public void index() {
        String value = getPara("name");
        String dictId = getPara("dictId");
        setAttr("page", Content.dao.page(getParaToInt("p", 1), defaultPageSize(), value, dictId));
        setAttr("name", value);
        setAttr("dictId", dictId);
        setAttr("categories", Dict.dao.getListByContentCategory());
        render("index.ftl");
    }

    @RequiresPermissions("content:add")
    public void add() {
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            setAttr("categories", Dict.dao.getListByContentCategory());
            setAttr("targetCategory", Dict.dao.getList4Type("target"));
            render("add.ftl");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            AdminUser adminUser = getAdminUser();

            Content content = getModel(Content.class);
            content.setCreatedTime(DateUtil.getCurrentDateTime());
            content.setCreator(adminUser.getId());
            content.save();
            Integer contentId = content.getId();
            List<ContentImages> newsImagesList = getModels(ContentImages.class, "contentImages");
            if (CollectionUtils.isNotEmpty(newsImagesList)) {
                for (ContentImages img : newsImagesList) {
                    img.setContentId(contentId);
                    img.setCreatedTime(DateUtil.getCurrentDateTime());
                    img.setCreator(adminUser.getId());
                    img.save();
                }
            }
            List<ContentFiles> contentFilesList = getModels(ContentFiles.class, "contentFiles");
            if (CollectionUtils.isNotEmpty(contentFilesList)) {
                for (ContentFiles file : contentFilesList) {
                    file.setContentId(contentId);
                    file.setCreatedTime(DateUtil.getCurrentDateTime());
                    file.setCreator(adminUser.getId());
                    file.save();
                }
            }
            redirect("/admin/content");
        }
    }

    @RequiresPermissions("content:delete")
    @Before(Tx.class)
    public void delete() {
        String id = getPara("id");
        Content content = Content.dao.findById(id);
        //删除（非物理删除）
        content.setIsDeleted(1);
        content.update();

        //记录日志
        AdminUser adminUser = getAdminUser();
        AdminLog adminLog = new AdminLog();
        adminLog.set("uid", adminUser.getInt("id"))
                .set("target_id", id)
                .set("source", "content")
                .set("in_time", new Date())
                .set("action", "delete")
                .set("message", null)
                .save();
        success();
    }

    @RequiresPermissions("content:edit")
    public void edit() {
        String contentID = getPara(0);
        if (StrUtil.isBlank(contentID)) {
            renderText(AppConstants.OP_ERROR_MESSAGE);
        }
        String method = getRequest().getMethod();
        if (method.equalsIgnoreCase(AppConstants.GET)) {
            Content content = Content.dao.findById(contentID);
            if (content == null) {
                renderText("内容不存在");
            }
            setAttr("categories", Dict.dao.getListByContentCategory());
            setAttr("targetCategory", Dict.dao.getList4Type("target"));
            setAttr("content", content);
            setAttr("contentImages", ContentImages.dao.findByContentId(contentID));
            setAttr("contentFiles", ContentFiles.dao.findByContentId(contentID));
            render("edit.ftl");
        } else if (method.equalsIgnoreCase(AppConstants.POST)) {
            AdminUser adminUser = getAdminUser();

            Content contentEdit = getModel(Content.class);
            contentEdit.setUpdatedTime(DateUtil.getCurrentDateTime());
            contentEdit.setLastModifier(adminUser.getId());
            contentEdit.update();
            String contentImagesIDS = getRequest().getParameter("contentImagesIDS");
            String contentFilesIDS = getRequest().getParameter("contentFilesIDS");

            if (!Strings.isNullOrEmpty(contentImagesIDS)) {
                List<String> imageIDList = Splitter.onPattern("[,，]{1,}").trimResults().omitEmptyStrings().splitToList(contentImagesIDS);
                List<String> ids = new ArrayList<>();
                //1.根据传入待删除的图片ids; 来 删除 对应的资讯组图;
                if (CollectionUtils.isNotEmpty(imageIDList)) {
                    List<ContentImages> newsImagesList = ContentImages.dao.findIdsByContentId(contentID);
                    for (String imageId : imageIDList) {
                        for (ContentImages contentImages : newsImagesList) {
                            //2.删除前校验该id是否真实存在当前的资讯组图中,以防止非法删除;
                            if (imageId.equals(contentImages.getId().toString())) {
                                //NewsImages.dao.deleteById(img.getId());
                                ids.add(imageId);
                            }
                        }
                    }
                    //3.只有全部通过的,才做统一删除操作
                    if (CollectionUtils.isEqualCollection(imageIDList, ids)) {
                        ContentImages.dao.deleteByIds(ids);
                    }
                }
            }

            List<ContentImages> contentImagesList = getModels(ContentImages.class, "contentImages");
            if (CollectionUtils.isNotEmpty(contentImagesList)) {
                for (ContentImages img : contentImagesList) {
                    img.setCreatedTime(DateUtil.getCurrentDateTime());
                    if (ObjectUtils.notEqual(img.getId(), null)) {
                        img.setLastModifier(adminUser.getId());
                        img.setUpdatedTime(DateUtil.getCurrentDateTime());
                        img.update();
                    } else {
                        img.setContentId(Integer.parseInt(contentID));
                        img.setCreatedTime(DateUtil.getCurrentDateTime());
                        img.setCreator(adminUser.getId());
                        img.save();
                    }
                }
            }

            if (!Strings.isNullOrEmpty(contentFilesIDS)) {
                List<String> fileIDList = Splitter.onPattern("[,，]{1,}").trimResults().omitEmptyStrings().splitToList(contentFilesIDS);
                List<String> ids = new ArrayList<>();
                //1.根据传入待删除的附件ids; 来 删除 对应的附件;
                if (CollectionUtils.isNotEmpty(fileIDList)) {
                    List<ContentFiles> newsFilesList = ContentFiles.dao.findIdsByContentId(contentID);
                    for (String fileId : fileIDList) {
                        for (ContentFiles contentFiles : newsFilesList) {
                            //2.删除前校验该id是否真实存在当前的资讯附件中,以防止非法删除;
                            if (fileId.equals(contentFiles.getId().toString())) {
                                //NewsImages.dao.deleteById(img.getId());
                                ids.add(fileId);
                            }
                        }
                    }
                    //3.只有全部通过的,才做统一删除操作
                    if (CollectionUtils.isEqualCollection(fileIDList, ids)) {
                        ContentFiles.dao.deleteByIds(ids);
                    }
                }
            }

            List<ContentFiles> contentFilesList = getModels(ContentFiles.class, "contentFiles");
            if (CollectionUtils.isNotEmpty(contentFilesList)) {
                for (ContentFiles file : contentFilesList) {
                    file.setCreatedTime(DateUtil.getCurrentDateTime());
                    if (ObjectUtils.notEqual(file.getId(), null)) {
                        file.setLastModifier(adminUser.getId());
                        file.setUpdatedTime(DateUtil.getCurrentDateTime());
                        file.update();
                    } else {
                        file.setContentId(Integer.parseInt(contentID));
                        file.setCreatedTime(DateUtil.getCurrentDateTime());
                        file.setCreator(adminUser.getId());
                        file.save();
                    }
                }
            }

            //记录日志
            AdminLog adminLog = new AdminLog();
            adminLog.set("uid", adminUser.getInt("id"))
                    .set("target_id", contentEdit.getId())
                    .set("source", "content")
                    .set("in_time", new Date())
                    .set("action", "edit")
                    .set("message", null)
                    .save();
            redirect("/admin/content");
        }
    }

    @RequiresPermissions("content:view")
    public void view() {
        String contentId = getPara(0);
        if (StrUtil.isBlank(contentId)) {
            renderText(AppConstants.OP_ERROR_MESSAGE);
        }

        Content content = Content.dao.findById(contentId);
        if (content == null) {
            renderText("该内容不存在");
        }
        List<Dict> dictList = Dict.dao.getListByContentCategory();
        setAttr("categories", dictList);
        setAttr("targetCategory", Dict.dao.getList4Type("target"));

        String categoryName = null;
        for (Dict dict : dictList) {
            if (dict.getId() == content.getDictId()) {
                categoryName = dict.getValue();
            }
        }
        setAttr("categoryName", categoryName);

        setAttr("content", content);
        setAttr("contentImages", ContentImages.dao.findByContentId(contentId));
        setAttr("contentFiles", ContentFiles.dao.findByContentId(contentId));
        render("view.ftl");
    }

    public void downloadFile() {
        String fileId = getPara(0);
        ContentFiles contentFiles = ContentFiles.dao.findById(fileId);
        if (contentFiles == null) {
            renderText(AppConstants.OP_ERROR_MESSAGE);
        }
        String filePath = AppConstants.UPLOAD_DIR + contentFiles.get("file");
        File file = new File(filePath);
        if (file.isFile()) {
            render(new MyFileRender(file, contentFiles.getTitle()));
        } else {
            renderText(AppConstants.OP_ERROR_MESSAGE);
        }
    }


}

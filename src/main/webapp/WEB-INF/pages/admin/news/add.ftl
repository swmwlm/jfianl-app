<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="news">
<section class="content-header">
    <h1>
        资讯
        <small>添加</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/news"><i class="fa fa-tag"></i> 资讯</a></li>
        <li class="active">添加</li>
    </ol>
</section>
<section class="content">
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">创建资讯</h3>
        </div>
        <form class="form-horizontal" action="add" method="post">
            <div class="box-body">
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">资讯分类</label>
                    <div class="col-sm-5">
                        <select name="news.dictId" id="dictId" class="form-control">
                            <#list newsCategory as category>
                                <option value="${category.id!}">${category.value!}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="target" class="col-sm-2 control-label">打开方式</label>
                    <div class="col-sm-5">
                        <select name="news.target" id="target" class="form-control">
                            <#list targetCategory as target>
                                <option value="${target.key!}">${target.value!}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">标题</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="title" name="news.title" placeholder="标题" required="required">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">摘要</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="introduction" name="news.introduction" placeholder="摘要" required="required">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">是否为外链</label>
                    <div class="col-sm-5">
                        <div class="input-group">
                            <span class="input-group-addon">
                              <input type="checkbox" id="isExternalHref" name="news.isExternalHref" onchange="showExternalHref();">
                            </span>
                            <input class="form-control" type="text" readonly="readonly" id="externalHref" name="news.externalHref" placeholder="外链地址,形如:http://abc.com">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="img" class="col-sm-2 control-label">资讯摘要图片</label>
                    <div class="col-sm-6" id="pickfiles">
                        <img src="${path!}/static/img/upload.png" id="imgUpload" />
                        <input name="link.img" id="img" type="hidden" value=""/>
                    </div>
                </div>
                <div class="form-group" id="contentDiv">
                    <label for="name" class="col-sm-2 control-label">资讯内容</label>
                    <div class="col-sm-8">
                        <textarea class="form-control" id="content" name="news.content" placeholder="资讯内容" rows="20" required="required"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">作者</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="author" name="news.author" placeholder="作者">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">文章来源</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="source" name="news.source" placeholder="文章来源">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">发布时间</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="releaseTime" readonly="readonly" name="news.releaseTime" placeholder="发布时间" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" required="required">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">浏览量</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="view" name="news.view" placeholder="初始浏览量,例如:222" required="required">
                    </div>
                </div>
            </div>
            <div class="box-footer">
                <button type="submit" class="btn btn-raised btn-info pull-right">保存</button>
            </div>
        </form>
    </div>
</section>
<script type="text/javascript" src="${path}/static/component/plupload-2.1.9/js/plupload.full.min.js"></script>
<script type="text/javascript" src="${path}/static/component/plupload-2.1.9/js/i18n/zh_CN.js"></script>
<script type="text/javascript" src="${path}/static/component/plupload-2.1.9/extractUpload.js"></script>
<link rel="stylesheet" href="${path!}/static/component/wangEditor/css/wangEditor.css">
<link href="http://cdn.bootcss.com/jqueryui/1.11.4/jquery-ui.min.css" rel="stylesheet">
<script src="${path!}/static/component/wangEditor/js/wangEditor.js"></script>
<script src="http://cdn.bootcss.com/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script type="text/javascript">
    $(function () {
        uploadImg();
        initWangEditor();
    });
    function initWangEditor() {
        //==========wangEditor Start============
        var editor = new wangEditor("content");
        // 自定义菜单
        editor.config.menus = [
            'source',
            '|',
            'bold',
            'underline',
            'italic',
            'strikethrough',
            'forecolor',
            'bgcolor',
            'quote',
            'fontfamily',
            'fontsize',
            'head',
            'unorderlist',
            'orderlist',
            'link',
            'table',
            'img',
            'insertcode',
            '|',
            'fullscreen'
        ];
        editor.config.uploadImgUrl = '${path!}/upload/editor';
        editor.create();
        //==========wangEditor End============
    }
    function uploadImg() {
        extractUpload({
            browseElementId: 'pickfiles',
            url: '${path!}/uploadPl/news',
            filters: {
                max_file_size: '5mb',
                multi_selection: false,
                mime_types: [
                    {title: "图片文件", extensions: "jpg,png,jpeg,JPG,PNG,JPEG"},
                ]
            },
            error: function (up, error) {
                if (error.message.indexOf("File size error") > -1)
                    layer.open({content:'文件大小不允许超过5MB!', time: 1});
                else if (error.message.indexOf("File extension error") > -1)
                    layer.open({content:'请上传jpg、png、jpeg文件!', time: 1});
                else
                    layer.open({content:'上传出错,请重试...', time: 1});
            },
            success: function (up, file, info) {
                if (info.status) {
                    $('#img').val(info.response);
                    $('#imgUpload').attr("src","${imgPath!}"+info.response);
                }
            }
        });
    }
    function showExternalHref() {
        if($("#isExternalHref").is(':checked')){
            $("#externalHref").removeAttr("readonly");
            $("#contentDiv").hide();
        }else{
            $("#externalHref").attr("readonly","readonly");
            $("#contentDiv").show();
        }
    }
</script>
</@layout>
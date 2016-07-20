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
                    <label for="img" class="col-sm-2 control-label">资讯摘要图</label>
                    <div class="col-sm-6" id="pickfiles">
                        <img src="${path!}/static/img/upload.png" id="imgUpload" />
                        <input name="news.img" id="img" type="hidden" value=""/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="img" class="col-sm-2 control-label">资讯组图</label>
                    <div class="col-sm-3">
                        <img src="${path!}/static/img/upload.png" id="imgsUpload" style="display: block;clear:both;" />
                    </div>
                    <a href="javascript:void(0);" id="triggerFileBrowser"></a>
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
        initImagesConfig('triggerFileBrowser');
        $(document).on('click', '#imgsUpload', function () {
            if ($('div.newsimgs_upload').length == 10) {
                layer.msg('最多允许上传10张图片!', {time: 1000});
                return false;
            }
            document.getElementById('triggerFileBrowser').click();
        });
        $(document).on('mouseenter', 'div.newsimgs_upload', function () {
            var $deleteImg = $(this).find('.uploadimg_close');
            if ($deleteImg == null) {
                return false;
            }
            $deleteImg.show();
        });
        $(document).on('mouseleave', 'div.newsimgs_upload', function () {

            var $deleteImg = $(this).find('.uploadimg_close');
            if ($deleteImg == null) {
                return false;
            }
            $deleteImg.hide();
        });
        $(document).on('click', 'div.uploadimg_close>img', function () {
            var $imgContainer = $(this).parent().parent().parent();
            var index = $imgContainer.index();
            $imgContainer.remove();
        });
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
    /**
     * 上传文章缩略图
     */
    function uploadImg() {
        extractUpload({
            browseElementId: 'pickfiles',
            url: '${path!}/uploadPl/news',
            filters: {
                max_file_size: '2mb',
                multi_selection: false,
                mime_types: [
                    {title: "图片文件", extensions: "jpg,png,jpeg,JPG,PNG,JPEG"},
                ]
            },
            error: function (up, err) {
                if (err.message.indexOf("文件大小错误") > -1) {
                    layer.msg('您上传的文件超出限制，请重新上传（文件要小于2MB', {time: 1000});
                } else if (err.message.indexOf("文件扩展名错误") > -1) {
                    layer.msg('请上传jpg,png格式的文件', {time: 1000});
                } else {
                    layer.msg('上传错误，请重试', {time: 1000});
                }
            },
            success: function (up, file, info) {
                if (info.status) {
                    $('#img').val(info.response);
                    $('#imgUpload').attr("src","${imgPath!}"+info.response);
                }
            }
        });
    }

    /**
     * 上传文章组图
     * @param elementId
     */
    function initImagesConfig(elementId) {

        extractUpload({
            browseElementId: elementId,
            url: '${path!}/uploadPl/news',
            filters: {
                max_file_size: '2mb',
                mime_types: [
                    {title: "图片文件", extensions: "jpg,png"}
                ]
            },
            error: function (up, err) {
                if (err.message.indexOf("文件大小错误") > -1) {
                    layer.msg('您上传的文件超出限制，请重新上传（文件要小于2MB', {time: 1000});
                } else if (err.message.indexOf("文件扩展名错误") > -1) {
                    layer.msg('请上传jpg,png格式的文件', {time: 1000});
                } else {
                    layer.msg('上传错误，请重试', {time: 1000});
                }
            },
            success: function (up, file, info) {
                if (info.status) {
                    <#--$('#img').val(info.response);-->
                    <#--$('#imgUpload').attr("src","${imgPath!}"+info.response);-->

                    $("#imgsUpload").before(template('imagesElementTemplate', {
//                        index: index,
                        value: info.response
                    }));

                }else{
                    layer.msg('上传错误，请重试', {time: 1000})
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
<script type="text/html" id="imagesElementTemplate">
    <div style="display: block;">
        <div class="newsimgs_upload">
            <img src="${imgPath!}{{value}}" style="width:100px;height:50px;margin:0px 0px;">
            <div class="uploadimg_close" style="width:100px;height:50px;margin:0px 0px;display:none;">
                <img src="${path!}/static/img/uploadClose.png">
            </div>
            <input type="text" style="display:none;" name="newsImages.img" value="{{value}}"/>
        </div>
        <div style="float:left">
            <input type="text" name="newsImages.title" required="required">
        </div>
        <div style="clear: both;"></div>
    </div>
</script>
<style>
    .newsimgs_upload { width:100px; height:50px; border-radius:0px; -moz-border-radius:0px; background-color:#e5e5e6; border:1px solid #bfbfbf; float:left;  margin:0 10px 20px 0; position:relative;}
    .newsimgs_upload img{ width:26px; height:26px; margin: 12px 37px 12px 37px; position:absolute; z-index:55;}
    .uploadimg_close { width:100%; height:100%; background-color:#000; opacity:0.7; position:absolute; z-index:99999;}
    .uploadimg_close img{ cursor:pointer;}
</style>
</@layout>
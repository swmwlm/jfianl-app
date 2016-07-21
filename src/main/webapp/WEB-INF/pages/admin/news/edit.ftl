<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="news">
<section class="content-header">
    <h1>
        资讯
        <small>编辑</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/news"><i class="fa fa-tag"></i> 资讯</a></li>
        <li class="active">编辑</li>
    </ol>
</section>
<section class="content">
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">编辑资讯</h3>
        </div>
        <form class="form-horizontal" action="${path!}/admin/news/edit/${news.id!}" method="post" onsubmit="return toValid();">
            <div class="box-body">
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">资讯分类</label>
                    <div class="col-sm-5">
                        <input type="hidden" name="news.id" value="${news.id}" />
                        <select name="news.dictId" id="dictId" class="form-control" required="required">
                            <#list newsCategory as category>
                                <option value="${category.id!}" <#if '${category.id}'=='${news.dictId!}'>selected="selected"</#if>>
                                    ${category.value!}
                                </option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="target" class="col-sm-2 control-label">打开方式</label>
                    <div class="col-sm-5">
                        <select name="news.target" id="target" class="form-control" required="required">
                            <#list targetCategory as target>
                                <option value="${target.key!}" <#if '${target.key}'=='${news.target!}'>selected="selected"</#if>>
                                    ${target.value!}
                                </option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">标题</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="title" name="news.title" placeholder="标题" value="${news.title!}" required="required">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">摘要</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="introduction" name="news.introduction" placeholder="摘要" value="${news.introduction}" required="required">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">是否为外链</label>
                    <div class="col-sm-5">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <input type="checkbox" id="isExternalHrefChk" <#if news.isExternalHref==1 >checked="checked"</#if> onchange="showExternalHref();">
                                <input type="hidden" id="isExternalHref" name="news.isExternalHref" value="${news.isExternalHref}" />
                            </span>
                            <input class="form-control" type="text" <#if news.isExternalHref==0 >readonly="readonly"</#if> id="externalHref" name="news.externalHref" placeholder="外链地址,形如:http://abc.com" value="${news.externalHref!}">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="img" class="col-sm-2 control-label">资讯摘要图</label>
                    <div class="col-sm-6">
                        <#if news.img ??>
                            <img src="${imgPath!}${news.img}" id="imgUpload" style="width:100px;height: 100px;" />
                        <#else>
                            <img src="${path!}/static/img/upload.png" id="imgUpload" />
                        </#if>
                        <input name="news.img" id="img" type="hidden" value="${news.img!}"/>
                    </div>
                </div>
                <#--<#if news.isExternalHref==0>-->
                    <div class="form-group" id="newsImgsDiv">
                    <label for="img" class="col-sm-2 control-label">资讯组图</label>
                    <div class="col-sm-3">
                        <#if newsImages ??>
                            <#list newsImages as image>
                            <div style="display: block;">
                                <div class="newsimgs_upload">
                                    <img src="${imgPath!}${image.img}" style="width:100px;height:50px;margin:0px 0px;">
                                    <div class="uploadimg_close" style="width:100px;height:50px;margin:0px 0px;display:none;">
                                        <img src="${path!}/static/img/uploadClose.png">
                                        <input type="text" style="display:none;" flag="newsImagesId" name="newsImages[${image_index}].id" value="${image.id}"/>
                                        <input type="text" style="display:none;" name="newsImages[${image_index}].img" value="${image.img}"/>
                                        <input type="text" style="display:none;" name="newsImages[${image_index}].newsId" value="${image.newsId}"/>
                                    </div>
                                </div>
                                <div style="float:left">
                                    <input type="text" name="newsImages[${image_index}].title" required="required" value="${image.title}">
                                </div>
                                <div style="clear: both;"></div>
                            </div>
                            </#list>
                        </#if>
                        <input type="text" style="display:none;" id="newsImagesIDS" name="newsImagesIDS" value=""/>
                        <img src="${path!}/static/img/upload.png" id="imgsUpload" style="display: block;clear:both;" />
                    </div>
                    <a href="javascript:void(0);" id="triggerFileBrowser"></a>
                </div>
                <div class="form-group" id="contentDiv">
                    <label for="name" class="col-sm-2 control-label">资讯内容</label>
                    <div class="col-sm-8">
                        <textarea class="form-control" id="content" name="news.content" placeholder="资讯内容" rows="20">${news.content!}</textarea>
                    </div>
                </div>
                <#--</#if>-->
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">作者</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="author" name="news.author" placeholder="作者" value="${news.author!}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">文章来源</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="source" name="news.source" placeholder="文章来源" value="${news.source!}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">发布时间</label>
                    <div class="col-sm-5">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input class="form-control pull-right" type="text" id="releaseTime" name="news.releaseTime" placeholder="发布时间" readonly="readonly" value="${news.releaseTime}" />
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">浏览量</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="view" name="news.view" placeholder="初始浏览量,例如:222" required="required" value="${news.view!'0'}">
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
    var index=-1;
    $(function () {
        var imageSize=${newsImages?size};
        if(imageSize>0){
            index=imageSize-1;
        }
        initLayDate();
        uploadImg();
        initWangEditor();
        initImagesConfig('triggerFileBrowser');

        if(${news.isExternalHref}==1){
            $("#isExternalHrefChk").change();
        }
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
            var newsImagesId=$(this).next("input[flag='newsImagesId']");
            var newsImagesIDSObj=$("#newsImagesIDS");
            if(newsImagesId.length>0){
                newsImagesIDSObj.val(newsImagesIDSObj.val()+','+newsImagesId.val());
            }
            var $imgContainer = $(this).parent().parent().parent();
            $imgContainer.remove();
        });
    });
    function initLayDate() {
        laydate({
            elem: '#releaseTime',
            format: 'YYYY-MM-DD hh:mm:ss', // 分隔符可以任意定义，该例子表示只显示年月
            festival: true,
            istoday: true,
            start: laydate.now(0, "YYYY-MM-DD hh:mm:ss"),
            istime: true,
            min: laydate.now(0,"YYYY-MM-DD hh:mm:ss"),
            max: laydate.now(365)
        });
    }
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
            browseElementId: 'imgUpload',
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
                    index+=1;//上传成功,索引+1
                    $("#imgsUpload").before(template('imagesElementTemplate', {
                        index:index,
                        value: info.response
                    }));
                }else{
                    layer.msg('上传错误，请重试', {time: 1000})
                }
            }
        });
    }



    function showExternalHref() {
        if($("#isExternalHrefChk").is(':checked')){
            $("#externalHref").removeAttr("readonly");
            $("#contentDiv").hide();
            $("#newsImgsDiv").hide();
            $("#isExternalHref").val("1");
        }else{
            $("#externalHref").attr("readonly","readonly");
            $("#contentDiv").show();
            $("#newsImgsDiv").show();
            $("#isExternalHref").val("0");

        }
    }
    function toValid() {
        if($("#releaseTime").val()==""){
            layer.msg('发布时间必须填写', {time: 1000});
            return false;
        }
        if($("#isExternalHrefChk").is(':checked')){
            if($("#externalHref").val()==""){
                layer.msg('外链地址必须填写', {time: 1000});
                return false;
            }
            if(!isURL($("#externalHref").val())){
                layer.msg('外链地址不合法', {time: 1000});
                return false;
            }
        }

    }
    // url正则匹配
    function isURL(str){
        return !!str.match(/(((^https?:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)$/g);
    }
</script>
<script type="text/html" id="imagesElementTemplate">
    <div style="display: block;">
        <div class="newsimgs_upload">
            <img src="${imgPath!}{{value}}" style="width:100px;height:50px;margin:0px 0px;">
            <div class="uploadimg_close" style="width:100px;height:50px;margin:0px 0px;display:none;">
                <img src="${path!}/static/img/uploadClose.png">
                <input type="text" style="display:none;" name="newsImages[{{index}}].img" value="{{value}}"/>
            </div>
        </div>
        <div style="float:left">
            <input type="text" name="newsImages[{{index}}].title" required="required">
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
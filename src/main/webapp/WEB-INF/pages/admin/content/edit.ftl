<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="content">
<section class="content-header">
    <h1>
        内容
        <small>编辑</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/content"><i class="fa fa-tag"></i> 内容</a></li>
        <li class="active">编辑</li>
    </ol>
</section>
<section class="content">
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">编辑内容</h3>
        </div>
        <form class="form-horizontal" action="${path!}/admin/content/edit/${content.id!}" method="post" onsubmit="return toValid();">
            <div class="box-body">
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">内容分类</label>
                    <div class="col-sm-5">
                        <input type="hidden" name="content.id" value="${content.id}" />
                        <select name="content.dictId" id="dictId" class="form-control" required="required">
                            <#list categories as category>
                                <option value="${category.id!}" <#if '${category.id}'=='${content.dictId!}'>selected="selected"</#if>>
                                    ${category.value!}
                                </option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="target" class="col-sm-2 control-label">打开方式</label>
                    <div class="col-sm-5">
                        <select name="content.target" id="target" class="form-control" required="required">
                            <#list targetCategory as target>
                                <option value="${target.key!}" <#if '${target.key}'=='${content.target!}'>selected="selected"</#if>>
                                    ${target.value!}
                                </option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">标题</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="title" name="content.title" placeholder="标题" value="${content.title!}" required="required">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">摘要</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="introduction" name="content.introduction" placeholder="摘要" value="${content.introduction}" required="required">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">是否为外链</label>
                    <div class="col-sm-5">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <input type="checkbox" id="isExternalHrefChk" <#if content.isExternalHref==1 >checked="checked"</#if> onchange="showExternalHref();">
                                <input type="hidden" id="isExternalHref" name="content.isExternalHref" value="${content.isExternalHref}" />
                            </span>
                            <input class="form-control" type="text" <#if content.isExternalHref==0 >readonly="readonly"</#if> id="externalHref" name="content.externalHref" placeholder="外链地址,形如:http://abc.com" value="${content.externalHref!}">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="img" class="col-sm-2 control-label">内容摘要图</label>
                    <div class="col-sm-6">
                        <#if content.img ??>
                            <img src="${imgPath!}${content.img}" id="imgUpload" style="max-width: 100px;cursor: pointer;" />
                        <#else>
                            <img src="${path!}/static/img/upload.png" id="imgUpload" style="max-width: 100px;cursor: pointer;" />
                        </#if>
                        <input name="content.img" id="img" type="hidden" value="${content.img!}"/>
                    </div>
                </div>
                <div class="form-group" id="contentImgsDiv">
                    <label for="img" class="col-sm-2 control-label">内容组图</label>
                    <div class="col-sm-3">
                        <#if contentImages ??>
                            <#list contentImages as image>
                            <div style="display: block;">
                                <div class="contentimgs_upload">
                                    <img src="${imgPath!}${image.img}" style="width:100px;height:50px;margin:0px 0px;">
                                    <div class="uploadimg_close" style="width:100px;height:50px;margin:0px 0px;display:none;">
                                        <img src="${path!}/static/img/uploadClose.png">
                                        <input type="text" style="display:none;" flag="contentImagesId" name="contentImages[${image_index}].id" value="${image.id}"/>
                                        <input type="text" style="display:none;" name="contentImages[${image_index}].img" value="${image.img}"/>
                                        <input type="text" style="display:none;" name="contentImages[${image_index}].contentId" value="${image.contentId}"/>
                                    </div>
                                </div>
                                <div style="float:left">
                                    <input type="text" name="contentImages[${image_index}].title" required="required" value="${image.title}">
                                </div>
                                <div style="clear: both;"></div>
                            </div>
                            </#list>
                        </#if>
                        <input type="text" style="display:none;" id="contentImagesIDS" name="contentImagesIDS" value=""/>
                        <img src="${path!}/static/img/upload.png" id="imgsUpload" style="display: block;clear:both;" />
                    </div>
                    <a href="javascript:void(0);" id="triggerFileBrowser"></a>
                </div>
                <div class="form-group" id="contentDiv">
                    <label for="name" class="col-sm-2 control-label">内容</label>
                    <div class="col-sm-8">
                        <textarea class="form-control" id="content" name="content.content" placeholder="内容" rows="20">${content.content!}</textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">作者</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="author" name="content.author" placeholder="作者" value="${content.author!}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">内容来源</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="source" name="content.source" placeholder="内容来源" value="${content.source!}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">发布时间</label>
                    <div class="col-sm-5">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input class="form-control pull-right" type="text" id="releaseTime" name="content.releaseTime" placeholder="发布时间" readonly="readonly" value="${content.releaseTime}" />
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">浏览量</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="view" name="content.view" placeholder="初始浏览量,例如:222" required="required" value="${content.view!'0'}">
                    </div>
                </div>
            </div>
            <div class="box-footer">
                <button type="submit" class="btn btn-raised btn-info pull-right">保存</button>
            </div>
        </form>
    </div>
</section>

<script type="text/javascript">
    var index=-1;
    $(function () {
        var imageSize=${contentImages?size};
        if(imageSize>0){
            index=imageSize-1;
        }
        initLayDate();
        uploadImg();
        initWangEditor();
        initImagesConfig('triggerFileBrowser');

        if(${content.isExternalHref}==1){
            $("#isExternalHrefChk").change();
        }
        $(document).on('click', '#imgsUpload', function () {
            if ($('div.contentimgs_upload').length == 10) {
                layer.msg('最多允许上传10张图片!', {time: 1000});
                return false;
            }
            document.getElementById('triggerFileBrowser').click();
        });
        $(document).on('mouseenter', 'div.contentimgs_upload', function () {
            var $deleteImg = $(this).find('.uploadimg_close');
            if ($deleteImg == null) {
                return false;
            }
            $deleteImg.show();
        });
        $(document).on('mouseleave', 'div.contentimgs_upload', function () {
            var $deleteImg = $(this).find('.uploadimg_close');
            if ($deleteImg == null) {
                return false;
            }
            $deleteImg.hide();
        });
        $(document).on('click', 'div.uploadimg_close>img', function () {
            var contentImagesId=$(this).next("input[flag='contentImagesId']");
            var contentImagesIDSObj=$("#contentImagesIDS");
            if(contentImagesId.length>0){
                contentImagesIDSObj.val(contentImagesIDSObj.val()+','+contentImagesId.val());
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
        extractWangEditor("content");
    }
    /**
     * 上传内容缩略图
     */
    function uploadImg() {
        extractUpload({
            browseElementId: 'imgUpload',
            url: '${path!}/uploadPl/content',
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
     * 上传内容组图
     * @param elementId
     */
    function initImagesConfig(elementId) {
        extractUpload({
            browseElementId: elementId,
            url: '${path!}/uploadPl/content',
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
            $("#contentImgsDiv").hide();
            $("#isExternalHref").val("1");
        }else{
            $("#externalHref").attr("readonly","readonly");
            $("#contentDiv").show();
            $("#contentImgsDiv").show();
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
</script>
<script type="text/html" id="imagesElementTemplate">
    <div style="display: block;">
        <div class="contentimgs_upload">
            <img src="${imgPath!}{{value}}" style="width:100px;height:50px;margin:0px 0px;">
            <div class="uploadimg_close" style="width:100px;height:50px;margin:0px 0px;display:none;">
                <img src="${path!}/static/img/uploadClose.png">
                <input type="text" style="display:none;" name="contentImages[{{index}}].img" value="{{value}}"/>
            </div>
        </div>
        <div style="float:left">
            <input type="text" name="contentImages[{{index}}].title" required="required">
        </div>
        <div style="clear: both;"></div>
    </div>
</script>
<style>
    .contentimgs_upload { width:100px; height:50px; border-radius:0px; -moz-border-radius:0px; background-color:#e5e5e6; border:1px solid #bfbfbf; float:left;  margin:0 10px 20px 0; position:relative;}
    .contentimgs_upload img{ width:26px; height:26px; margin: 12px 37px 12px 37px; position:absolute; z-index:55;}
    .uploadimg_close { width:100%; height:100%; background-color:#000; opacity:0.7; position:absolute; z-index:99999;}
    .uploadimg_close img{ cursor:pointer;}
</style>
</@layout>
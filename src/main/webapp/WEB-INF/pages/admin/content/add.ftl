<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="content">
<section class="content-header">
    <h1>
        內容
        <small>添加</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/content"><i class="fa fa-tag"></i> 內容</a></li>
        <li class="active">添加</li>
    </ol>
</section>
<section class="content">
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">创建內容</h3>
        </div>
        <form class="form-horizontal" action="add" method="post" onsubmit="return toValid();">
            <div class="box-body">
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">內容分类</label>
                    <div class="col-sm-5">
                        <select name="content.dictId" id="dictId" class="form-control" required="required">
                            <#list categories as category>
                                <option value="${category.id!}">
                                    [${category.type!}]${category.value!}
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
                                <option value="${target.key!}">${target.value!}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">标题</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="title" name="content.title" placeholder="标题" required="required">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">摘要</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="introduction" name="content.introduction" placeholder="摘要" required="required">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">是否为外链</label>
                    <div class="col-sm-5">
                        <div class="input-group">
                            <span class="input-group-addon">
                              <input type="checkbox" id="isExternalHrefChk" onchange="showExternalHref();">
                                <input type="hidden" id="isExternalHref" name="content.isExternalHref" value="0" />
                            </span>
                            <input class="form-control" type="text" readonly="readonly" id="externalHref" name="content.externalHref" placeholder="外链地址,形如:http://abc.com">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="img" class="col-sm-2 control-label">內容摘要图</label>
                    <div class="col-sm-6">
                        <img src="${path!}/static/img/upload.png" id="imgUpload" />
                        <input name="content.img" id="img" type="hidden" value=""/>
                    </div>
                </div>
                <div class="form-group" id="contentImgsDiv">
                    <label for="img" class="col-sm-2 control-label">內容组图</label>
                    <div class="col-sm-3">
                        <img src="${path!}/static/img/upload.png" id="imgsUpload" style="display: block;clear:both;" />
                    </div>
                    <a href="javascript:void(0);" id="triggerFileBrowser"></a>
                </div>
                <div class="form-group" id="contentFilesDiv">
                    <label for="img" class="col-sm-2 control-label">附件</label>
                    <div class="col-sm-3">
                        <img src="${path!}/static/img/upload.png" id="filesUpload" style="display: block;clear:both;" />
                    </div>
                    <a href="javascript:void(0);" id="triggerFileBrowserFuJian"></a>
                </div>
                <div class="form-group" id="contentDiv">
                    <label for="name" class="col-sm-2 control-label">内容</label>
                    <div class="col-sm-8">
                        <textarea class="form-control" id="content" name="content.content" placeholder="内容" rows="20"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">作者</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="author" name="content.author" placeholder="作者">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">内容来源</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="source" name="content.source" placeholder="内容来源">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">发布时间</label>
                    <div class="col-sm-5">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input class="form-control pull-right" type="text" id="releaseTime" name="content.releaseTime" placeholder="发布时间" readonly="readonly" />
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">浏览量</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="view" name="content.view" placeholder="初始浏览量,例如:222" required="required">
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
    var fileIndex=-1;
    $(function () {
        initLayDate();
        uploadImg();
        initWangEditor();
        initImagesConfig('triggerFileBrowser');
        initFilesConfig('triggerFileBrowserFuJian');
        $(document).on('click', '#imgsUpload', function () {
            if ($('div.contentimgs_upload').length == 10) {
                layer.msg('最多允许上传10张图片!', {time: 1000});
                return false;
            }
            document.getElementById('triggerFileBrowser').click();
        });
        $(document).on('click', '#filesUpload', function () {
            if ($('div.contentFiles_upload').length == 10) {
                layer.msg('最多允许上传10个附件!', {time: 1000});
                return false;
            }
            document.getElementById('triggerFileBrowserFuJian').click();
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
            var $imgContainer = $(this).parent().parent().parent();
            $imgContainer.remove();
        });
        $(document).on('click', 'div.contentFiles_upload>img', function () {
            var $fileContainer = $(this).parent().parent();
            $fileContainer.remove();
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
                    layer.msg('请上传jpg,png,jpeg格式的文件', {time: 1000});
                } else {
                    layer.msg('上传错误，请重试', {time: 1000});
                }
            },
            success: function (up, file, info) {
                if (info.status) {
                    $('#img').val(info.response);
                    $('#imgUpload').attr("src","${imgPath!}"+info.response);
                    $('#imgUpload').attr("style","width:100px;height: 100px;");
                }
            }
        });
    }

    /**
     * 上传內容组图
     * @param elementId
     */
    function initImagesConfig(elementId) {

        extractUpload({
            browseElementId: elementId,
            url: '${path!}/uploadPl/content',
            filters: {
                max_file_size: '2mb',
                mime_types: [
                    {title: "图片文件", extensions: "jpg,png,jpeg,JPG,PNG,JPEG"}
                ]
            },
            error: function (up, err) {
                if (err.message.indexOf("文件大小错误") > -1) {
                    layer.msg('您上传的文件超出限制，请重新上传（文件要小于2MB', {time: 1000});
                } else if (err.message.indexOf("文件扩展名错误") > -1) {
                    layer.msg('请上传jpg,png,jpeg格式的文件', {time: 1000});
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


    /**
     * 上传附件
     * @param elementId
     */
    function initFilesConfig(elementId) {

        extractUpload({
            browseElementId: elementId,
            url: '${path!}/uploadPl/content',
            filters: {
                max_file_size: '20mb'
            },
            error: function (up, err) {
                if (err.message.indexOf("文件大小错误") > -1) {
                    layer.msg('您上传的文件超出限制，请重新上传（文件要小于20MB', {time: 1000});
                } else if (err.message.indexOf("文件扩展名错误") > -1) {
                    layer.msg('请上传正确格式的文件', {time: 1000});
                } else {
                    layer.msg('上传错误，请重试', {time: 1000});
                }
            },
            success: function (up, file, info) {
                if (info.status) {
                    fileIndex+=1;//上传成功,索引+1
                    $("#filesUpload").before(template('filesElementTemplate', {
                        filename:file.name,
                        index:fileIndex,
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
            $("#contentFilesDiv").hide();
            $("#isExternalHref").val("1");
        }else{
            $("#externalHref").attr("readonly","readonly");
            $("#contentDiv").show();
            $("#contentImgsDiv").show();
            $("#contentFilesDiv").show();
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
            </div>
            <input type="text" style="display:none;" name="contentImages[{{index}}].img" value="{{value}}"/>
        </div>
        <div style="float:left">
            <input type="text" name="contentImages[{{index}}].title" required="required">
        </div>
        <div style="clear: both;"></div>
    </div>
</script>
<script type="text/html" id="filesElementTemplate">
    <div style="display: block;">
        <div class="contentFiles_upload">
            {{filename}}&nbsp;&nbsp;&nbsp;&nbsp;<img src="${path!}/static/img/uploadClose.png"
                                                     style="background-color: #666666;border-radius: 50%">
            <input type="text" style="display:none;" name="contentFiles[{{index}}].file" value="{{value}}"/>
            <br/>
            <input type="text" name="contentFiles[{{index}}].title" required="required" value="{{filename}}" style="width: 300px;">
        </div>
        <div style="clear: both;"></div>
    </div>
</script>
<style>
    .contentimgs_upload { width:100px; height:50px; border-radius:0px; -moz-border-radius:0px; background-color:#e5e5e6; border:1px solid #bfbfbf; float:left;  margin:0 10px 20px 0; position:relative;}
    .contentimgs_upload img{ width:26px; height:26px; margin: 12px 37px 12px 37px; position:absolute; z-index:55;}
    .uploadimg_close { width:100%; height:100%; background-color:#000; opacity:0.7; position:absolute; z-index:99999;}
    .uploadimg_close img{ cursor:pointer;}

    .contentFiles_upload { width:400px; height:50px; border-radius:0px; -moz-border-radius:0px; float:left;  margin:0 10px 20px 0; position:relative;}

</style>
</@layout>
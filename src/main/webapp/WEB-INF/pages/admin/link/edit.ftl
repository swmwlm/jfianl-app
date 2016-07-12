<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="link">
<section class="content-header">
    <h1>
        友链
        <small>编辑</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/link"><i class="fa fa-tag"></i> 友链</a></li>
        <li class="active">编辑</li>
    </ol>
</section>
<section class="content">
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">编辑友链</h3>
        </div>
        <form class="form-horizontal" action="edit" method="post">
            <input type="hidden" name="link.id" value="${link.id!}">
            <div class="box-body">
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="name" name="link.name" value="${link.name!}" placeholder="名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="url" class="col-sm-2 control-label">访问地址</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="url" name="link.url" value="${link.url!}" placeholder="访问地址">
                    </div>
                </div>
                <div class="form-group">
                    <label for="img" class="col-sm-2 control-label">图片</label>
                    <div class="col-sm-6" id="pickfiles">
                        <#if link.img??>
                            <img src="${imgPath!}/${link.img!}" id="imgUpload" />
                        <#else>
                            <img src="${path!}/static/img/upload.png" id="imgUpload" />
                        </#if>
                        <input name="link.img" id="img" type="hidden" value="${link.img!}"/>
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
<script type="text/javascript">
    $(function () {
        uploadImg();
    });
    function uploadImg() {
        extractUpload({
            browseElementId: 'pickfiles',
            url: '${path!}/uploadPl/link',
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
</script>
</@layout>
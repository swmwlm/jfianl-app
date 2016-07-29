<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="link">
<section class="content-header">
    <h1>
        友链
        <small>添加</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/link"><i class="fa fa-tag"></i> 友链</a></li>
        <li class="active">添加</li>
    </ol>
</section>
<section class="content">
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">创建友链</h3>
        </div>
        <form class="form-horizontal" action="add" method="post" onsubmit="return toValid();">
            <div class="box-body">
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="name" name="link.name" placeholder="名称" required="required">
                    </div>
                </div>
                <div class="form-group">
                    <label for="url" class="col-sm-2 control-label">访问地址</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="url" name="link.url" placeholder="访问地址,形如:http://abc.com" required="required">
                    </div>
                </div>
                <div class="form-group">
                    <label for="img" class="col-sm-2 control-label">图片</label>
                    <div class="col-sm-6">
                        <img src="${path!}/static/img/upload.png" id="imgUpload" style="max-width: 100px;cursor: pointer;" />
                        <input name="link.img" id="img" type="hidden" value=""/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="url" class="col-sm-2 control-label">描述</label>
                    <div class="col-sm-6">
                        <textarea class="form-control" rows="3" id="description" name="link.description"
                                  placeholder="描述信息"></textarea>
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
    $(function () {
        uploadImg();
    });
    function uploadImg() {
        extractUpload({
            browseElementId: 'imgUpload',
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
    function toValid() {
        var $url=$("#url");
        if(!isURL($url.val())){
            layer.msg('链接地址不合法', {time: 1000});
            $url.focus();
            return false;
        }
    }
</script>
</@layout>
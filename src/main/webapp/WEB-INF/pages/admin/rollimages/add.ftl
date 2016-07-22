<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="rollimages">
<section class="content-header">
    <h1>
        轮播图
        <small>添加</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/rollimages"><i class="fa fa-tag"></i> 轮播图</a></li>
        <li class="active">添加</li>
    </ol>
</section>
<section class="content">
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">创建轮播图</h3>
        </div>
        <form class="form-horizontal" action="add" method="post" onsubmit="return toValid();">
            <div class="box-body">

                <div class="form-group">
                    <label for="show_status" class="col-sm-2 control-label">轮播图类型</label>

                    <div class="col-sm-5">
                        <select name="rollImages.dictId" id="dictId" class="form-control">
                            <#list rollimageDict! as dict>
                                <option value="${dict.id!}">${dict.value!}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">标题</label>

                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="title" name="rollImages.title" maxlength="100"
                               placeholder="标题">
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">简介</label>

                    <div class="col-sm-5">
                        <textarea class="form-control" rows="3" id="introduction" name="rollImages.introduction"
                                  placeholder="简介"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="tab" class="col-sm-2 control-label">图片</label>

                    <div class="col-sm-5" id="pickfiles">
                        <img src="${path!}/static/img/upload.png" id="imgUpload"
                             style="max-width: 500px;cursor: pointer;"/>
                        <input name="rollImages.imgSrc" id="imgSrc" type="hidden" value=""/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="tab" class="col-sm-2 control-label">链接url</label>

                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="linkUrl" name="rollImages.linkUrl"
                               placeholder="形如:http://abc.com">
                    </div>
                </div>
                <div class="form-group">
                    <label for="tab" class="col-sm-2 control-label">打开方式</label>

                    <div class="col-sm-5">
                        <select name="rollImages.target" id="target" class="form-control">
                            <#list targetDict! as dict>
                                <option value="${dict.key!}">${dict.value!}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="tab" class="col-sm-2 control-label">排序</label>

                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="sort" name="rollImages.sort" maxlength="3"
                               placeholder="排序">
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
    var index = -1;
    $(function () {
        uploadImg();
    });

    /**
     * 上传图片
     */
    function uploadImg() {
        extractUpload({
            browseElementId: 'pickfiles',
            url: '${path!}/uploadPl/rollimages',
            filters: {
                max_file_size: '5mb',
                multi_selection: false,
                mime_types: [
                    {title: "图片文件", extensions: "jpg,png,jpeg,JPG,PNG,JPEG"},
                ]
            },
            error: function (up, err) {
                if (err.message.indexOf("文件大小错误") > -1) {
                    layer.msg('您上传的文件超出限制，请重新上传（文件要小于5MB', {time: 1000});
                } else if (err.message.indexOf("文件扩展名错误") > -1) {
                    layer.msg('请上传jpg,png格式的文件', {time: 1000});
                } else {
                    layer.msg('上传错误，请重试', {time: 1000});
                }
            },
            success: function (up, file, info) {
                if (info.status) {
                    $('#imgSrc').val(info.response);
                    $('#imgUpload').attr("src", "${imgPath!}" + info.response);
                }
            }
        });
    }

    function toValid() {
        if ($("#introduction").val().length > 250) {
            layer.msg('简介不允许多于250字', {time: 1000});
            return false;
        }
        if ($("#imgSrc").val() == "") {
            layer.msg('请上传图片', {time: 1000});
            return false;
        }
        if ($("#sort").val() == "") {
            layer.msg('请填写排序', {time: 1000});
            return false;
        } else {
            if (isNaN($("#sort").val())) {
                layer.msg('排序只允许输入数字', {time: 1000});
                return false;
            }
        }
    }

</script>

</@layout>
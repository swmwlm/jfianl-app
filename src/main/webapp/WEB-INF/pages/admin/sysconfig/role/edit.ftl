<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="label">
<link rel="stylesheet" href="${path!}/static/AdminLTE/plugins/iCheck/square/blue.css">
<section class="content-header">
    <h1>
        设置
        <small>角色编辑</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/role">角色</a></li>
        <li class="active">编辑</li>
    </ol>
</section>
<section class="content">
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">编辑角色</h3>
        </div>
        <form class="form-horizontal" action="edit" method="post">
            <input type="hidden" name="role.id" value="${role.id!}"/>
            <div class="box-body">
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="name" name="role.name" value="${role.name!}" placeholder="名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="description" class="col-sm-2 control-label">描述</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="description" name="role.description" value="${role.description!}" placeholder="标签描述">
                    </div>
                </div>
                <#list permissions as permission>
                    <div class="form-group">
                        <label for="" class="col-sm-2 control-label">${permission.description!}</label>
                        <div class="col-sm-10" style="padding-top: 4px;">
                            <#list permission.childPermission as p>
                                <input type="checkbox" name="permissions" id="permission_${p.id}" value="${p.id}">
                                <label for="permission_${p.id}">${p.description!}</label>
                            </#list>
                        </div>
                    </div>
                </#list>
            </div>
            <div class="box-footer">
                <button type="submit" class="btn btn-raised btn-info pull-right">保存</button>
            </div>
        </form>
    </div>
</section>
<script src="${path!}/static/AdminLTE/plugins/iCheck/icheck.min.js"></script>
<script>
    $(function () {

        <#list rolePermissions as rp>
            $("#permission_${rp.pid}").attr("checked", true);
        </#list>

        $("input[type='checkbox']").iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
    });
</script>
</@layout>

<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="adminuser">
<link rel="stylesheet" href="${path!}/static/AdminLTE/plugins/iCheck/square/blue.css">
<section class="content-header">
    <h1>
        设置
        <small>用户添加</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/adminuser"> 后台用户</a></li>
        <li class="active">添加</li>
    </ol>
</section>
<section class="content">
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">添加后台用户</h3>
        </div>
        <form class="form-horizontal" action="add" method="post">
            <div class="box-body">
                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-5">
                        <input style="display:none"><!-- for disable autocomplete on chrome -->
                        <input type="text" class="form-control" id="username" name="username" placeholder="用户名" value="">
                    </div>
                </div>
                <div class="form-group">
                    <label for="description" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-5">
                        <input type="password" class="form-control" id="password" name="password" placeholder="密码" value="">
                    </div>
                </div>
                <div class="form-group">
                    <label for="description" class="col-sm-2 control-label">角色</label>
                    <div class="col-sm-10" style="padding-top: 4px;">
                        <#list roles as role>
                            <input type="checkbox" name="roles" id="role_${role.id}" value="${role.id}">
                            <label for="role_${role.id}">${role.description!}</label>
                        </#list>
                    </div>
                </div>
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
        $("input[type='checkbox']").iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
    });
</script>
</@layout>

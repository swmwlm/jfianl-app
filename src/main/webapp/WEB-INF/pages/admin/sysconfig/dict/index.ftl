<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="dict">
<section class="content-header">
    <h1>
        字典
        <small>字典列表</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/dict"><i class="fa fa-tag"></i> 字典设置</a></li>
        <li class="active">列表</li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="box-header">
            <form class="form-inline" method="get" action="${path!}/admin/dict">
                <div class="form-group">
                    <input type="text" class="form-control" name="name" value="${name!}" placeholder="字典值"/>
                </div>
                <button type="submit" class="btn btn-raised btn-default ">搜索</button>
                <@shiro.hasPermission name="setting:dict">
                    <a href="${path!}/admin/dict/add" class="btn btn-raised btn-default  pull-right">添加</a>
                </@shiro.hasPermission>
            </form>
        </div>
        <div class="box-body">
            <div class="dataTables_wrapper form-inline dt-bootstrap">
                <table class="table table-hover table-bordered">
                    <thead>
                    <th>type</th>
                    <th>key</th>
                    <th>value</th>
                    <th>排序</th>
                    <th>备注</th>
                    <th>操作</th>
                    </thead>
                    <tbody>
                        <#list page.getList() as dict>
                        <tr id="dict_${dict.id!}">
                            <td>${dict.type!}</td>
                            <td>${dict.key!}</td>
                            <td>${dict.value!}</td>
                            <td>${dict.sort!}</td>
                            <td>${dict.remark!}</td>
                            <td>
                                <@shiro.hasPermission name="setting:dict">
                                    <a href="${path!}/admin/dict/edit/${dict.id!}">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </a>
                                    <a href="javascript:deleteDict(${dict.id!});">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </a>
                                </@shiro.hasPermission>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <div class="row">
                    <div class="col-sm-5">
                        <div class="dataTables_info" id="example2_info" role="status" aria-live="polite">
                            总字典数：${page.getTotalRow()}</div>
                    </div>
                    <div class="col-sm-7">
                        <div class="dataTables_paginate paging_simple_numbers">
                            <#include "/WEB-INF/pages/admin/common/_paginate.ftl"/>
                        <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="${path!}/admin/dict/index" urlParas="" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script>
    function deleteDict(id) {
        if (confirm("确定 删除字典 吗？")) {
            $.ajax({
                url: "${path!}/admin/dict/delete",
                async: false,
                cache: false,
                type: 'post',
                dataType: "json",
                data: {
                    id: id
                },
                success: function (data) {
                    if (data.code == '200') {
                        $("#dict_" + id).remove();
                    } else {
                        alert(data.description);
                    }
                }
            });
        }
    }
</script>
</@layout>

<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="label">
<section class="content-header">
    <h1>
        标签
        <small>列表</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/label"><i class="fa fa-tag"></i> 标签</a></li>
        <li class="active">列表</li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="box-header">
            <form class="form-inline" method="get" action="${path!}/admin/label">
                <div class="form-group">
                    <input type="text" class="form-control" name="name" value="${name!}" placeholder="标签名"/>
                </div>
                <button type="submit" class="btn btn-raised btn-default ">搜索</button>
                <@shiro.hasPermission name="label:add">
                    <a href="${path!}/admin/label/add" class="btn btn-raised btn-default  pull-right">添加</a>
                </@shiro.hasPermission>
            </form>
        </div>
        <div class="box-body">
            <div class="dataTables_wrapper form-inline dt-bootstrap">
                <table class="table table-hover table-bordered">
                    <thead>
                    <th>标签名</th>
                    <th>话题数</th>
                    <th>时间</th>
                    <th>操作</th>
                    </thead>
                    <tbody>
                        <#list page.getList() as label>
                        <tr id="label_${label.id!}">
                            <td>${label.name!}</td>
                            <td>${label.topic_count!}</td>
                            <td>${label.in_time!}</td>
                            <td>
                                <@shiro.hasPermission name="label:edit">
                                    <a href="${path!}/admin/label/edit/${label.id!}">
                                        <span class="glyphicon glyphicon-edit"></span>
                                    </a>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="label:delete">
                                    <#if label.topic_count &lt;= 0>
                                        <a href="javascript:deletelabel(${label.id!});">
                                            <span class="glyphicon glyphicon-trash"></span>
                                        </a>
                                    </#if>
                                </@shiro.hasPermission>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <div class="row">
                    <div class="col-sm-5">
                        <div class="dataTables_info" id="example2_info" role="status" aria-live="polite">
                            总标签数：${page.getTotalRow()}</div>
                    </div>
                    <div class="col-sm-7">
                        <div class="dataTables_paginate paging_simple_numbers">
                            <#include "/WEB-INF/pages/admin/common/_paginate.ftl"/>
                        <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="${path!}/admin/label/index" urlParas="" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script>
    function deletelabel(id) {
        if (confirm("确定 删除标签 吗？)")) {
            $.ajax({
                url: "${path!}/admin/label/delete",
                async: false,
                cache: false,
                type: 'post',
                dataType: "json",
                data: {
                    id: id
                },
                success: function (data) {
                    if (data.code == '200') {
                        $("#label_" + id).remove();
                    } else {
                        alert(data.description);
                    }
                }
            });
        }
    }
</script>
</@layout>

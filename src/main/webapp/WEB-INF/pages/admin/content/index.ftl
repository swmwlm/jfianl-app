<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="content">
<section class="content-header">
    <h1>
        内容
        <small>列表</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/content"><i class="fa fa-tag"></i> 内容</a></li>
        <li class="active">列表</li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="box-header">
            <form class="form-inline" method="get" action="${path!}/admin/content">
                <div class="form-group">
                    <select name="dictId" class="form-control">
                        <option value="">请选择分类</option>
                        <#list categories as category>
                            <option value="${category.id!}" <#if '${category.id}'=='${dictId!}'>selected="selected"</#if>>
                                [${category.type!}]${category.value!}
                            </option>
                        </#list>
                    </select>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" name="name" value="${name!}" placeholder="标题"/>
                </div>
                <button type="submit" class="btn btn-raised btn-default ">搜索</button>
                <@shiro.hasPermission name="content:add">
                    <a href="${path!}/admin/content/add" class="btn btn-raised btn-default  pull-right">添加</a>
                </@shiro.hasPermission>
            </form>
        </div>
        <div class="box-body">
            <div class="dataTables_wrapper form-inline dt-bootstrap">
                <table class="table table-hover table-bordered table-responsive">
                    <thead>
                    <th width="80">摘要图</th>
                    <th>标题</th>
                    <th width="60">作者</th>
                    <th width="80">分类</th>
                    <th width="80">浏览量</th>
                    <th>发布时间</th>
                    <th>操作</th>
                    </thead>
                    <tbody>
                        <#list page.getList() as content>
                        <tr id="content_${content.id!}">
                            <td>
                                <#if content.img ??>
                                    <img src="${imgPath!}${content.img!}" style="width:50px;height: 25px;" />
                                </#if>
                            </td>
                            <td>
                                <#if content.isExternalHref == 1>
                                    <span class="label label-success">外</span>
                                </#if>
                                <a href="${path!}/content/${content.id!}.html" target="_blank">
                                    ${content.title!}
                                </a>
                            </td>
                            <td>${content.author!}</td>
                            <td>${content.categoryName!}</td>
                            <td>
                                ${content.view!}
                            </td>
                            <td>${content.releaseTime!}</td>
                            <td>
                                <@shiro.hasPermission name="content:view">
                                    <a href="${path!}/admin/content/view/${content.id!}"><span
                                        class="glyphicon glyphicon-eye-open" title="预览"></span></a>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="content:edit">
                                    <a href="${path!}/admin/content/edit/${content.id!}"><span
                                            class="glyphicon glyphicon-edit" title="编辑"></span></a>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="content:delete">
                                    <a href="javascript:deleteContent('${content.id}')"><span
                                            class="glyphicon glyphicon-trash" title="删除"></span></a>
                                </@shiro.hasPermission>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <div class="row">
                    <div class="col-sm-5">
                        <div class="dataTables_info" id="example2_info" role="status" aria-live="polite">
                            总记录数：${page.getTotalRow()}</div>
                    </div>
                    <div class="col-sm-7">
                        <div class="dataTables_paginate paging_simple_numbers">
                            <#include "/WEB-INF/pages/admin/common/_paginate.ftl"/>
                            <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="${path!}/admin/content/index" urlParas="" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</@layout>
<script type="text/javascript">
    function deleteContent(id) {
        if (confirm("确定 删除该内容 吗？\n(注：这会删除该内容下的所有相关内容！)")) {
            $.ajax({
                url: "${path!}/admin/content/delete",
                async: false,
                cache: false,
                type: 'post',
                dataType: "json",
                data: {
                    id: id
                },
                success: function (data) {
                    if (data.code == '200') {
                        $("#content_" + id).remove();
                    } else {
                        layer.msg(data.description, {time: 1000});
                    }
                }
            });
        }
    }
</script>

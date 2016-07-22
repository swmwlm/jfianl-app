<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="rollimages">
<section class="content-header">
    <h1>
        轮播图
        <small>列表</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/rollimages"><i class="fa fa-tag"></i> 轮播图</a></li>
        <li class="active">列表</li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="box-header">
            <form class="form-inline" method="get" id="searchForm" action="${path!}/admin/rollimages">
                <div class="form-group">
                    <select name="dictId" id="dictId" class="form-control" onchange="$('#searchForm').submit()">
                        <option value="-1">全部</option>
                        <#list rollimageDict! as dict>
                            <option value="${dict.id!}" <#if dictId=dict.id>selected</#if> >${dict.value!}</option>
                        </#list>
                    </select>
                </div>
                <@shiro.hasPermission name="rollimages:add">
                    <a href="${path!}/admin/rollimages/add" class="btn btn-raised  btn-default pull-right">添加</a>
                </@shiro.hasPermission>
            </form>
        </div>

        <div class="box-body">
            <div class="dataTables_wrapper form-inline dt-bootstrap">
                <table class="table table-hover table-bordered">
                    <thead>
                    <th>轮播图类型</th>
                    <th>标题</th>
                    <th>简介</th>
                    <th>图片</th>
                    <th>链接url</th>
                    <th>打开方式</th>
                    <th>排序</th>
                    <th>创建时间</th>
                    <#--<th>更新时间</th>-->
                    <th>操作</th>
                    </thead>
                    <tbody id="sortable">
                    <#--<#list admin_rollimages as rollimage>-->
                        <#list page.getList()  as rollimage>
                        <tr class="ui-state-default" id="rollimage_${rollimage.id!}">
                            <td>
                                <input type="hidden" name="ids" value="${rollimage.id!}">
                            <#--<#list rollimageDict! as dict>-->
                            <#--<#if dict.id == rollimage.dictId>-->
                            <#--${dict.value!}-->
                            <#--</#if>-->
                            <#--</#list>-->
                            ${rollimage.dictName!}
                            </td>
                            <td>${rollimage.title!}</td>
                            <td>${rollimage.introduction!}</td>
                            <td>
                                <#if rollimage.imgSrc ??>
                                    <a href="${imgPath!}${rollimage.imgSrc!}" target="_blank">
                                        <img src="${imgPath!}${rollimage.imgSrc!}" style="width:50px;height: 25px;"/>
                                    </a>
                                </#if>
                            </td>
                            <td>
                                <#if rollimage.imgSrc ??>
                                    <a href="${rollimage.linkUrl!}" target="_blank">
                                    ${rollimage.linkUrl!}
                                    </a>
                                </#if>
                            </td>
                            <td>
                                 ${rollimage.targetValue!}
                            </td>
                            <td>${rollimage.sort!}</td>
                        <td>${rollimage.createdTime!}</td>
                        <#--<td>${rollimage.updatedTime!}</td>-->
                            <td>
                                <@shiro.hasPermission name="rollimages:edit">
                                    <a href="${path!}/admin/rollimages/edit?id=${rollimage.id!}"><span
                                            class="glyphicon glyphicon-edit"></span></a>
                                </@shiro.hasPermission>
                                <@shiro.hasPermission name="rollimages:delete">
                                    <a href="javascript:deleteRollimages('${rollimage.id!}')"><span
                                            class="glyphicon glyphicon-trash"></span></a>
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
                            <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="${path!}/admin/rollimages/index?dictId=${dictId!}" urlParas="" />
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</section>
<script>
    $(function () {
        $("#sortable").disableSelection();
    });

    function deleteRollimages(id) {
    <#--if (confirm("确定 删除轮播图 吗？")) {-->
    <#--$.ajax({-->
    <#--url: "${path!}/admin/rollimages/delete",-->
    <#--async: false,-->
    <#--cache: false,-->
    <#--type: 'post',-->
    <#--dataType: "json",-->
    <#--data: {-->
    <#--id: id-->
    <#--},-->
    <#--success: function (data) {-->
    <#--if (data.code == '200') {-->
    <#--$("#rollimage_" + id).remove();-->
    <#--} else {-->
    <#--alert(data.description);-->
    <#--}-->
    <#--}-->
    <#--});-->
    <#--}-->

        //询问框
        layer.confirm('确定 删除轮播图 吗？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            $.ajax({
                url: "${path!}/admin/rollimages/delete",
                async: false,
                cache: false,
                type: 'post',
                dataType: "json",
                data: {
                    id: id
                },
                success: function (data) {
                    if (data.code == '200') {
                        $("#rollimage_" + id).remove();
                        layer.msg('删除成功！', {time: 1000});
                    } else {
                        alert(data.description);
                    }
                }
            });
        }, function () {
        });

    }

</script>
</@layout>
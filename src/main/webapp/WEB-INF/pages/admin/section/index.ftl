<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="section">
<section class="content-header">
    <h1>
        板块
        <small>列表</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/section"><i class="fa fa-tag"></i> 板块</a></li>
        <li class="active">列表</li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <@shiro.hasPermission name="section:add">
            <div class="box-header">
                <a href="${path!}/admin/section/add" class="btn btn-raised  btn-default pull-right">添加</a>
            </div>
        </@shiro.hasPermission>
        <div class="box-body">
            <div class="dataTables_wrapper form-inline dt-bootstrap">
                <form id="form-sort" action="${path!}/admin/section/sort" method="post">
                    <table class="table table-hover table-bordered">
                        <thead>
                        <th>名称</th>
                        <th>Tab</th>
                        <th>是否显示</th>
                        <th>默认</th>
                        <th>操作</th>
                        </thead>
                        <tbody id="sortable">
                            <#list admin_sections as section>
                            <tr class="ui-state-default" id="section_${section.id!}">
                                <td>
                                    <input type="hidden" name="ids" value="${section.id!}">
                                ${section.name!}
                                </td>
                                <td>${section.tab!}</td>
                                <td>
                                    <#if section.show_status == 1>
                                        显示
                                    <#elseif section.show_status == 0>
                                        不显示
                                    </#if>
                                </td>
                                <td id="tab_${section.id!}">
                                    <#if section.default_show == 1>
                                        默认
                                    </#if>
                                </td>
                                <td>
                                    <@shiro.hasPermission name="section:setDefault">
                                        <a href="javascript:setDefault(${section.id!}, '${section.tab!}')"><span
                                                class="glyphicon glyphicon-flag"></span></a>
                                    </@shiro.hasPermission>
                                    <@shiro.hasPermission name="section:edit">
                                        <a href="${path!}/admin/section/edit?id=${section.id!}"><span
                                                class="glyphicon glyphicon-edit"></span></a>
                                    </@shiro.hasPermission>
                                    <@shiro.hasPermission name="section:delete">
                                        <a href="javascript:deleteSection('${section.id!}')"><span
                                                class="glyphicon glyphicon-trash"></span></a>
                                    </@shiro.hasPermission>
                                </td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                    <@shiro.hasPermission name="section:sort">
                        <input type="submit" class="btn btn-raised btn-default " value="点击保存排序">
                    </@shiro.hasPermission>
                </form>
            </div>
        </div>
    </div>
</section>
<script>
    $(function () {
        $("#sortable").sortable();
        $("#sortable").disableSelection();
    });

    function deleteSection(id) {
        if (confirm("确定 删除板块 吗？")) {
            $.ajax({
                url: "${path!}/admin/section/delete",
                async: false,
                cache: false,
                type: 'post',
                dataType: "json",
                data: {
                    id: id
                },
                success: function (data) {
                    if (data.code == '200') {
                        $("#section_" + id).remove();
                    } else {
                        alert(data.description);
                    }
                }
            });
        }
    }

    function setDefault(id, tab) {
        if (confirm("确定 设置成默认显示板块 吗？")) {
            $.ajax({
                url: "${path!}/admin/section/setDefault",
                async: false,
                cache: false,
                type: 'post',
                dataType: "json",
                data: {
                    tab: tab
                },
                success: function (data) {
                    if (data.code == '200') {
                        $("td[id^='tab_']").each(function () {
                            $(this).html("");
                        });
                        $("#tab_" + id).html("默认");
                    } else {
                        alert(data.description);
                    }
                }
            });
        }
    }
</script>
</@layout>
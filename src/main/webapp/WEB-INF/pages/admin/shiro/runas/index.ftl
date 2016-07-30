<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="runas">
<section class="content-header">
    <h1>
        切换身份
        <small>列表</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/runas"> 切换身份</a></li>
        <li class="active">列表</li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="box-header">
            <div>${msg!}</div>

            <#if (isRunas?string('true','false')) == "true">
                上一个身份：${previousUsername!}
                |
                <a href="${path!}/admin/runas/switchBack">切换回该身份</a>
            </#if>

        </div>
        <div class="box-body">
            <h3>切换到其他身份：</h3>

            <div class="dataTables_wrapper form-inline dt-bootstrap">
                <table class="table table-hover table-bordered">
                    <thead>
                    <th>用户名</th>
                    <th>操作</th>
                    </thead>
                    <tbody>
                        <#list fromUsers as fromUser>
                        <tr>
                            <td>${fromUser.username!}</td>
                            <td>
                                <a href="${path!}/admin/runas/switchTo/${fromUser.id!}" class="btn btn-sm btn-primary">切换到该身份</a>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
            </div>

            <h3>授予身份给其他人：</h3>
            <div class="dataTables_wrapper form-inline dt-bootstrap">
                <table class="table table-hover table-bordered">
                    <thead>
                    <th>用户名</th>
                    <th>操作</th>
                    </thead>
                    <tbody>
                        <#list allUsers as u>
                        <tr>
                            <td>${u.username!}</td>
                            <td>
                                <#if toUserIds?seq_contains(u.id)?string('true','false')=='true'>
                                    <a href="${path!}/admin/runas/revoke/${u.id!}" class="btn btn-sm btn-primary">回收身份</a></c:when>
                                <#else>
                                    <a href="${path!}/admin/runas/grant/${u.id!}" class="btn btn-sm btn-primary">授予身份</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</section>
</@layout>
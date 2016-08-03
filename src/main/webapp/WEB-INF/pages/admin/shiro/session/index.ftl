<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="session">
<section class="content-header">
    <h1>
        后台会话
        <small>列表</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/session"><i class="fa fa-tag"></i> 后台会话</a></li>
        <li class="active">列表</li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <div class="box-header">
            <div>${msg!}</div>
        </div>
        <div class="box-body">

            当前在线人数：${sessionCount}人<br/>

            <div class="dataTables_wrapper form-inline dt-bootstrap">
                <table class="table table-hover table-bordered">
                    <thead>
                    <th style="width: 320px;">会话ID</th>
                    <th>用户名</th>
                    <th>主机地址</th>
                    <th>最后访问时间</th>
                    <th>已强制退出</th>
                    <th>操作</th>
                    </thead>
                    <tbody>
                        <#list sessions as sess>
                        <tr>
                            <td>${sess.id!}</td>
                            <td>${sess.attributes.name!}</td>
                            <td>${sess.host!}</td>
                            <td>${sess.lastAccessTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                            <td>${sess.attributes.isForceLogout?string('是','否')}</td>
                            <td>
                                <#if sess.attributes.isForceLogout?string('true','false')=='false'>
                                    <a href="${path!}/admin/session/forceLogout?id=${sess.id}">强制退出</a>
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
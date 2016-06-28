<#include "/WEB-INF/pages/front/common/_layout.ftl"/>
<@html title="未读消息 - ${siteTitle!}" description="未读消息" page_tab="message" sidebar_user_info="show" sidebar_create="show">
<div class="panel panel-default">
    <div class="panel-heading">
        <ol class="breadcrumb">
            <li><a href="${path!}/">首页</a></li>
            <li class="active">新消息</li>
        </ol>
    </div>
    <#list notifications as notification>
        <div class="panel-body">
            <a href="${path!}/user/${notification.from_author_id!}" target="_blank">${notification.nickname!} </a>
            ${notification.formatDate(notification.in_time)!}
            ${notification.action!}
            <#if notification.source == 'topic'>
                <a href="${path!}/topic/${notification.target_id!}" target="_blank">${notification.message!}</a>
            <#elseif notification.source == 'message'>
                <a href="${path!}/message/read/${notification.target_id!}" target="_blank">${notification.message!}</a>
            </#if>
        </div>
        <#if notification_has_next><div class="divide" style="margin-top:0;"></div></#if>
    </#list>
</div>
<div class="panel panel-default">
    <div class="panel-heading">
        <ol class="breadcrumb">
            <li><a href="/">首页</a></li>
            <li class="active">过往消息</li>
        </ol>
    </div>
    <#list oldMessages.getList() as notification>
        <div class="panel-body">
            <a href="${path!}/user/${notification.from_author_id!}" target="_blank">${notification.nickname!} </a>
            ${notification.formatDate(notification.in_time)!}
            ${notification.action!}
            <#if notification.source == 'topic'>
                <a href="${path!}/topic/${notification.target_id!}" target="_blank">${notification.message!}</a>
            <#elseif notification.source == 'message'>
                <a href="${path!}/message/read/${notification.target_id!}" target="_blank">${notification.message!}</a>
            </#if>
        </div>
        <#if notification_has_next><div class="divide" style="margin-top:0;"></div></#if>
    </#list>
    <div style="height: 10px;"></div>
    <#include "/WEB-INF/pages/front/common/_paginate.ftl" />
    <@paginate currentPage=oldMessages.pageNumber totalPage=oldMessages.totalPage actionUrl="${path!}/user/message/${session.user.id!}" urlParas="" />
</div>
</@html>
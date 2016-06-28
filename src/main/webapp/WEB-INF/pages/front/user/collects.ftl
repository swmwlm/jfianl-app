<#include "/WEB-INF/pages/front/common/_layout.ftl"/>
<@html title="${current_user.nickname!} 收藏的话题 - ${siteTitle!}" description="${current_user.nickname!} 收藏的话题" sidebar_user_info="show" sidebar_create="show">
<div class="panel panel-default">
    <div class="panel-heading">
        <ol class="breadcrumb">
            <li><a href="${path!}/">首页</a></li>
            <li class="active">${current_user.nickname!} 收藏的话题</li>
        </ol>
    </div>
    <div class="panel-body">
        <#include "/WEB-INF/pages/front/common/topic_list.ftl"/>
        <@topic_list topics=page.getList()/>
    </div>
    <#include "/WEB-INF/pages/front/common/_paginate.ftl" />
    <@paginate currentPage=page.pageNumber totalPage=page.totalPage actionUrl="${path!}/user/collects/${current_user.id!}" urlParas="" />
</div>
</@html>
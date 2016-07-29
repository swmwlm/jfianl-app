<#include "/WEB-INF/pages/front/common/_layout.ftl"/>
<@html title="Top100 积分榜 - ${siteTitle!}" description="Top100 积分榜" sidebar_user_info="show" sidebar_create="show">
<div class="panel panel-default">
    <div class="panel-heading">
        <ol class="breadcrumb">
            <li><a href="${path!}/">首页</a></li>
            <li class="active">Top100 积分榜</li>
        </ol>
    </div>
    <div class="panel-body">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>#</th>
                <th>用户名</th>
                <th style="min-width: 45px;">积分</th>
                <th style="min-width: 58px;">主题数</th>
                <th style="min-width: 58px;">评论数</th>
            </tr>
            </thead>
            <tbody>
            <#list top100 as user>
                <tr>
                    <td style="vertical-align: middle;padding:4px;">${user_index + 1}</td>
                    <td style="vertical-align: middle;padding:4px; max-width: 150px;text-overflow: ellipsis;white-space: nowrap;overflow: hidden;">
                        <a href="${path!}/user/${user.id!}">
                            <img src="${imgPath!}/${user.avatar!}" width="30">
                        </a>
                        &nbsp;${user.nickname!}
                    </td>
                    <td style="vertical-align: middle;padding:4px;">${user.score!}</td>
                    <td style="vertical-align: middle;padding:4px;">${user.topic_count!}</td>
                    <td style="vertical-align: middle;padding:4px;">${user.reply_count!}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
</@html>
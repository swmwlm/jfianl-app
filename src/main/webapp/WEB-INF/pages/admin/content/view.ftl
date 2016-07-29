<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="content">
<section class="content-header">
    <h1>
        内容
        <small>预览</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/content"><i class="fa fa-tag"></i> 内容</a></li>
        <li class="active">预览</li>
    </ol>
</section>
<div class="panel panel-default">
    <div class="panel-body">
        <#if content.isExternalHref == 1>
            <span class="label label-success">外</span>
        </#if>
        <span>${content.title!}</span>
        <div style="font-size: 12px;color: #838383; padding-top: 5px;">
            <span>
                &nbsp;•&nbsp;发布${content.releaseTime!}
            </span>
            <span>&nbsp;•&nbsp;${content.view!} 次浏览</span>
                <span>&nbsp;•&nbsp;创建
                ${content.createdTime!}
                </span>
            <span>
                &nbsp;•&nbsp;<a href="${path!}/?tab=${content.tab!}">${categoryName!}</a>
            </span>
        </div>
    </div>
    <div class="panel-body" style="border-top: 1px #E5E5E5 solid; padding-top: 10px">
        <div style="font-size: 12px;color: #838383; padding-top: 5px;">
            ${content.introduction!}
        </div>
    </div>
    <div class="panel-body" style="border-top: 1px #E5E5E5 solid; padding-top: 10px;padding-left: 50px;padding-right: 50px;">
        <div class="pull-left topic-label">
            <#if content.isExternalHref == 1>
                <a href="${content.externalHref!}" target="_blank">${content.externalHref!}</a>
            <#else>
                <#if (contentImages?size gt 0)>
                    <div style="margin-left: auto;margin-right: auto;width: 300px;">
                        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                            <!-- Indicators -->
                            <ol class="carousel-indicators">
                                <#list contentImages as image>
                                    <li data-target="#carousel-example-generic" data-slide-to="${image_index}"
                                        <#if image_index==0 > class="active" </#if> ></li>
                                </#list>
                            </ol>

                            <!-- Wrapper for slides -->
                            <div class="carousel-inner" role="listbox">
                            <#list contentImages as image>
                                <#if image_index==0>
                                    <div class="item active">
                                <#else>
                                    <div class="item">
                                </#if>
                                        <img src="${imgPath!}${image.img!}" alt="${image.title}" style="width: 300px;height: 200px;">
                                        <div class="carousel-caption">
                                            <h3>${image.title}</h3>
                                        </div>
                                    </div>
                            </#list>
                            </div>

                            <!-- Controls -->
                            <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </div>
                    </div>
                </#if>
                <br/><br/>
                <div class="wangEditor-txt" style="width:758px;display: block;">${content.content!}</div>
            </#if>
        </div>
    </div>
    <div class="panel-footer" id="topic_footer">
        <div style="font-size: 12px;color: #838383; padding-top: 5px;">
            <span>&nbsp;•&nbsp;作者:${content.author!}</span><br>
            <span>&nbsp;•&nbsp;来源:${content.source!}</span>
        </div>
    </div>
</div>
</@layout>
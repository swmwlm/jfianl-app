<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="news">
<section class="content-header">
    <h1>
        资讯
        <small>预览</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/news"><i class="fa fa-tag"></i> 资讯</a></li>
        <li class="active">预览</li>
    </ol>
</section>
<div class="panel panel-default">
    <div class="panel-body">
        <#if news.isExternalHref == 1>
            <span class="label label-success">外</span>
        </#if>
        <span>${news.title!}</span>
        <div style="font-size: 12px;color: #838383; padding-top: 5px;">
            <span>
                &nbsp;•&nbsp;发布${news.releaseTime!}
            </span>
            <span>&nbsp;•&nbsp;${news.view!} 次浏览</span>
                <span>&nbsp;•&nbsp;创建
                ${news.createdTime!}
                </span>
            <span>
                &nbsp;•&nbsp;<a href="${path!}/?tab=${news.tab!}">${categoryName!}</a>
            </span>
        </div>
    </div>
    <div class="panel-body" style="border-top: 1px #E5E5E5 solid; padding-top: 10px">
        <div style="font-size: 12px;color: #838383; padding-top: 5px;">
            ${news.introduction!}
        </div>
    </div>
    <div class="panel-body" style="border-top: 1px #E5E5E5 solid; padding-top: 10px;padding-left: 50px;padding-right: 50px;">
        <div class="pull-left topic-label">
            <#if news.isExternalHref == 1>
                <a href="${news.externalHref!}" target="_blank">${news.externalHref!}</a>
            <#else>
                <#if newsImages ??>
                    <div style="margin-left: auto;margin-right: auto;width: 300px;">
                        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                            <!-- Indicators -->
                            <ol class="carousel-indicators">
                                <#list newsImages as image>
                                    <li data-target="#carousel-example-generic" data-slide-to="${image_index}"
                                        <#if image_index==0 > class="active" </#if> ></li>
                                </#list>
                            </ol>

                            <!-- Wrapper for slides -->
                            <div class="carousel-inner" role="listbox">
                            <#list newsImages as image>
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
                <div>${news.content!}</div>
            </#if>
        </div>
    </div>
    <div class="panel-footer" id="topic_footer">
        <div style="font-size: 12px;color: #838383; padding-top: 5px;">
            <span>&nbsp;•&nbsp;作者${news.author!}</span><br>
            <span>&nbsp;•&nbsp;来源${news.source!}</span>
        </div>
    </div>
</div>
</@layout>
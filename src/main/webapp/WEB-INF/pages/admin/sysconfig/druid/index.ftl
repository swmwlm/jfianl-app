<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="druid">
<section class="content-header">
    <h1>
        MySQL监控
        <small>druid</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="javascript:void(0);" class="active"><i class="fa fa-tag"></i> 监控</a></li>
    </ol>
</section>
<section class="content">
    <div class="box">
        <iframe src="${path!}/admin/druid" style="width: inherit;min-height: 600px;"></iframe>
    </div>
</section>
</@layout>
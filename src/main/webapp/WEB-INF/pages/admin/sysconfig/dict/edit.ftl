<#include "/WEB-INF/pages/admin/common/_layout.ftl"/>
<@layout page_tab="dict">
<section class="content-header">
    <h1>
        字典
        <small>编辑字典</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="${path!}/admin/index"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a href="${path!}/admin/dict"><i class="fa fa-tag"></i> 字典列表</a></li>
        <li class="active">编辑</li>
    </ol>
</section>
<section class="content">
    <div class="box box-info">
        <div class="box-header with-border">
            <h3 class="box-title">编辑字典</h3>
        </div>
        <form class="form-horizontal" action="${path!}/admin/dict/edit" method="post">
            <input type="hidden" name="id" value="${dict.id!}">
            <div class="box-body">
                <div class="form-group">
                    <label for="type" class="col-sm-2 control-label">type</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="type" name="type" value="${dict.type!}" placeholder="类型">
                    </div>
                </div>
                <div class="form-group">
                    <label for="key" class="col-sm-2 control-label">key</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="key" name="key" value="${dict.key!}" placeholder="关键字">
                    </div>
                </div>
                <div class="form-group">
                    <label for="value" class="col-sm-2 control-label">value</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="value" name="value" value="${dict.value!}" placeholder="对应值">
                    </div>
                </div>
                <div class="form-group">
                    <label for="sort" class="col-sm-2 control-label">排序</label>
                    <div class="col-sm-5">
                        <input type="text" class="form-control" id="sort" name="sort" value="${dict.sort!}" placeholder="排序">
                    </div>
                </div>
                <div class="form-group">
                    <label for="remark" class="col-sm-2 control-label">备注</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="remark" name="remark" value="${dict.remark!}" placeholder="字典备注">
                    </div>
                </div>
            </div>
            <div class="box-footer">
                <button type="submit" class="btn btn-raised btn-info pull-right">保存</button>
            </div>
        </form>
    </div>
</section>
</@layout>

<#macro layout page_tab="">
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>后台管理 - ${siteTitle!}</title>
    <link rel="icon" href="${path!}/static/favicon.ico">

    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="${path!}/static/AdminLTE/bootstrap/css/bootstrap.min.css">
    <!-- DataTables -->
    <link rel="stylesheet" href="${path!}/static/AdminLTE/plugins/datatables/dataTables.bootstrap.css">
    <!-- Font Awesome -->
    <#--<link rel="stylesheet" href="${path!}/static/font-awesome/css/font-awesome.min.css">-->
    <link href="http://cdn.bootcss.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet">
    <!-- Ionicons -->
    <#--<link rel="stylesheet" href="${path!}/static/ionicons/css/ionicons.min.css">-->
    <link href="http://cdn.bootcss.com/ionicons/2.0.0/css/ionicons.min.css" rel="stylesheet">
    <!-- Theme style -->
    <link rel="stylesheet" href="${path!}/static/AdminLTE/dist/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="${path!}/static/AdminLTE/dist/css/skins/_all-skins.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="${path!}/static/AdminLTE/plugins/iCheck/flat/blue.css">
    <!-- Morris chart -->
    <#--<link rel="stylesheet" href="${path!}/static/AdminLTE/plugins/morris/morris.css">-->
    <!-- jvectormap -->
    <link rel="stylesheet" href="${path!}/static/AdminLTE/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
    <!-- Date Picker -->
    <link rel="stylesheet" href="${path!}/static/AdminLTE/plugins/datepicker/datepicker3.css">
    <!-- Daterange picker -->
    <link rel="stylesheet" href="${path!}/static/AdminLTE/plugins/daterangepicker/daterangepicker-bs3.css">
    <!-- bootstrap wysihtml5 - text editor -->
    <#--<link rel="stylesheet" href="${path!}/static/AdminLTE/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">-->

    <link rel="stylesheet" href="${path!}/static/css/admin.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- jQuery 2.2.0 -->
    <script src="${path!}/static/AdminLTE/plugins/jQuery/jQuery-2.2.0.min.js"></script>
    <!-- jQuery UI 1.11.4 -->
    <#--<script src="${path!}/static/bootstrap/js/jquery-ui.js"></script>-->
    <script src="http://cdn.bootcss.com/jqueryui/1.11.4/jquery-ui.min.js"></script>

    <script type="text/javascript" src="${path}/static/component/plupload-2.1.9/js/plupload.full.min.js"></script>
    <script type="text/javascript" src="${path}/static/component/plupload-2.1.9/js/i18n/zh_CN.js"></script>
    <script type="text/javascript" src="${path}/static/component/plupload-2.1.9/extractUpload.js"></script>

</head>

<body class="hold-transition skin-blue sidebar-mini">

<div class="wrapper">
    <#include "/WEB-INF/pages/admin/common/header.ftl">
    <@header/>

    <!-- Left side column. contains the logo and sidebar -->
    <#include "/WEB-INF/pages/admin/common/left.ftl"/>
    <@pageLeft page_tab=page_tab/>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <#nested />
    </div>
    <!-- /.content-wrapper -->
    <footer class="main-footer">
        <strong>后台模板学习地址 <a href="http://almsaeedstudio.com">Almsaeed Studio</a>.</strong>
    </footer>
    <#include "/WEB-INF/pages/admin/common/controlAside.ftl">
    <@controlAside/>
    <div class="control-sidebar-bg"></div>
</div>

<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
    $.widget.bridge('uibutton', $.ui.button);
</script>
<!-- Bootstrap 3.3.5 -->
<script src="${path!}/static/AdminLTE/bootstrap/js/bootstrap.min.js"></script>
<!-- DataTables -->
<script src="${path!}/static/AdminLTE/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${path!}/static/AdminLTE/plugins/datatables/dataTables.bootstrap.min.js"></script>
<!-- Morris.js charts -->
<#--<script src="${path!}/static/js/raphael-min.js"></script>-->
<#--<script src="${path!}/static/AdminLTE/plugins/morris/morris.min.js"></script>-->
<!-- Sparkline -->
<script src="${path!}/static/AdminLTE/plugins/sparkline/jquery.sparkline.min.js"></script>
<!-- jvectormap -->
<script src="${path!}/static/AdminLTE/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
<script src="${path!}/static/AdminLTE/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
<!-- jQuery Knob Chart -->
<script src="${path!}/static/AdminLTE/plugins/knob/jquery.knob.js"></script>
<!-- daterangepicker -->
<#--<script src="${path!}/static/js/moment.min.js"></script>-->
<script src="http://cdn.bootcss.com/moment.js/2.10.6/moment.min.js"></script>
<script src="${path!}/static/AdminLTE/plugins/daterangepicker/daterangepicker.js"></script>
<!-- datepicker -->
<script src="${path!}/static/AdminLTE/plugins/datepicker/bootstrap-datepicker.js"></script>
<!-- Bootstrap WYSIHTML5 -->
<#--<script src="${path!}/static/AdminLTE/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>-->
<!-- Slimscroll -->
<script src="${path!}/static/AdminLTE/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="${path!}/static/AdminLTE/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="${path!}/static/AdminLTE/dist/js/app.min.js"></script>
<script src="${path!}/static/component/layer/web/layer.js"></script>
<script type="text/javascript" src="${path!}/static/component/laydate/laydate.js"></script>
<script type="text/javascript" src="${path!}/static/component/artTemplate/template.js"></script>
<script type="text/javascript" src="${path!}/static/component/artTemplate/template.plugin.js"></script>
<!-- CSRF -->
<#include "/WEB-INF/pages/public/common.ftl"/>
<script type="text/javascript" src="${path!}/static/js/app/csrf/csrf.js"></script>
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
<#--<script src="${path!}/static/AdminLTE/dist/js/pages/dashboard.js"></script>-->
<!-- AdminLTE for demo purposes -->
<#--<script src="${path!}/static/AdminLTE/dist/js/demo.js"></script>-->
</body>
</html>
</#macro>
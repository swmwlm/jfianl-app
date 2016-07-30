<#macro header>
<header class="main-header">
    <!-- Logo -->
    <a href="${path!}/admin/index" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>首科</b></span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><b>首科PLUS</b></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top" role="navigation">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>

        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <li class="user user-menu">
                    <a href="#">
                        <img src="${path!}/static/AdminLTE/dist/img/user0-160x160.png" class="user-image" alt="User Image">
                        <span class="hidden-xs">管理员</span>
                    </a>
                </li>
                <!-- Control Sidebar Toggle Button -->
                <li>
                    <a href="${path!}/admin/runas">切换身份</a>
                </li>
                <li>
                    <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
                </li>
            </ul>
        </div>
    </nav>
</header>
</#macro>
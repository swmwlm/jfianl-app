<#macro pageLeft page_tab="">
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="${path!}/static/AdminLTE/dist/img/user0-160x160.png" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p>欢迎你,<label id="username">${currrent_admin_user.username!}</label></p>
                <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                <a href="${path!}/admin/runas"><i class="fa fa-exchange text-success"></i> 切换身份</a>
            </div>
        </div>
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">导航菜单</li>
            <@shiro.hasPermission name="menu:index">
                <li <#if page_tab="index"> class="active" </#if>>
                    <a href="${path!}/admin/index">
                        <i class="fa fa-dashboard"></i> <span>首页</span>
                    </a>
                </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="menu:section">
                <li <#if page_tab="section"> class="active" </#if>>
                    <a href="${path!}/admin/section">
                        <i class="fa fa-puzzle-piece"></i> <span>板块</span>
                    </a>
                </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="menu:label">
                <li <#if page_tab="label"> class="active" </#if>>
                    <a href="${path!}/admin/label">
                        <i class="fa fa-tag"></i> <span>标签</span>
                    </a>
                </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="menu:topic">
                <li <#if page_tab="topic"> class="active" </#if>>
                    <a href="${path!}/admin/topic">
                        <i class="fa fa-th-list"></i> <span>话题</span>
                    </a>
                </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="menu:reply">
                <li <#if page_tab="reply"> class="active" </#if>>
                    <a href="${path!}/admin/reply">
                        <i class="fa fa-reply"></i> <span>回复</span>
                    </a>
                </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="menu:content">
                <li <#if page_tab="content"> class="active" </#if>>
                    <a href="${path!}/admin/content">
                        <i class="fa fa-newspaper-o"></i> <span>内容</span>
                    </a>
                </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="menu:rollimages">
                <li <#if page_tab="rollimages"> class="active" </#if>>
                    <a href="${path!}/admin/rollimages">
                        <i class="fa fa-file-image-o"></i> <span>轮播图</span>
                    </a>
                </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="menu:user">
                <li <#if page_tab="user"> class="active" </#if>>
                    <a href="${path!}/admin/user">
                        <i class="fa fa-user"></i> <span>用户</span>
                    </a>
                </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="menu:link">
                <li <#if page_tab="link"> class="active" </#if>>
                    <a href="${path!}/admin/link">
                        <i class="fa fa-link"></i> <span>友链</span>
                    </a>
                </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="menu:mission">
                <li <#if page_tab="mission"> class="active" </#if>>
                    <a href="${path!}/admin/mission">
                        <i class="fa fa-tasks"></i> <span>签到</span>
                    </a>
                </li>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="menu:setting">
                <li class="treeview <#if page_tab="druid" || page_tab="sysconfig" || page_tab="dict"|| page_tab="session" || page_tab="adminuser" || page_tab="role" || page_tab="permission" || page_tab="modifypwd">active</#if>">
                    <a href="javascript:;">
                        <i class="fa fa-cogs"></i> <span>设置</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <@shiro.hasPermission name="setting:druid">
                            <li <#if page_tab="druid"> class="active" </#if>><a
                                    href="${path!}/admin/sysconfig/druid"><i class="fa fa-circle-o"></i> MySql监控</a></li>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="setting:sysconfig">
                            <li <#if page_tab="sysconfig"> class="active" </#if>><a
                                    href="${path!}/admin/sysconfig"><i class="fa fa-circle-o"></i> 系统设置</a></li>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="setting:dict">
                            <li <#if page_tab="dict"> class="active" </#if>><a
                                    href="${path!}/admin/dict"><i class="fa fa-circle-o"></i> 系统字典</a></li>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="setting:session">
                            <li <#if page_tab="session"> class="active" </#if>><a
                                    href="${path!}/admin/session"><i class="fa fa-circle-o"></i> 后台会话</a></li>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="setting:adminuser">
                            <li <#if page_tab="adminuser"> class="active" </#if>><a
                                    href="${path!}/admin/adminuser"><i class="fa fa-circle-o"></i> 后台用户</a></li>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="setting:role">
                            <li <#if page_tab="role"> class="active" </#if>><a href="${path!}/admin/role"><i
                                    class="fa fa-circle-o"></i> 角色</a></li>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="setting:permission">
                            <li <#if page_tab="permission"> class="active" </#if>><a
                                    href="${path!}/admin/permission"><i class="fa fa-circle-o"></i> 权限</a></li>
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="setting:modifypwd">
                            <li <#if page_tab="modifypwd"> class="active" </#if>><a
                                    href="${path!}/admin/adminuser/modifypwd"><i class="fa fa-circle-o"></i> 修改密码</a></li>
                        </@shiro.hasPermission>
                    </ul>
                </li>
            </@shiro.hasPermission>
            <li>
                <a href="${path!}/admin/logout">
                    <i class="fa fa-sign-out"></i> <span>退出</span>
                </a>
            </li>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>
</#macro>
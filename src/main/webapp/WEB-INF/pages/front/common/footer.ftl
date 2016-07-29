<#macro footer>
<div class="footer">
    <div class="container">
        <div style="padding-bottom: 10px; text-align: left;" class="hidden-sm hidden-xs">
            <ul class="nav nav-pills" id="links">
                <li>友情链接：</li>
                <#list links as link>
                    <li><a href="${link.url!}" target="_blank">${link.name!}</a></li>
                </#list>
            </ul>
        </div>
        <a href="http://git.oschina.net/20110516/jfinalbbs" target="_blank">源码地址</a>
        <a target="_blank" href="#">QQ</a>&nbsp;
        <a target="_blank" href="#">Email</a>&nbsp;
        <br>
        &copy;2015 Powered by <a href="http://shoukeplus.com" target="_blank">JFinalTest</a>
        <a href="#">${beian_name!}</a>
        ${tongji_js!}
    </div>
</div>
</#macro>
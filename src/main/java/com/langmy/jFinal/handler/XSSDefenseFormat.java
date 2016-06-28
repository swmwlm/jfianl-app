package com.langmy.jFinal.handler;


import com.jfinal.kit.PathKit;
import org.apache.commons.lang3.StringEscapeUtils;
import org.beetl.core.Format;
import org.owasp.validator.html.*;

/**
 * 技术参考:http://my.oschina.net/smile622/blog/300137
 * 1.自定义一个格式化类XSSDefenseFormat.java
 * 2.配置antisamy.xml
 * 3.Config.java中 注册该格式化 groupTemplate.registerFormat("xss", new XSSDefenseFormat());
 * 4.针对需要的地方，进行ｘｓｓ处理，默认是不对ｈｔｍｌ进行转义输出的，因为在使用富编辑器后，会带用html，
 *   转义的话页面显示需要处理，所以默认不转义．
 *   ${a.name, xss}
 * 5.如果输出的ｈｔｍｌ需要转义那么使用：
 *   ${a.name, xss＝"escape"}
 */
public class XSSDefenseFormat implements Format {


    @Override
    public Object format(Object data, String pattern) {
        if (null == data) {
            return null;
        } else {

            try {
                //antisamy.xml采用官方给出的ｅｂａｙ的模板
                Policy policy = Policy.getInstance(PathKit.getRootClassPath()+"/antisamy.xml");
                AntiSamy as = new AntiSamy(policy);

                String content = (String) data;
                if ("escape".equals(pattern)) {
                    content = StringEscapeUtils.escapeHtml4(content);
                }

                //clean content
                CleanResults cr = as.scan(content);
                content = cr.getCleanHTML();

                return content;

            } catch (ScanException e) {
                return "系统错误";
            } catch (PolicyException e) {
                return "系统错误";
            }

        }
    }
}
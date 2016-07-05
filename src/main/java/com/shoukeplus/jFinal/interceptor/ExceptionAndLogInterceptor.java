package com.shoukeplus.jFinal.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.JFinal;
import com.shoukeplus.jFinal.common.BaseController;
import org.apache.commons.lang3.StringUtils;
import com.jfinal.log.Log;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志/异常拦截器,拦截action
 */
public class ExceptionAndLogInterceptor implements Interceptor {

  private static final Log log = Log.getLog(ExceptionAndLogInterceptor.class);

  @Override
  public void intercept(Invocation ai) {
    BaseController controller = (BaseController)ai.getController();
    HttpServletRequest request = controller.getRequest();

    try {
      ai.invoke();
    } catch (Exception e) {
      //
      doLog(ai,e);
      //判断是否ajax请求
      String header = request.getHeader("X-Requested-With");
      boolean isAjax = "XMLHttpRequest".equalsIgnoreCase(header);
      String msg = formatException(e);
      if(isAjax){
        msg = new StringBuilder().append("{\"status\":\"0\",\"msg\":\"")
            .append(msg).append("\"}").toString();
        controller.renderJson(msg);
      }else{
        String redirctUrl = request.getHeader("referer");
        if(StringUtils.isBlank(redirctUrl)){
          redirctUrl = request.getRequestURI();
        }
        controller.setAttr("message", msg);
        controller.setAttr("redirctUrl",redirctUrl);
        controller.render("/WEB-INF/pages/public/failed.ftl");
      }
    }finally{
      //记录日志到数据库,暂不实现
    }
  }

  /**
   * @methodName: doLog
   * @description: 记录log4j日志
   * @author: vakinge
   * @createDate: 2014年4月3日
   * @param ai 
   * @param e 
   */
  private void doLog(Invocation ai,Exception e) {
    //开发模式
    if(JFinal.me().getConstants().getDevMode()){
      e.printStackTrace();
    }
    StringBuilder sb =new StringBuilder("\n---Exception Log Begin---\n");
    sb.append("Controller:").append(ai.getController().getClass().getName()).append("\n");
    sb.append("Method:").append(ai.getMethodName()).append("\n");
    sb.append("Exception Type:").append(e.getClass().getName()).append("\n");
    sb.append("Exception Details:");
    log.error(sb.toString(), e);
  }

  /**
   * 格式化异常信息，用于友好响应用户
   * @param e
   * @return
   */
  private static String formatException(Exception e){
     String message = null;
      Throwable ourCause = e;
      while ((ourCause = e.getCause()) != null) {
        e = (Exception) ourCause;
      }
      String eClassName = e.getClass().getName();
      //一些常见异常提示
      if("java.lang.NumberFormatException".equals(eClassName)){
        message = "请输入正确的数字";
      }else if (e instanceof RuntimeException) {
        message = e.getMessage();
        if(StringUtils.isBlank(message))message = e.toString();
      }
      
      //获取默认异常提示
      if (StringUtils.isBlank(message)){
        message = "系统繁忙,请稍后再试";
      }
      //替换特殊字符
      message = message.replaceAll("\"", "'");
      return message;
  }
}
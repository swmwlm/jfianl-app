package com.shoukeplus.jFinal.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.shoukeplus.jFinal.common.AppConstants;
import com.shoukeplus.jFinal.common.model.User;
import com.shoukeplus.jFinal.common.utils.Result;
import com.shoukeplus.jFinal.common.utils.StrUtil;

/**
 * 验证令牌合法性,通过才可以执行相应的controller,
 * 使用方式:例如,LabelClientController.index()
 * 方法上加注解@Before(ClientInterceptor.class)
 */
public class ClientInterceptor implements Interceptor {

    public void intercept(Invocation inv) {
        String token = inv.getController().getPara("token");
        Result result = new Result(AppConstants.CODE_SUCCESS, AppConstants.DESC_SUCCESS, null);
        if (StrUtil.isBlank(token)) {
            result.setCode(AppConstants.CODE_FAILURE);
            result.setDescription("用户令牌不能为空");
            inv.getController().renderJson(result);
        } else {
            //验证token 的合法性
            User user = User.dao.findByToken(token);
            if (user == null) {
                result.setCode(AppConstants.CODE_FAILURE);
                result.setDescription("用户令牌无效");
                inv.getController().renderJson(result);
            } else {
                inv.invoke();
            }
        }
    }
}
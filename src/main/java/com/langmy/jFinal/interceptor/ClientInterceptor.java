package com.langmy.jFinal.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.langmy.jFinal.common.AppConstants;
import com.langmy.jFinal.common.model.User;
import com.langmy.jFinal.common.utils.Result;
import com.langmy.jFinal.common.utils.StrUtil;

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
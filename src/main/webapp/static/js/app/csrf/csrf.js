$(document).ready(function () {
    appendToken();
});

function appendToken(){
    updateForms();
}

function updateForms() {
    // 得到页面中所有的 form 元素
    var forms = document.getElementsByTagName('form');
    for(i=0; i<forms.length; i++) {
        var url = forms[i].action;

        // 如果这个 form 的 action 值为空，则不附加 csrftoken
        if(url == null || url == "" ) continue;

        // 动态生成 input 元素，加入到 form 之后
        var e = document.createElement("input");
        e.name = "token";
        e.value = token;
        e.type="hidden";
        forms[i].appendChild(e);
    }
}

/**
 * 重写jquery $.ajax方法,封装token到http头信息
 */
(function($){
    //备份jquery的ajax方法
    var _ajax=$.ajax;

    //重写jquery的ajax方法
    $.ajax=function(opt){
        //备份opt中error和success方法
        var fn = {
            error:function(XMLHttpRequest, textStatus, errorThrown){},
            success:function(data, textStatus){},
        }
        if(opt.error){
            fn.error=opt.error;
        }
        if(opt.success){
            fn.success=opt.success;
        }

        //扩展增强处理
        var _opt = $.extend(opt,{
            error:function(XMLHttpRequest, textStatus, errorThrown){
                //错误方法增强处理

                fn.error(XMLHttpRequest, textStatus, errorThrown);
            },
            success:function(data, textStatus){
                //成功回调方法增强处理

                fn.success(data, textStatus);
            },
            headers:{
                'token':token
            }
        });
        _ajax(_opt);
    };
})(jQuery);
! function(window, undefined) {

    //var host =  'http://localhost:8080/borrow-manage'
    // var host = 'http://47.92.246.248:8088/borrow-manage'
     var host = window.location.protocol + "//" + window.location.host + '/borrow-manage'
     //var host = window.location.protocol + "//" + window.location.host + "/" + window.location.pathname.split('/')[1] + '/';

    var loginUrl = "login"

    var ajaxError = '登录超时,请重新登录'

    var loginTimeOut = "登录超时,请重新登录"

    var noPower = "您没有权限进行此操作"

    function getUserUid() {
        var userInfo = layui.data("MA_USER");
        return userInfo["sysUserUid"] || " "
    }

    function getToken() {
        var userInfo = layui.data("MA_USER");
        return userInfo["token"] || " "
    }
    function ajax(obj,source) {
        if(source&&$(source).hasClass("disable")){
            return;
        }
        if(source){
            $(source).addClass("disable");
        }

        if (obj.url.indexOf("/") !== 0 && obj.url.indexOf("http://") !== 0 && obj.url.indexOf("https://") !== 0) {
            obj.url = host + obj.url;
        }
        if (obj.data === undefined) {
            obj.data = {}
        }
        if (obj.data && typeof obj.data === "object") {
            // if (!obj.data["sysUserUid"]) {
            obj.data["sysUserUid"] =  sessionStorage.getItem("sysUserUid");
            obj.data["token"] =  sessionStorage.getItem("token");
            // }
            obj.data = JSON.stringify(obj.data)
        }

        var _def = {
            type: "post",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(re) {
                if (re.succeed) {
                    _def.done(re)
                } else {
                    _def.fail(re)
                }
            },
            done: function(re) {},
            fail: function(re) {
                enableBtn();
                layer.hideLoad();
                layer.error(re.errorMessage);
            },
            nologin: function(re) {
                enableBtn();
                layer.hideLoad();
                layer.error({
                    content: loginTimeOut,
                    end: function() {
                        top.location.href = host + loginUrl
                    }
                });
            },
            nopower: function(re) {
                enableBtn();
                layer.hideLoad();
                layer.error(noPower);
            },
            error: function() {
                enableBtn();
                layer.hideLoad()
                layer.error(ajaxError)
            }
        }

        $.ajax($.extend(_def, obj))

        function enableBtn(){
            if(source){
                $(source).removeClass("disable");
            }
        }
    }

    function format(time, format) {
        var t = new Date(time);
        var tf = function(i) {
            return (i < 10 ? '0' : '') + i
        };
        return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a) {
            switch (a) {
                case 'yyyy':
                    return tf(t.getFullYear());
                case 'MM':
                    return tf(t.getMonth() + 1);
                case 'mm':
                    return tf(t.getMinutes());
                case 'dd':
                    return tf(t.getDate());
                case 'HH':
                    return tf(t.getHours());
                case 'ss':
                    return tf(t.getSeconds());
                default:
                    return '';
            }
        });
    }
    function getParameterByName(name) {
        var values = decodeURIComponent((location.search.match(RegExp("[?|&]" + name + '=([^\&]+)')) || [, null])[1]);
        return isNullOrEmpty(values) || values == "null" ? "" : values;
    };
    function isNullOrEmpty(obj) {
        var result=(obj == null || obj == undefined || obj == "" || obj == "null" || typeof obj == "undefined");
        if(result&&(obj!=0||obj!="0")){
            return result;
        }
        else{
            return false;
        }
    }
    window.ma = {
        host: host,
        ajaxError: ajaxError,
        loginTimeOut: loginTimeOut,
        getUserUid: getUserUid,
        ajax: ajax,
        format: format,
        getParameterByName:getParameterByName,
        isNullOrEmpty:isNullOrEmpty
    }

}(window)

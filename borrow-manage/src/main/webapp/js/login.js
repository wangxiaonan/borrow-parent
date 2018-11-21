/**
 *login page
 **/
layui.use(["form", "layer", "element"], function () {
    var form = layui.form();
    var element = layui.element();
    var urlAjax = "user/permission/login";
    var hasVertify = true;
    if (localStorage.loginName && localStorage.passwd) {
        $('#username').val(localStorage.loginName);
        $('#password').val(localStorage.passwd);
    }
    form.on('submit(formLogin)', function (data) {
        var formData = data;
        if(hasVertify){
            if(!formData.field.captchaId || !formData.field.code){
                layer.error({
                    content: '请先通过验证后再登录',
                    end: initData
                });
                return false;
            }
        }
        var obj = {
            type: "post",
            url: urlAjax,
            data: formData.field,
            done: function (data) {
                if ($('#memory').is(':checked')) {
                    localStorage.loginName = formData.field.loginName;
                    localStorage.passwd = formData.field.passwd;
                } else {
                    localStorage.removeItem('loginName');
                    localStorage.removeItem('passwd');
                }
                //存储当前当前用户id
                var userUid = data.data.userInfoRes.userUid;
                layui.data("MA_USER", {
                    key: 'userUid',
                    value: userUid
                });
                window.location.href = ma.host + "index";
            },
            fail: function (data) {
                layer.hideLoad();
                layer.error({
                    content: data.errorMessage,
                    end: function () {
                        window.location.reload();
                    }
                });
            },
            error: function (err) {
                layer.hideLoad();
                layer.error({
                    content: ma.ajaxError,
                    end: function () {
                        window.location.reload();
                    }
                });
            }
        };
        layer.showLoad();
        ma.ajax(obj);
        return false;
    });
    element.on('tab(docDemoTabBrief)', function (data) {
        if (data.index == 0) {//内部员工
            urlAjax = "user/permission/login";
        } else {
            urlAjax = "user/permission/outside/login"
        }
    });
    getCode();
    function getCode() {
        var obj = {
            type: "post",
            url: 'user/getVertifyCode',
            data: {
                width: 360,
                height: 200
                ,
                r: Math.random()
            },
            done: function (result) {
                layer.hideLoad();
                switch (result.data.style) {
                    case 'IMAGE':
                        getIMAGE(result);
                        break;
                    case 'COMPUTE':
                        getIMAGE(result);
                        break;
                    case 'SLIDE':
                        initData(result);
                        break;
                    case 'CLICK':
                        getCLICK(result);
                        break;
                }
            },
            fail: function (result) {
                layer.hideLoad();
                $('#isSlide').val(1);
                hasVertify = false;
            }
        };
        layer.showLoad();
        ma.ajax(obj);
        return false;
    }


    function getIMAGE(result) {
        $('#codeImg').attr('src', 'data:image/jpeg;base64,' + result.data.imageData);
        $('#captchaId').val(result.data.captchaId);
        $('#IMAGE').show();
        $('#code').on('keydown keypress keyup', function () {
            $('#globalCode').val($(this).val());
        });
    }

    $('#codeImg').click(getCode);

    var captchaId = '';
    var refreshBtn = $('#SLIDE #verify-section-refresh');
    var topSmall = $('#SLIDE #verify-section-small');
    var tip = $('#SLIDE #slide-tip');
    var drag = $('#SLIDE #slide-section');
    var drag_bg = $('#SLIDE #slide-inner-bg');
    var handler = $('#SLIDE #slide-btn');
    var arrow = $('#SLIDE #slide-btn-arrow');
    var isMove = false;
    var x;
    var maxWidth = drag.width() - handler.width();  //能滑动的最大间距



    //生成滑块验证码
    function getSLIDE(result) {
        $('#SLIDE').height('auto');
        captchaId = result.data.captchaId;
        $('#SLIDE #verify-section-bg').attr('src', 'data:image/jpeg;base64,' + result.data.imageData);
        $('#SLIDE #verify-section-small').attr('src', 'data:image/jpeg;base64,' + result.data.smallImageData);
        //渲染图片
        setTimeout(()=>{
            var vssH = $('#SLIDE #verify-section-small').height();
            $('#SLIDE #verify-section-small').css('top', result.data.yPos - vssH / 2 + 'px');
        }, 50);
    }

    //验证滑块验证码
    function verifyCode(code) {
        var obj = {
            type: "post",
            url: 'user/conVertifyCode',
            data: {
                captchaId: code.captchaId,
                code: code.code
            },
            done: function (result) {
                $('#isSlide').val(1);
                $('#globalCode').val(code.code);
                $('#captchaId').val(code.captchaId);
                $('#SLIDE #result').removeClass('succeed failed').addClass('succeed').html("验证成功")
                tip.addClass('slide-tip-green');
                drag_bg.removeClass('slide-inner-bg-move').addClass('slide-inner-bg-success')
                arrow.addClass('slide-btn-arrow-success')
                handler.addClass('slide-btn-success')
                refreshBtn.hide();
                $('#login').click();
            },
            fail: function (result) {
                $('#SLIDE #result').removeClass('succeed failed').addClass('failed').html("验证失败")
                tip.addClass('slide-tip-red');
                //刷新
                getCode();
            }
        };
        ma.ajax(obj);
    }



    //刷新验证码
    $('#verify-section-refresh').on('click', function() {
        getCode()
    });

    function initData(result) {
        $('#login').hide();
        isSlide = 1;
        drag_bg.css({'width': '38px'});

        handler.css({'left': '0'}).removeClass('slide-btn-success');
        topSmall.css({'left': '0'});
        arrow.removeClass('slide-btn-arrow-success');
        tip.removeClass('slide-tip-red');
        tip.removeClass('slide-tip-green');
        tip.text("向右滑动滑块填充拼图");
        $('#SLIDE #result').removeClass('succeed failed').html("");
        getSLIDE(result);

    }
    userSlide();

    //滑动滑块
    function userSlide() {
        var UPNull = false;
        handler.mousedown(function(e) {
            if($('#username').val() === '' || $('#password').val() === ''){
                layer.error('用户名或密码为必填项，请填写完整再验证！');
                UPNull = true;
                return;
            }else {
                UPNull = false;
            }
            isMove = true;
            x = e.pageX - parseInt(handler.css('left'), 10);
        });

        //鼠标指针在上下文移动时，移动距离大于0小于最大间距，滑块x轴位置等于鼠标移动距离
        handler.mousemove(function(e) {
            if(UPNull){
                return;
            }
            var _x = e.pageX - x;// _x = e.pageX - (e.pageX - parseInt(handler.css('left'), 10)) = x
            if (isMove) {
                if (_x > 0 && _x <= maxWidth) {
                    handler.css({'left': _x});
                    drag_bg.css({'width': _x});
                    $('#SLIDE #verify-section-small').css({'left': _x});
                } else if (_x > maxWidth) {  //鼠标指针移动距离达到最大时清空事件
                    dragOk();
                }
                $('#SLIDE #slide-inner-bg').addClass('slide-inner-bg-move');
                tip.text('');
            }
        }).mouseup(function(e) {
            if(UPNull){
                return;
            }
            isMove = false;
            var _x = e.pageX - x;
            verifyCode({code: _x, captchaId:captchaId});
        });
    }

    var refreshBtnClick = $('#verify-section-refresh-click');
    var selectData = [];
    var count = 0;  //图片字数
    var clickCount = 0;  //点选次数
    function initBtn() {
        $('.click-btn').remove();
        clickCount = 0;
        for (var i = 0; i < count; i++) {
            $('#verify-section-click').append('<img id="click' + (i + 1) + '" class="click-btn clickbtn-off" src="' + window.location.protocol + '//' + window.location.host + '/Admin/images/click_' + (i + 1) + '.png" alt="">');
        }
    }

    $('#verify-section-click').on('click',function(e) {
        if($('#username').val() === '' || $('#password').val() === ''){
            layer.error('用户名或密码为必填项，请填写完整再验证！');
            return;
        }
        var _this = $(this).find('.click-btn').eq(clickCount);
        var clickBtn = $(this).find('.click-btn').eq(clickCount);
        var clickStatus = _this.css('display');
        var offL = _this.width() / 2;
        var offT = _this.height() / 2;
        clickCount++;
        _this.css({
            left: e.offsetX-offL,
            top: e.offsetY-offT,
        }).removeClass('clickbtn-off').addClass('clickbtn-on')
        selectData.push({x: e.offsetX + offL, y: e.offsetY  + offT})
        if (clickCount == count) {
            verifyCodeClick({codes:selectData})
        }
    });

    function getCLICK(result) {
        $('#result-click').html('');
        refreshBtnClick.show();
        $('#login').hide();
        $('#CLICK').height('auto');
        captchaId = result.data.captchaId;
        $('#verify-section-bg-click').attr('src', 'data:image/jpeg;base64,'  + result.data.imageData);
        $('#verify-section-small-click').attr('src', 'data:image/jpeg;base64,' + result.data.smallImageData);
        count = result.data.count;
        clickCount = 0;
        selectData = [];
        initBtn();
    }

    refreshBtnClick.on('click', function(event) {
        initBtn();
        getCode();
        event.stopPropagation();
    })

    function verifyCodeClick(code) {
        var obj = {
            type: "post",
            url: 'user/conVertifyCode',
            data: {
                captchaId: captchaId,
                code: JSON.stringify(code)
            },
            done: function (result) {
                $('#isSlide').val(1);
                $('#globalCode').val(code);
                $('#captchaId').val(captchaId);
                refreshBtnClick.hide();
                $('#result-click').removeClass('succeed failed').addClass('succeed').html("验证成功");
                $('#verify-section-click').off('click');
                $('#login').click();
            },
            fail: function (result) {
                $('#result-click').removeClass('succeed failed').addClass('failed').html("验证失败");
                //刷新
                getCode();
            }
        };
        ma.ajax(obj);
    }
});
layui.use(["element", "layer"], function() {

    var element = layui.element();
    var layer = layui.layer;

    var leave_time;

    bindUserMenu();

    //bindNav();

    //initMenu();
    bindMenu()
    bindPageLoad();

    bindSplit();
    
    /**
     * 呼出用户菜单
     */
    function bindUserMenu() {
        $(".ma-user-menu dd a").click(function() {
            var _url = $(this).attr("data-url")
            if ("LoginOut" === _url) {
                layer.confirm({
                    content: "确定要退出登录？",
                    yes: function() {
                        var obj = {
                            type: "POST",
                            url: "user/permission/login/quit",
                            data: {},
                            success: function() {
                                window.location.href = "login";
                            },
                            error: function() {
                                window.location.href = "login";
                            }
                        };
                        layer.showLoad();
                        ma.ajax(obj);
                    }
                })
            } else {
                layer.iframe({
                    content: ma.host + _url,
                    area: ["580px", "380px"],
                    yes: function(index, page) {
                        console.log(index)
                        console.log(page)
                        var btn = layer.getChildFrame('#btnSubmit', index);
                        btn && btn.click()
                    }
                });
            }
            $(".ma-user-menu dl").removeClass("layui-show")
        })
    }

    /**
     * 一级导航绑定
     */
    function bindNav() {
        $(".ma-nav-more").click(function() {
            $(".ma-nav,.ma-nav-more").toggleClass("all")
        })
        $(".ma-nav").hover(function() {
            $(".ma-nav,.ma-nav-more").toggleClass("all")
        })
        $(".ma-nav>li>a").each(function(index, el) {
            $(el).click(function() {
                if (!$(this).parent("li").hasClass("layui-this")) {
                    initMenu(index);
                }
            })
        })
    }

    /**
     * 二级导航加载
     */
    function initMenu(index) {
        if (!menuList.childMenu.length) {
            layer.warning({
                content: "您没有任何权限，请联系管理员为您分配相应权限后重新登录!",
                time: 4000,
                end: function() {
                    window.location.href = ma.host + 'login';
                }
            });
            return;
        }
        index = index || 0;
        $(".ma-menu").empty();
        var _list = menuList.childMenu[index].childMenu;
        $.each(_list, function(k, v) {
            var _$li = $('<li class="layui-nav-item layui-nav-itemed"><a href="javascript:;">' + v.name + '</a><dl class="layui-nav-child"></dl></li>');
            if (v.childMenu) {
                $.each(v.childMenu, function(kk, vv) {
                    _$li.find(".layui-nav-child").append('<dd><a href="javascript:;" data-url="' + vv.target + '">' + vv.name + '</a></dd>')
                })
            }
            $(".ma-menu").append(_$li);
        })

        element.init();
        bindMenu()
    }

    /**
     * 二级导航绑定
     */
    function bindMenu() {
        $(".ma-menu>li").click(function() {
            if ($(this).hasClass("layui-this")) {
                var _url = $(this).find('a').attr("data-url");
                var _hash = $(this).find('a').attr("data-hash");
                if (_url) {
                    layer.showLoad();
                    $(".ma-iframe").attr("src", _url);
                    location.hash=_hash;
                }
            }
        })
    }

    /**
     * 页面加载Loading
     */
    function bindPageLoad() {
        $(".ma-iframe").on("load", function() {
            layer.ready(function() {
                layer.hideLoad();
            });
        })
    }
    /**
     * 菜单显示/隐藏条
     */
    function bindSplit() {
        $(".ma-split").on("click", function() {
            $(".ma-split,.ma-side,.ma-body").toggleClass("on");
        })
    }
    if(location.hash){
        var hash = location.hash.split('/')[1]
        $(".ma-iframe").attr("src", './page/loan/'+hash+'.html');
        $(".ma-menu a").each(function(i,item){
            if($(item).attr('data-hash').indexOf(hash)>-1){
                $(item).parents('li').removeClass('layui-nav-itemed').addClass('layui-this')
            }
        })
    }
});
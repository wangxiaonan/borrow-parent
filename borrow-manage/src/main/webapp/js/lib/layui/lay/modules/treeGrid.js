/** layui-v1.0.0 MIT License By http://www.layui.com */
 ;layui.define("grid",function(e){var d=layui.grid,r={config:d.config,createNew:function(e){var r=d.createNew(e);r.pageSize=void 0,r.id=e.id||"id",r.parentid=e.parentid||"parentid",r.root=e.root||0,r.order=e.order||r.id,r.asc=e.asc||"asc",r.loadRow=e.loadRow,r.dataSuccess=function(d){e.dataSuccess&&e.dataSuccess(d);var n=[];i.treeData(d.rows,n,r.root),r.pageData.rows=n};var n='<span class="treegrid-expander"></span>',t='<span class="treegrid-indent"></span>',i={treeData:function(e,d,n){"[object Array]"!=Object.prototype.toString.call(e)&&(e=[e]);var t=[];$.each(e,function(e,d){d[r.parentid]==n&&t.push(d)}),t.sort(function(e,d){var n=e[r.order],t=d[r.order];return n==t?0:n>t?1:-1}),t.length>0&&$.each(t,function(n,t){d.push(t),i.treeData(e,d,t[r.id])})},nodeId:function(e){var d=/treegrid-([A-Za-z0-9_-]+)/;return d.test(e.attr("class"))?d.exec(e.attr("class"))[1]:null},nodePid:function(e){var d=/treegrid-parent-([A-Za-z0-9_-]+)/;return d.test(e.attr("class"))?d.exec(e.attr("class"))[1]:null},expanded:function(e,d){var n,t;void 0!=d?(t=r.elem.find(".grid-body tr.treegrid-"+d),t.removeClass("treegrid-collapse").addClass("treegrid-expanded"),n=t.nextAll()):n=r.elem.find(".grid-body tbody tr"),n.each(function(n,a){if(a=$(a),t&&a.find("span.treegrid-indent,span.treegrid-expander").length<=t.find("span.treegrid-indent,span.treegrid-expander").length)return!1;var o=i.nodePid(a),s=a.prevAll(".treegrid-"+o);(e||s.hasClass("treegrid-expanded")||void 0==d&&i.nodeId(s)==r.root)&&a.show(),(a.hasClass("treegrid-collapse")&&e||void 0==d&&null==o)&&a.removeClass("treegrid-collapse").addClass("treegrid-expanded")}),r.resize()},cellapsed:function(e,d){var n,t;void 0!=d?(t=r.elem.find(".grid-body tbody tr.treegrid-"+d),t.removeClass("treegrid-expanded").addClass("treegrid-collapse"),n=t.nextAll()):n=r.elem.find(".grid-body tbody tr"),n.each(function(r,n){if(n=$(n),t&&n.find("span.treegrid-indent,span.treegrid-expander").length<=t.find("span.treegrid-indent,span.treegrid-expander").length)return!1;var a=i.nodePid(n);null!=a&&n.hide(),(n.hasClass("treegrid-expanded")&&e||void 0==d&&null==a)&&n.removeClass("treegrid-expanded").addClass("treegrid-collapse")}),r.resize()},onExpand:function(){var e=$(this).parents("tr").last(),d=i.nodeId(e);e.hasClass("treegrid-expanded")?i.cellapsed(!1,d):i.expanded(!1,d),event.stopPropagation()},onLoadRow:function(){var e=$(this),d=e.parents("tr").last().removeClass("treegrid-collapse").addClass("treegrid-loading"),n=i.nodeId(d);e.unbind("click"),r.loadRow({pid:n,children:function(a){d.removeClass("treegrid-loading"),a?a.length>0?(r.insertChild(a,n),e.on("click",i.onExpand),d.addClass("treegrid-expanded")):e.replaceWith(t):d.addClass("treegrid-wrong")}})},treeDom:function(e,d){function a(e,d){e.nextAll().each(function(r,n){return!($(n).find("span.treegrid-indent,span.treegrid-expander").length<=e.find("span.treegrid-indent,span.treegrid-expander").length)&&(d?$(n).addClass("selected"):$(n).removeClass("selected"),void($(n).find("td:first :checkbox").length>0&&($(n).find("td:first :checkbox")[0].checked=d)))})}function o(e,d){var n=r.elem.find("tr.treegrid-"+d);n.length>0&&(e&&!n.hasClass("selected")?(n.addClass("selected"),n.find("td:first :checkbox").length>0&&(n.find("td:first :checkbox")[0].checked=!0)):0==n.nextAll("tr.treegrid-parent-"+d+".selected").length&&(n.removeClass("selected"),n.find("td:first :checkbox").length>0&&(n.find("td:first :checkbox")[0].checked=!1)),d=i.nodePid(n),null!=d&&o(e,d))}"[object Array]"!=Object.prototype.toString.call(d)&&(d=[d]),$.each(e,function(e,n){n=$(n),id=d[e][r.id],pid=d[e][r.parentid],n.addClass("treegrid-"+id),pid!=r.root&&n.addClass("treegrid-parent-"+pid),r.singleSelect||n.click(function(){var e=$(this).hasClass("selected");a($(this),e);var d=i.nodePid($(this));null!=d&&o(e,d)})}),$.each(e,function(e,d){d=$(d);var a=i.nodeId(d),o=i.nodePid(d);if(d.next(".treegrid-parent-"+a).length>0?$(n).on("click",i.onExpand).prependTo(d.addClass("treegrid-expanded").find("td:eq(0)")):r.loadRow&&r.pageData.rows[d.index()]._children?$(n).on("click",i.onLoadRow).prependTo(d.addClass("treegrid-collapse").find("td:eq(0)")):d.find("td:eq(0)").prepend(t),r.elem.find("tr.treegrid-"+o).length>0){for(var s=r.elem.find("tr.treegrid-"+o).find(".treegrid-expander").prevAll().length,l="",g=0;g<s;g++)l+=t;d.find("td:eq(0)").prepend(l+t)}}),r.resize()}};return r.success=function(){e.success&&e.success(),i.treeDom(r.elem.find(".grid-body tbody tr"),r.pageData.rows)},r.collapse=function(e){i.cellapsed(!1,e)},r.collapseAll=function(e){i.cellapsed(!0,e)},r.expand=function(e){i.expanded(!1,e)},r.expandAll=function(e){i.expanded(!0,e)},r.getChild=function(e){var d=[],n=r.elem.find(".grid-body tr.treegrid-"+e);n.index();return $.each(n.nextAll(".treegrid-parent-"+e),function(){d.push(r.pageData.rows[$(this).index()])}),d},r.getChildAll=function(e){var d=[],n=r.elem.find(".grid-body tr.treegrid-"+e),t=n.index();return $.each(n.nextAll(),function(e,i){var i=$(i);return!(i.find("span.treegrid-indent,span.treegrid-expander").length<=n.find("span.treegrid-indent,span.treegrid-expander").length)&&void d.push(r.pageData[t+e])}),d},r.insertNode=function(e,d){d=void 0!=d?d:r.root;var n=void 0;if(d!=r.root){var t=r.elem.find(".grid-body tr.treegrid-"+d);n=t.index(),d=i.nodePid(t)}var a=[];i.treeData(e,a,d);var o=r.insert(a,n);i.treeDom(o,a)},r.insertChild=function(e,d){d=void 0!=d?d:r.root;var n=void 0;if(d!=r.root){var t=r.elem.find(".grid-body tr.treegrid-"+d);n=t.index(),n++,t.addClass("treegrid-expanded"),0==t.find("td").first().find("span.treegrid-expander").length?t.find("td").first().find("span.treegrid-indent").last().removeClass("treegrid-indent").addClass("treegrid-expander").on("click",i.onExpand):$.each(t.nextAll(),function(e,r){var r=$(r);return!(r.find("span.treegrid-indent,span.treegrid-expander").length<=t.find("span.treegrid-indent,span.treegrid-expander").length)&&(n++,void(i.nodePid(r)==d&&r.show()))})}n>=r.pageData.rows.length&&(n=void 0);var a=[];i.treeData(e,a,d);var o=r.insert(a,n);i.treeDom(o,a)},r.updateNode=function(e,d){if(void 0==d)return void console.log("treegrid error:参数id错误");var n=r.elem.find(".grid-body tr.treegrid-"+d),t=n.index(),a=r.update(e,t);i.treeDom(a,e)},r.deleteNode=function(e){if(void 0==e)return void console.log("treegrid error:参数id错误");var d=r.elem.find(".grid-body tr.treegrid-"+e),n=d.index(),t=[n];$.each(d.nextAll(),function(e,r){var r=$(r);return!(r.find("span.treegrid-indent,span.treegrid-expander").length<=d.find("span.treegrid-indent,span.treegrid-expander").length)&&(n++,void t.push(n))}),r["delete"](t)},r.editNode=function(e){var d,n,t;if(void 0!=e){var a=r.elem.find(".grid-body tbody tr.treegrid-"+e);d=a.index()}r.edit(d),void 0!=d&&d>=0?(n=r.elem.find(".grid-body tbody tr:eq("+d+")"),t=r.pageData.rows[d]):(n=r.elem.find(".grid-body tbody tr"),t=r.pageData.rows),i.treeDom(n,t)},r.endEditNode=function(e){var d,n,t;if(void 0!=e){var a=r.elem.find(".grid-body tbody tr.treegrid-"+e);d=a.index()}r.endEdit(d),void 0!=d&&d>=0?(n=r.elem.find(".grid-body tbody tr:eq("+d+")"),t=r.pageData.rows[d]):(n=r.elem.find(".grid-body tbody tr"),t=r.pageData.rows),i.treeDom(n,t)},r}};e("treegrid",r)});
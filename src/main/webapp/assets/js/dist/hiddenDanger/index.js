$(function() {
	$.menuIsLoad=false;
	var loadMsg = "数据加载中..."
	var menuUrl = baseUrl + "/hiddenDanger/getMenu";
	$.ajax({
		type : "post",
		url : menuUrl,
		data : {
			pid : pid
		},
		dataType : "json",
		success : function(data) {
			bindMenu($("#menu").append(makeMenu(data)).find("a"));	
		}
	});
	
	/**
	 * 绑定菜单时间
	 */
	function bindMenu(A){
		$(A).click(function(){
			if($.menuIsLoad){
				return;	
			}
			var _this = $(this);
			
			var href = _this.attr("af");
			if(href){
				navTitle(_this);
				$(".active").removeClass("active").removeClass("open");
				_this.parents("li").addClass("active").addClass("open");
				_this.parent().removeClass("open").addClass("active");
				
				$.menuIsLoad = true;
				$.gohref(href);
			}
		});
	}
	
	$.gohref = function(href){
		var jqCss=".content-wrapper .content";
		var pageContent = $(jqCss);
		pageContent.children().remove();
		$(pageContent[0].outerHTML).insertAfter(pageContent);
		pageContent.remove();
		$(jqCss).html(loadMsg).load(href,response);
	}
	
	/**
	 * 导航标题
	 */
	function navTitle(_this){
		setTimeout(function(){
			var as = _this.parents("li").find("a:eq(0)");
			var navTitle = $(".breadcrumb");
			var li = navTitle.find("li").eq(0).clone();
			navTitle.children().remove();
			navTitle.append(li);
			
			$.each(as,function(){
				var _ts = $(this);
				var text = _ts.find(".menu-text").html();
				var lli = li.clone();
				lli.find("i").removeAttr("class").toggleClass(_ts.attr("class"));
				lli.find("a").html(text).attr("href","javascript:void(0)").attr("af",_ts.attr("af"));
				navTitle.append(lli);
				bindMenu(this);
				
			});
			var last = $(".breadcrumb li:last");
			last.html(last.text());
			
			// 更改内容标题
			$("#contentHeader").text(last.text());
		}, 0);
	}
	
	/**
	 * 返回
	 */
	function response(responseTxt,statusTxt,xhr){
		$.menuIsLoad = false;
		/*if(statusTxt=="error"){
			pc.alert("Error: "+xhr.status+": "+xhr.statusText);
		}*/
	}
	
	
	
	function makeMenu(nodes){
		var menu = "";
		$.each(nodes,function(i,d){
			var size = d.children.length;
			
			if(size>0){
				menu += '<li>';
			}
			else{
				menu += '<li class="treeview">';
			}
			
			var hfUrl ="";
			if(d.attributes.url){ hfUrl = baseUrl+d.attributes.url; }
			
			menu += '<a href="javascript:void(0)" af="' + hfUrl + '">';
			menu += '<i class="fa fa-dashboard"><\/i><span class="menu-text">' + d.text +'<\/span><i class="fa fa-angle-left pull-right"><\/i>';
			menu += '<\/a>';
			
			if(size>0){
				menu += '<ul class="treeview-menu">';
				menu += makeMenu(d.children);
				menu += '<\/ul>';
			}

			menu += '<\/li>';
			
		});
		
		return menu;
	}
	
    
    $("#btnRet").click(function(){
    	 window.location.href = baseUrl + "/home";
    });
    
    $("#btnLogout").click(function(){
   	 window.location.href = baseUrl + "/logout";
   });
});
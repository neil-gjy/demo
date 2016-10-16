$(function() {
	
	$('#loginBtn').click(function () {
		

        var loginMsg = $(".login-box-msg");
        
        $("#loginForm").ajaxSubmit({
            type: "post",
            url: baseUrl + "/login",
            dataType: "json",
            success: function (msg) {
                if (msg) {
                    if (msg.code) {
                    	loginMsg.html(msg.msg);
                        window.location.href = baseUrl + "/home";
                    }
                    else {
                    	loginMsg.html(msg.msg);
                    }
                }
                else {
                	loginMsg.html("异常");
                }
            },
            error: function () {
            	loginMsg.html("网络连接错误，请重试！");
            }
        });
        // 为了防止普通浏览器进行表单提交和产生页面导航（防止页面刷新？）返回false
        return false;
    });
});
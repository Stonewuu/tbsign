$(function(){
	$("#loginBtn").click(function(){
		var flag = validateArticleForm($("#loginForm"));
		if(!flag){
			return false;
		}
		var param = $("#loginForm").serialize();
		$.post(basePath+"/auth/loginIn",param,function(data){
			if(data.status){
				location.href = basePath+"/";
			}else{
				layer.alert(data.msg,{
					icon:2,
					closeBtn: 0
				},function(index){
					layer.close(index);
					location.reload();
				});
			}
		},"json");
	});
});
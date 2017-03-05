$(function(){
	$("#loginBtn").click(function(){
		var flag = validateArticleForm($("#loginForm"));
		if(!flag){
			return false;
		}
		var param = $("#loginForm").serialize();
		$.post(path+"/auth/loginIn",param,function(data){
			if(data.status){
				location.href = path+"/";
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
	
	$(".input-element input").keydown(function(event){
		if(event.keyCode==13){
			$(this).blur();
			$("#loginBtn").click();
		}
	});
});
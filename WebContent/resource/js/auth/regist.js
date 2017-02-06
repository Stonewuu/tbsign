$(function(){
	$("#registBtn").click(function(){
		var flag = validateArticleForm($("#registForm"));
		if(!flag){
			return false;
		}
		var param = $("#registForm").serialize();
		$.post(basePath+"/auth/registIn",param,function(data){
			if(data.status){
				layer.alert("注册成功！将跳转至首页。", {closeBtn: 0}, function(index){
					layer.close(index);
					location.href = basePath+"/";
				});
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
$(function() {

	$("#bindBduss").click(function(){
		var bduss = $("input[name='bduss']").val();
		$.post(path+"/bindInfo/submitBduss",{bduss:bduss},function(data){
			if(data.status){
				layer.alert("绑定成功",{
					closeBtn: 0
				},function(index){
					layer.close(index);
					location.reload();
				});
			}else{
				layer.alert(data.msg,{
					closeBtn: 0
				});
			}
		},"json");
	});
	


	$(".unbindBduss").click(function() {
		var bdid = $(this).attr("bdid");
		if(bdid==""){
			layer.alert("bduss不能为空！", {
				closeBtn : 0
			});
			return;
		}
		layer.confirm('确认解除绑定？', {
			btn : [ '是', '否' ]
			// 按钮
		}, function() {
			$.post(path + "/bindInfo/unbindBduss", {
				info_id : bdid
			}, function(data) {
				if (data.status) {
					layer.alert("解除绑定成功", {
						closeBtn : 0
					}, function(index) {
						layer.close(index);
						location.reload();
					});
				} else {
					layer.alert(data.msg, {
						closeBtn : 0
					});
				}
			}, "json");
		}, function() {
			
		});

	});
});


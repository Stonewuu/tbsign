$(function() {
	$("#refreshForum").click(function(){
		var uid = $("input[name='uid']").val();
		if(uid==""){
			layer.alert("还没有选择账号你让人家怎么刷新嘛~",{
				closeBtn: 0
			});
			return;
		}
		$.post(basePath+"/sign/refreshForum",{uid:uid},function(data){
			if(data.status){
				layer.alert("刷新列表成功！",{
					closeBtn: 0
				},function(index){
					layer.close(index);
					location.reload();
				});
			}else{
				layer.alert("刷新失败",{
					closeBtn: 0
				});
			}
		},"json")
	});
	$("#bdselect").change(function(){
		var id = $(this).val();
		if(id==""){
			return;
		}
		location.href=basePath+"/sign/id_"+id;
	});
});


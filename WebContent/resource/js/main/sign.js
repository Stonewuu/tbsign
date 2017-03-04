$(function() {
	/**
	 * 刷新列表
	 */
	$("#refreshForum").click(function(){
		var uid = $("input[name='uid']").val();
		if(uid==""){
			layer.alert("还没有选择账号你让人家怎么刷新嘛~",{
				closeBtn: 0
			});
			return;
		}
		$.post(path+"/sign/refreshForum",{uid:uid},function(data){
			if(data.status){
				layer.alert("刷新列表成功！",{
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
	
	/**
	 * 切换百度账号
	 */
	$("#bdselect").change(function(){
		var id = $(this).val();
		if(id==""){
			return;
		}
		location.href=path+"/sign/id_"+id;
	});
	
	/**
	 * 测试签到
	 */
	$(".testSign").click(function(){
		var uid = $("input[name='uid']").val();
		var forum_id = $(this).attr("forum_id");
		$.post(path+"/sign/signSingleForum",{uid:uid,forum_id:forum_id},function(data){
			if(data.status){
				layer.alert("签到成功！",{
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
	
	/**
	 * 测试签到所有贴吧
	 */
	$("#signAllForum").click(function(){
		var uid = $("input[name='uid']").val();
		$.post(path+"/sign/signAllForum",{uid:uid},function(data){
			if(data.status){
				layer.alert(data.msg,{
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
});


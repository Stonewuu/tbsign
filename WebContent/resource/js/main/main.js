$(function() {
	$('.input-element input').val("");
	$('.input-element input').focusin(function() {
		$(this).parent().addClass('active');
	});

	$('.input-element input').blur(function() {
		$(this).parent().addClass('active');
		$(this).next("label").removeClass("error");
		if (!$(this).val().length > 0) {
			$(this).parent().removeClass('active');
		}
	});

	$(".menu_icon").click(function(){
		$(".menu_content").toggleClass("active");
	});
	$(".content").click(function(){
		$(".menu_content").removeClass("active");
	});
});

/* 验证表单 */
function validateArticleForm(form) {
	var flag = true;
	var inputs = form.find("[notNull='true']");
	var msg = "";
	for (var i = 0; i < inputs.length; i++) {
		if (inputs.eq(i).val() == "") {
			inputs.eq(i).next("label").addClass("error");
			flag = false;
			msg+=inputs.eq(i).attr("nullmsg")+"</br>";
		} else {
			inputs.eq(i).next("label").removeClass("error");
		}
	}
	if(!flag){
		layer.alert(msg,{
			icon:2,
			closeBtn: 0
		});
	}
	return flag;
}
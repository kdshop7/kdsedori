$(function(){
	$("form").submit(function(){
	
		$("input[type='text'].validate,textarea.validate").each(function(){
			
			//必須項目のチェック
			if($(this).hasClass("shipping_costs")){
				alert($(this).val());
				
			}
			
			
		});
		
		//ラジオボタンのチェック
		$("input[type='radio'].validate.required").each(function(){

			if($("input[name="+$(this).attr("name")+"]:checked").length == 0){
				$(this).parent().prepend("<p class='error'>選択してください</p>");
			}
		});
		
		//チェックボックスのチェック
		$(".checkboxRequired").each(function(){
			if($(":checked",this).length==0){
				$(this).prepend("<p class='error'>選択してください</p>");
			}
		});
		
		// その他項目のチェック
		$(".validate.add_text").each(function(){
			if($(this).prop("checked") && $("input[name="+$(this).attr("name").replace(/^(.+)$/, "$1_text")+"]").val()==""){
				$(this).parent().prepend("<p class='error'>その他の項目を入力してください。</p>");
			}
		});
		
		//エラーの際の処理
		if($("p.error").length > 0){
			$('html,body').animate({
				scrollTop: $("p.error:first").offset().top-40
			}, 'slow');
			$("p.error").parent().addClass("error");
			return false;
		}
	});
});
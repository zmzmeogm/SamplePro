<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script type="text/javascript">
$(document).ready(function(){
	$("#checkbox").hide();
	$("#ceckbutton").hide();
	var content = "";
	
	$("#email").on("click",function(event){
		
		var email=$(this).siblings("#textbox").val();
		console.log(email);
		
		$("#authorize").html(
			"<input type='text' name='autorize' id='confirm' placeholder='check num'><button id='ok'>인증확인</button>"	
		);
		$.ajax({
			url:"sendMailServlet?email="+email,
			type:"get",
			dataType:"json",
			success:function(responseData,status,xhr){
				content = responseData.content;
				console.log(content);
			}
		});
		
	});
	
	$(document).on("click", "#ok", function(){
		var check=$(this).siblings("#confirm").val();
		if(check===content){
			alert("sucess");
		}else{
			alert("false");
		}
	});
	
});
</script>
</head>
<body>
<!-- <input id="checkBox" type="text" placeholder="CheckNumber">
<button id="emailCheck" >인증번호 확인</button> -->
<span id="authorize">
	<input id="textbox" type="text" placeholder="your email">
	<button id="email" >인증번호</button>
</span>
</body>
</html>
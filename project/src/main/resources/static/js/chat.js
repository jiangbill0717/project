
sendMsgToTarget=function(){
	$("#personalMsg").append($('#username').text()+":"+ $("#msgtext").val()+'<hr>');
	var divscll = document.getElementById('personalMsg');
	divscll.scrollTop = divscll.scrollHeight;
	sendMsgToServer();
	$("#msgtext").val("");
}

function showMsg(message) {
	$("#targetUser").text(message.username);
	if ($("#username").text() == message.to) {
		$("#personalMsg").append(message.username + ":" + message.msg + '<hr>');
	}
	var divscll = document.getElementById('personalMsg');
	divscll.scrollTop = divscll.scrollHeight;
	$("#chatWindow").show();
}
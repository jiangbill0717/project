$(function(){
	var webSocket = getWebSocket();
	
	
	function getWebSocket() {
	    /**
		 * WebSocket客户端 PS：URL开头表示WebSocket协议 中间是域名端口 结尾是服务端映射地址
		 */
	    var webSocket = new WebSocket('ws://localhost:8080/chat');
	    /**
		 * 当服务端打开连接
		 */
	    webSocket.onopen = function (event) {
	        console.log('WebSocket打开连接');
	    };

	    /**
		 * 当服务端发来消息：1.广播消息 2.更新在线人数
		 */
	    webSocket.onmessage = function (event) {
	        console.log('WebSocket收到消息：%c' + event.data, 'color:green');
	        // 获取服务端消息
	        var message = JSON.parse(event.data) || {};
	        var $messageContainer = $('#message-container');
	        if (message.type === 'SPEAK') {
	            $messageContainer.append(
	                '<div class="card border-primary mb-3">' +
	                '<div class="card-header">' + message.username +
	                '</div><div class="card-body"><p class="card-text">'+ message.msg + '</p>' +
	                '</div></div>');
	        }
	        $('#chat-num').text(message.onlineCount);
	        // 防止刷屏
	        var $cards = $messageContainer.children('.card:visible').toArray();
	        if ($cards.length > 4) {
	            $cards.forEach(function (item, index) {
	                index < $cards.length - 5 && $(item).slideUp('fast');
	            });
	        }
	    };

	    
	    /**
	     *<div class="card border-primary mb-3" style="max-width: 20rem;">
		  <div class="card-header">Header</div>
		  <div class="card-body">
		    <h4 class="card-title">Primary card title</h4>
		    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
		  </div>
		</div>
	     * */
	    /**
		 * 关闭连接
		 */
	    webSocket.onclose = function (event) {
	        console.log('WebSocket关闭连接');
	    };

	    /**
		 * 通信失败
		 */
	    webSocket.onerror = function (event) {
	        console.log('WebSocket发生异常');

	    };
	    return webSocket;
	}

	/**
	 * 通过WebSocket对象发送消息给服务端
	 */
	sendMsgToServer=function() {
	    var $message = $('#msg');
	    if ($message.val()) {
	        webSocket.send(JSON.stringify({username: $('#username').text(), msg: $message.val()}));
	        $message.val(null);
	    }

	}

	/**
	 * 清屏
	 */
	clearMsg=function() {
	    $(".message-container").empty();
	}

	/**
	 * 使用ENTER发送消息
	 */
	document.onkeydown = function (event) {
	    var e = event || window.event || arguments.callee.caller.arguments[0];
	    e.keyCode === 13 && sendMsgToServer();
	};
});

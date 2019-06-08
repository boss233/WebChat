var stompClient = null;
var name = null;
var sessionId = null;
var onlineCount = 0;

function connect() {

    name = document.getElementById('username').value;
    if (name == null || name == '') {
        alert("username is undefine");
        return;
    }
    if (stompClient != null) {
        alert("already connected");
        return
    }

    var url = 'http://localhost:8080/stomp/stomp_server';
    var socket = new SockJS(url, undefined, {transports: ['websocket']});  
    //新建stomp客户端
    stompClient = Stomp.over(socket); 
    //stomp请求与服务器建立连接 connet({},function(),function())第一个参数Map是请求的头信息，第二个参数是请求成功回调函数，第三个函数是请求失败回调函数
    //stompClient.connect({name:name}, connectSuccess, connectError);
    var headers = { "name" : name};
    stompClient.connect(headers , function (frame) {
        //console.log('Connected: ' + frame);
        sessionId = /\/([^\/]+)\/websocket/.exec(socket._transport.url)[1];
        console.log("connected, session id: " + sessionId);

        document.getElementById('loginUser').innerText = "已登录用户: " + name;

        initSubscribeConnect()

        getAreadyOnlineUser()
        sendLoginMessage();

    }, connectError);

}


var connectError = function () {
    console.log('Connect error');
}


function initSubscribeConnect() {

    stompClient.subscribe("/topic/userLogin", function (result) {
        var message = JSON.parse(result.body);

        if (message.command == "connect") {
            addOnlineUser(message.username, message.sessionId);
        } else if (message.command == "disconnect") {
            deleteOnlineUser(message.sessionId);
        }
        
    }, {name:name});

    stompClient.subscribe("/user/queue/message", function (result) {
        //var message = JSON.parse(result.body);
        //addOnlineUser(message.username, message.sessionId);
        var message = JSON.parse(result.body);

        addMessage(message.sendFrom, message.sendTo, message.content);

        //alert("get message");
    }, {name:name});

}


function disconnect() {
    if (stompClient !== null) {

        stompClient.disconnect( function() {
            console.log(name + " disconnect");
        } ,{name:name});
        document.getElementById('loginUser').innerText = "未登录"
        deleteOnlineUser(sessionId);
        
        stompClient = null
        name = null;
        sessionId = null;
        
    }
}

function getAreadyOnlineUser() {
    stompClient.subscribe("/app/alreadyUser", function (result) {

        var message = JSON.parse(result.body);

        if (message.onlineUserCount != 0) {

            var onlineUser = message.onlineUser;
            
            for(var i=0; i<message.onlineUserCount; i++) {
                for(var key in onlineUser[i]) {
                    addOnlineUser(onlineUser[i][key], key);
                }
            }
        }

    });
}

function sendLoginMessage() {
    var content = JSON.stringify({'name': name});
    stompClient.send("/app/userOnline", {}, content);
}


function addOnlineUser(username, sessionId) {

    onlineCount++;
    updateOnlineCount();

    var container = document.getElementById('onlineUser');

    var onlineuser = document.createElement('input');
    onlineuser.setAttribute("type", "checkbox");
    onlineuser.setAttribute("id", sessionId);
    onlineuser.setAttribute("name", "onlineUser");
    onlineuser.setAttribute("value", sessionId);

    var userLabel = document.createElement('label');
    userLabel.setAttribute('for', sessionId);
    userLabel.innerText = "Online User: " + username;

    container.appendChild(onlineuser);
    onlineuser.after(userLabel);
    userLabel.after(document.createElement('br'))

}

function deleteOnlineUser(sessionId) {

    onlineCount--;
    updateOnlineCount();

    var userInput = document.getElementById(sessionId);

    document.querySelector('label[for="'+userInput.id+'"]').remove();
    document.getElementById(sessionId).remove();
    
}

function updateOnlineCount() {
    document.getElementById("onlineCount").innerText = onlineCount;
}

function sendUserMessage() {
    var allUser = document.getElementsByName("onlineUser");
    for (var i=0; i<allUser.length; i++) {
        if (allUser[i].checked) {
            // alert(allUser[i].value);
            var message = document.getElementById("message").value;

            //var userInput = document.getElementById(allUser[i].value);
            //var toUsername = document.querySelector('label[for="'+userInput.id+'"]').innerText.substr(13);

            var sendMessage = {"sendFrom": sessionId, "sendTo": allUser[i].value, "content": message};
            stompClient.send("/app/userMessage", {}, JSON.stringify(sendMessage));

            if (sessionId != allUser[i].value) {
                addMessage(sessionId, allUser[i].value, message);
            }
        }
    }
}

function addMessage(sendFrom, sendTo, message) {

    var container = document.getElementById('right');

    var fromUsername = document.querySelector('label[for="'+sendFrom+'"]').innerText.substr(13);
    var toUsername = document.querySelector('label[for="'+sendTo+'"]').innerText.substr(13);

    var messageInfo = document.createElement('p');
    messageInfo.innerText = "发信人: " + fromUsername + " " + "收信人: " + toUsername;

    var messageText = document.createElement('p');
    messageText.innerText = "内容: " + message;

    container.appendChild(messageInfo);
    messageInfo.after(messageText);

}
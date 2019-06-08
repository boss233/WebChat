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
        sendMessage();

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

function sendMessage() {
    var content = JSON.stringify({'name': name});
    stompClient.send("/app/userOnline", {}, content);
}


function addOnlineUser(username, sessionId) {

    onlineCount++;
    updateOnlineCount();

    var container = document.getElementById('left');

    var onlineuser = document.createElement('p');
    onlineuser.innerHTML = "Online User: " + username;
    onlineuser.setAttribute("id", sessionId);

    container.appendChild(onlineuser);

}

function deleteOnlineUser(sessionId) {

    onlineCount--;
    updateOnlineCount();

    document.getElementById(sessionId).remove();

}


function updateOnlineCount() {
    document.getElementById("onlineCount").innerText = onlineCount;
}
var WebSocketClient = function () {
    this.ws;
    this.url;
    this.timer;
}

WebSocketClient.prototype.init = function (url) {
    this.url = url;
    var _this = this;
    if (!"WebSocket" in window) {
        alert("您的浏览器不支持 WebSocket!");
    }
    this.ws = new WebSocket(url);

    this.ws.onopen = function () {
        console.log("connect success")
        _this.timer = setInterval(function () {
            _this.send("{\"command\":\"ping\"}")
        }, 2000)
    };

    this.ws.onmessage = function (evt) {
        var message = eval('(' + evt.data + ')');
        console.log(message)
        if ("ping" == message.command) {
            _this.send("{\"command\":\"pong\"}")
        }
    };

    this.ws.onclose = function () {
        console.log("onclose")
        clearInterval(_this.timer)
        _this.reconnect(_this);
    };
}
WebSocketClient.prototype.send = function (msg) {
    this.ws.send(msg);
}
WebSocketClient.prototype.reconnect = function (context) {
    console.log("re connect...")
    setTimeout(function () {
        context.init(context.url);
    }, 2000)
}
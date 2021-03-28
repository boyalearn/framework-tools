var ws=new WebSocket("ws://localhost:8080/ws?name=sdfsdf&age=18");

ws.onopen = function() {
    this.send("{\"command\":\"rpc-call\",\"body\":\"我事一条消息\"}");
};

ws.onmessage = function(evt) {
    netWorkChange(evt);
};

ws.onclose = function(e) {
    console.log("onclose",e.code)
    console.log("onclose",e.reason)
};
var ws=new WebSocket("ws://localhost:8080/ws");

ws.onopen = function() {
    this.send("{\"optType\":\"loadMap\"}");
};

ws.onmessage = function(evt) {
    netWorkChange(evt);
};

ws.onclose = function() {
    console.log("onclose")
};
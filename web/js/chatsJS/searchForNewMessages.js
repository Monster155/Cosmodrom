function searchForNewMessages(){
    let req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        if (this.readyState != 4 || this.status != 200) return;
        document.getElementById("messages").innerHTML = this.responseText;
        alignMessages();
    };
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('chat') !== null) {
        req.open("get", window.location + "&sync", true);
        req.send();
    }
}

window.setInterval(function () {
    searchForNewMessages();
}, 2000);
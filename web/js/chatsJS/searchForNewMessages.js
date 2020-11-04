function searchForNewMessages() {
    let req = new XMLHttpRequest();
    let messages = document.getElementById("messages");
    req.onreadystatechange = function () {
        if (this.readyState != 4 || this.status != 200) return;
        canChange = false;
        if (this.responseText) {
            messages.innerHTML = this.responseText + messages.innerHTML;
            alignMessages();
        }
        canChange = true;
    };
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('chatID') !== null) {
        req.open("get", window.location + "&sync=" + messages.childElementCount, true);
        req.send();
    }
}

document.addEventListener('DOMContentLoaded', function () {
    searchForNewMessages();
    window.setInterval(function () {
        searchForNewMessages();
    }, 2000);
}, false);

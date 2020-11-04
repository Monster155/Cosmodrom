document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('chatID') == null) {
        hideMessages();
    }
}, false);

function hideMessages() {
    document.getElementById("chat-view").style.display = 'none';

}

function unhideMessages() {
    document.getElementById("chat-view").style.display = '';

}
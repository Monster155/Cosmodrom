function replaceURL(e) {
    window.history.replaceState({}, document.title, "/m?chatID=" + e.getAttribute('chatID'));
    document.getElementById('messages').innerHTML = '';
    searchForNewMessages();
    unhideMessages();
}
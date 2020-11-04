function replaceURL(e) {
    window.history.replaceState({}, document.title, "/m?chatID=" + e.getAttribute('chatID'));
    searchForNewMessages();
    unhideMessages();
}
function replaceURL(e) {
    window.history.replaceState({}, document.title, "/m?chat=" + e.getAttribute('chatID'));
    searchForNewMessages();
}
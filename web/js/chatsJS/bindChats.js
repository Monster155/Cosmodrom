function replaceURL(e) {
    let chatID = e.getAttribute('chatID');
    const urlParams = new URLSearchParams(window.location.search);
    window.history.replaceState({}, document.title, "/m?chatID=" + chatID);
    if (chatID !== urlParams.get('chatID')) {
        document.getElementById('messages').innerHTML = '';
        searchForNewMessages();
        unhideMessages();
    }
}
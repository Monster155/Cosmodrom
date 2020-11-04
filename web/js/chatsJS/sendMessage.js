function sendMessage(inputID) {
    if (isChanging) {
        confirmEditMessage();
    } else {
        let text = document.getElementById(inputID).value;
        if (text === '' || text == null) return;

        const urlParams = new URLSearchParams(window.location.search);
        let xhr = new XMLHttpRequest();

        console.log(window.location.pathname + '?chatID=' + urlParams.get('chatID')
            + '&text=' + text + '&profileID=' + profileID);
        xhr.open('post', window.location.pathname + '?chatID=' + urlParams.get('chatID')
            + '&text=' + text + '&profileID=' + profileID, true);

        xhr.send();
        document.getElementById(inputID).value = '';
        searchForNewMessages();
    }
}
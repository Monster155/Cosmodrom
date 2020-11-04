function editMessage() {
    isChanging = true;
    document.getElementById("send-message-input").value = selectedMessage.children.item(0).innerHTML;
}

function confirmEditMessage() {
    isChanging = false;
    let req = new XMLHttpRequest();
    const urlParams = new URLSearchParams(window.location.search);
    req.open("post", window.location +
        "&messageID=" + selectedMessage.getAttribute('messageID') +
        "&senderID=" + selectedMessage.getAttribute('senderID') +
        "&text=" + document.getElementById("send-message-input").value + "&update", true);
    console.log(window.location +
        "&messageID=" + selectedMessage.getAttribute('messageID') +
        "&senderID=" + selectedMessage.getAttribute('senderID') +
        "&text=" + document.getElementById("send-message-input").value + "&update");
    req.send();
    selectedMessage.children.item(0).innerHTML = document.getElementById("send-message-input").value;
    document.getElementById("send-message-input").value = '';
    hideChangeButtons();
}

function deleteMessage() {
    let req = new XMLHttpRequest();
    const urlParams = new URLSearchParams(window.location.search);
    req.open("post", window.location +
        "&messageID=" + selectedMessage.getAttribute('messageID') +
        "&senderID=" + selectedMessage.getAttribute('senderID') + "&delete", true);
    console.log(window.location +
        "&messageID=" + selectedMessage.getAttribute('messageID') +
        "&senderID=" + selectedMessage.getAttribute('senderID') + "&delete");
    req.send();
    selectedMessage.remove();
    hideChangeButtons();
}

function alignMessages() {
    let inputs = document.getElementsByClassName('message');
    for (let i = 0; i < inputs.length; i++) {
        if (inputs.item(i).getAttribute('senderID') == profileID) {
            inputs.item(i).style.alignSelf = 'flex-start';
        } else {
            inputs.item(i).style.alignSelf = 'flex-end';
        }
    }
}
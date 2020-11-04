function alignMessages() {
    let inputs = document.getElementsByClassName('message');
    for (let i = 0; i < inputs.length; i++) {
        if (inputs.item(i).getAttribute('senderID') == profileID) {
            inputs.item(i).style.alignSelf = 'flex-end';
            inputs.item(i).style.marginLeft = '100px';
        } else {
            inputs.item(i).style.alignSelf = 'flex-start';
            inputs.item(i).style.marginRight = '100px';
        }
    }
}
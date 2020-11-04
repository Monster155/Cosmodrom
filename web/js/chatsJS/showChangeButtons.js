function showChangeButtons() {
    document.getElementById('change').style.display = 'flex';
    if (profileID === selectedMessage.getAttribute('senderID')) {
        document.getElementById('change').removeAttribute('disabled');
    } else {
        document.getElementById('change').setAttribute('disabled', '');
    }
}

function hideChangeButtons() {
    document.getElementById('change').style.display = 'none';
}
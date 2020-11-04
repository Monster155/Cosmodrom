//TODO this
// CRUD
let selectedMessage = document.getElementById('messages');
let canChange = true;

function selectMessage(e) {
    if (!canChange) return;
    selectedMessage.style.background = '';
    e.style.background = '#6B50FE';
    selectedMessage = e;
    // showChangeButtons();
}
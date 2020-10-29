document.addEventListener('DOMContentLoaded', function () {
    switch (getSavedValue('side-bar-btn')) {
        case 'login':
            login();
            break;
        case 'registration':
            registration();
            break;
        case 'restore':
            restore();
            break;
        default:
            login();
    }

    let inputs = document.getElementsByTagName('input');
    for (let i = 0; i < inputs.length; i++) {
        inputs.item(i).value = getSavedValue(inputs.item(i).id);
        inputs.item(i).oninput = function () {
            saveValue(this.id, this.value);
        }
    }

    for (let i = 1; i <= 3; i++) {
        document.getElementById("email" + i).oninput = function (event) {
            changeValue("email", 3, event);
        };
    }

    for (let i = 0; i < localStorage.length; i++) {
        console.log(localStorage.key(i), localStorage.getItem(localStorage.key(i)));
    }
}, false);


function login() {
    document.getElementById("choose-btn").style.top = "0";
    document.getElementById("groups-container").style.top = "0";
    saveValue('side-bar-btn', 'login');
}

function registration() {
    document.getElementById("choose-btn").style.top = "110px";
    document.getElementById("groups-container").style.top = "-330px";
    saveValue('side-bar-btn', 'registration');
}

function restore() {
    document.getElementById("choose-btn").style.top = "220px";
    document.getElementById("groups-container").style.top = "-660px";
    saveValue('side-bar-btn', 'restore');
}

function saveValue(key, value) {
    localStorage.setItem(key, value);
}

function getSavedValue(key) {
    if (!localStorage.getItem(key)) {
        return "";
    }
    return localStorage.getItem(key);
}

function changeValue(pattern, count, event) {
    for (let i = 1; i <= count; i++) {
        document.getElementById(pattern + i).value = event.target.value;
        saveValue(pattern + i, event.target.value);
    }
}

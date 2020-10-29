switch (getSavedValue('side-bar-btn')) {
    case 'login':
        login();
        break;
    case 'register':
        register();
        break;
    case 'restore':
        restore();
        break;
    default:
        login();
}


function login() {
    document.getElementById("choose-btn").style.top = "0";
    document.getElementById("groups-container").style.top = "0";
    saveValue('side-bar-btn', 'login');
}

function register() {
    document.getElementById("choose-btn").style.top = "110px";
    document.getElementById("groups-container").style.top = "-330px";
    saveValue('side-bar-btn', 'register');
}

function restore() {
    document.getElementById("choose-btn").style.top = "220px";
    document.getElementById("groups-container").style.top = "-660px";
    saveValue('side-bar-btn', 'restore');
}

let inputs = document.getElementsByTagName('input');
for (let i = 0; i < inputs.length; i++) {
    inputs.item(i).value = getSavedValue(inputs.item(i).id);
    inputs.item(i).oninput = function (ev) {
        saveValue(inputs.item(i).id, inputs.item(i).value);
    }
}

for (let i = 1; i <= 3; i++) {
    document.getElementById("email" + i).oninput = function (event) {
        changeValue("email", 3, event);
    };

}

function changeValue(pattern, count, event) {
    for (let i = 1; i <= count; i++) {
        document.getElementById(pattern + i).value = event.target.value;
        saveValue(pattern + i, event.target.value);
    }
}

//Save the value function - save it to localStorage as (ID, VALUE)
function saveValue(key, value) {
    localStorage.setItem(key, value);// Every time user writing something, the localStorage's value will override .
}

//get the saved value function - return the value of "v" from localStorage.
function getSavedValue(key) {
    if (!localStorage.getItem(key)) {
        return "";// You can change this to your default value.
    }
    return localStorage.getItem(key);
}

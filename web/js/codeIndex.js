function login() {
    document.getElementById("choose-btn").style.top = "0";
    document.getElementById("groups-container").style.top = "0";
}

function register() {
    document.getElementById("choose-btn").style.top = "110px";
    document.getElementById("groups-container").style.top = "-330px";
}

function restore() {
    document.getElementById("choose-btn").style.top = "220px";
    document.getElementById("groups-container").style.top = "-660px";
}

for (let i = 1; i <= 3; i++) {
    document.getElementById("email" + i).oninput = function (event) {
        changeValue("email", 3, event);
    };
}

function changeValue(pattern, count, event) {
    for (let i = 1; i <= count; i++) {
        document.getElementById(pattern + i + "").value = event.target.value;
    }
}

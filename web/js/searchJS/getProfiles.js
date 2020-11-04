let isSendBefore = false;

function searchProfiles() {
    let req = new XMLHttpRequest();
    req.onreadystatechange = function () {
        if (this.readyState != 4 || this.status != 200) return;
        isSendBefore = false;
        document.getElementById("results").innerHTML = this.responseText;
    };
    // const urlParams = new URLSearchParams(window.location.search);
    console.log(window.location +
        "?searchText=" + document.getElementById("search-input").value);
    req.open("get", window.location +
        "?searchText=" + document.getElementById("search-input").value, true);
    isSendBefore = true;
    req.send();
}

document.addEventListener('DOMContentLoaded', function () {
    let input = document.getElementById("search-input");
    input.addEventListener("keyup", function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            document.getElementById("search-button").click();
        }
    });
}, false);

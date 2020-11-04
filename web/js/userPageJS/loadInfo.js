let xmlhttp = new XMLHttpRequest();
let url = window.location.pathname + window.location.search + "&get";
console.log(window.location);

let isContentLoaded = false;
document.addEventListener('DOMContentLoaded', function () {
    isContentLoaded = true;
}, false);

xmlhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
        console.log(this.responseText);
        let myArr = JSON.parse(this.responseText);
        if (isContentLoaded) {
            myFunction(myArr);
        } else {
            document.addEventListener('DOMContentLoaded', function () {
                myFunction(myArr);
            }, false);
        }
    }
};

xmlhttp.open("get", url, true);
xmlhttp.send();

function myFunction(arr) {
    console.log(arr);
    window.history.replaceState({}, document.title, "/user?id=" + arr.id);
    if (arr.id === profileID) document.getElementById("info3").style.display = 'block';
    // image
    if (arr.photo) {
        document.getElementById("photo-img").src = "data: image/png; base64, " + arr.photo;
        document.getElementById("photo-img").style.display = 'none';
        document.getElementById("photo").style.background = 'no-repeat center/cover url(' +
            '"data: image/png; base64, ' + arr.photo + '")';
    } else {
        document.getElementById("photo-img").src = "/imgs/space7.jpg";
    }

// others
    document.getElementById("name").innerHTML = arr.name + " " + arr.surname;
    document.getElementById("description").innerHTML = arr.description;
    document.getElementById("chat-list").innerHTML = arr.chats;
}
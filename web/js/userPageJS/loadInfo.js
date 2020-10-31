let xmlhttp = new XMLHttpRequest();
let url = window.location.pathname + window.location.search + "&get";
console.log(window.location);

xmlhttp.onreadystatechange = function () {
    if (this.readyState == 4 && this.status == 200) {
        console.log(this.responseText);
        let myArr = JSON.parse(this.responseText);
        myFunction(myArr);
    }
};

xmlhttp.open("get", url, true);
xmlhttp.send();

function myFunction(arr) {
    console.log(arr);
    window.history.replaceState({}, document.title, "/user?id=" + arr.id);
    document.getElementById("photo-img").src = "data: image/png; base64, " + arr.photo;
    document.getElementById("name").innerHTML = arr.name + " " + arr.surname;
    document.getElementById("description").innerHTML = arr.description;
    document.getElementById("chat-list").innerHTML = arr.chats;
}
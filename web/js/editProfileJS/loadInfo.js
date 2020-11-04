if (window.location.pathname === "/edit") {
    let xmlhttp = new XMLHttpRequest();
    let url = window.location.pathname + "?get";
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
        // image
        if (arr.photo) {
            setImage("data: image/png; base64, " + arr.photo);
        } else {
            setImage("/imgs/space7.jpg");
        }

        // others
        document.getElementById("name").value = arr.name;
        document.getElementById("surname").value = arr.surname;
        document.getElementById("description").value = arr.description;
    }
}
document.getElementById("photo").onclick = function () {
    console.log("click");
    document.getElementById("photo-input").click();
}

document.getElementById("photo-input").onchange = function (ev) {
    if (this.files && this.files[0]) {
        document.getElementById("photo-img").setAttribute("src",
            window.URL.createObjectURL(this.files[0]));
        uploadFile();
    }
}
// other version but first is faster works
// function (ev) {
//     //ensure a file was selected
//     if (this.files && this.files[0]) {
//         let imageFile = this.files[0];
//         let reader = new FileReader();
//         reader.onload = function (e) {
//             //set the image data as source
//             document.getElementById("photo-img").setAttribute("src", e.target.result + "")
//         }
//         reader.readAsDataURL(imageFile);
//     }
// }

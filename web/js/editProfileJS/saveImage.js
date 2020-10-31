function uploadFile() {
    let blobFile = document.getElementById("photo-input").files[0];
    let data = new FormData();
    data.append("fileToUpload", blobFile);
    let xhr = new XMLHttpRequest();
    xhr.open('post', '/reg', true);
    xhr.send(data);
}


document.addEventListener('DOMContentLoaded', function () {
    let input = document.getElementById("send-message-input");
    input.addEventListener("keyup", function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            document.getElementById("send-message-btn").click();
        }
    });
}, false);

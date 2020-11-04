//never used (servlet do this)
document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('id') !== null) {
        let req = new XMLHttpRequest();
        req.onreadystatechange = function () {
            if (this.readyState != 4 || this.status != 200) return;

            // const urlParams = new URLSearchParams(window.location.search);
            window.history.replaceState({}, document.title, "/m")
        };
        const urlParams = new URLSearchParams(window.location.search);
        req.open("get", window.location + "&sync", true);
        req.send();
    }
}, false);
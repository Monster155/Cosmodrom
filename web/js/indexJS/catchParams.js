let url = new URL(window.location.href);
console.log(url);
if (url.searchParams.get("return") === "-1") alert("Invalid email or password"); //invalid email or password
if (url.searchParams.get("return") === "-11") alert("passwords don't equals"); //passwords don't equals
if (url.searchParams.get("return") === "-12") alert("email already registered"); //email already registered
if (url.searchParams.get("return") === "-13") {
    alert("incorrect password type"); //password doesn't match regex
    document.addEventListener('DOMContentLoaded', function () {
        document.getElementById("password2").setCustomValidity("Wrong format of password. " +
            "Password should consist of 8-20 characters " +
            "which contains at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character.");
    }, false);
}
window.history.replaceState({}, document.title, "/");
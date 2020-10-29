let url = new URL(window.location.href);
console.log(url);
if (url.searchParams.get("return") === "-1") alert("Invalid email or password"); //invalid email or password
if (url.searchParams.get("return") === "-11") alert("passwords don't equals"); //passwords don't equals
if (url.searchParams.get("return") === "-12") alert("email already registered"); //email already registered
window.history.replaceState({}, document.title, "/");
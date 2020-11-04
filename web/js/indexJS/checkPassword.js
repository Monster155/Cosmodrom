document.addEventListener('DOMContentLoaded', function () {
    let password = document.getElementById("password2"),
        repeat_password = document.getElementById("password-repeat2");

    function checkPassword() {
        let check = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,20}$/;
        if (!password.value.match(check)) {
            password.setCustomValidity("Wrong format of password. " +
                "Password should consist of 8-20 characters " +
                "which contains at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character.");
        } else {
            password.setCustomValidity('');
        }
    }

    function validatePassword() {
        if (repeat_password.value !== '') {
            if (password.value !== repeat_password.value) {
                repeat_password.setCustomValidity("Passwords don't match!");
            } else {
                repeat_password.setCustomValidity('');
            }
        } else {
            repeat_password.setCustomValidity('');
        }
    }

    password.oninput = function () {
        checkPassword();
        validatePassword();
    };
    repeat_password.oninput = validatePassword;
}, false);
function valid(form) {
    var fail = false;
    var name = form.name.value;
    var surname = form.surname.value;
    var phone = form.phone.value;
    var email = form.email.value;
    var login = form.login.value;
    var password1 = form.password1.value;
    var password2 = form.password2.value;
    var email_pattern = /^[A-Z0-9._%+-]+@[A-Z0-9-]+.+.[A-Z]{2,4}$/i;
    var phone_pattern = /^\+?(\d{1,3})?[- .]?\(?(?:\d{2,3})\)?[- .]?\d\d\d[- .]?\d\d\d\d$/;
    var password_pattern = /[0-9a-zA-Z!@#$%^&*]{6,}/;
    var login_pattern = /[0-9a-zA-Z]{4,10}/;
    if (name === "" || name === " ") {
        fail = "Name is incorrect";

    } else if (surname === "" || surname === " ") {

        fail = "Surname is incorrect";
    } else if (phone_pattern.test(phone) === false) {

        fail = "Incorrect phone format";
    } else if (email_pattern.test(email) === false) {
        fail = "Incorrect email";

    } else if (login_pattern.test(login) === false) {
        fail = "Incorrect login format";

    } else if (password1 === "" || password1 === " ") {
        fail = "Incorrect password format";
    } else if (password1 !== password2) {
        fail = "Passwords not equals";
    }
    if (fail) {
        alert(fail);
        return false;
    }
}
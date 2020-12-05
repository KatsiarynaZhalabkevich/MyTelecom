function valid(form) {
    console.log("inside validation js");
    let fail = false;
    let login = form.login.value;
    let password = form.password.value;
    if (login === "" || login === " ") {
        fail = "Please, insert your login";
    } else if (password === "" || password === " ") {
        fail = "Please, insert your password";
    }
    if (fail) {
        alert(fail);
        return false;
    }
}
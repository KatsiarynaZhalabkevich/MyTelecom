function checkBalance(form) {
    let fail = false;
    let balance = form.balance;

    if (balance < 0) {
        fail = "Your balance < 0. Please, update balance to delete your account!"
    }
    if (fail) {
        alert(fail);
        return false;
    }

}
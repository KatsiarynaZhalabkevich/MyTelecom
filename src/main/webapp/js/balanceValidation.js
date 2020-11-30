function valid(form) {
    var fail = false;
    var balance = form.balance.value;
    if (balance === null && balance === '') {
        fail = "Field is empty!";
    } else if (balance.toString().includes(',')) {
        fail = "Please, use '.' instead of ','!";
    }
    if(fail){
        alert(fail);
        return false;
    }
}
function valid(form) {
    var fail = false;
    var name = form.name.value;
    var description = form.description.value;
    var speed = form.speed.value;
    var discount =form.discount.value;
    var price = form.price.value;

    if (name === "" || name === " ") {
        fail = "Name is incorrect!";
    } else if (description === "" || description === " ") {
        fail = "Description is incorrect!";
    } else if (speed ==="" && speed ===" ") {
        fail = "Speed is incorrect!"
    } else if (price ==="" && price ===" ") {
        fail = "Price is incorrect!";
    } else if (discount ==="" && discount ===" ") {
        fail = "Discount is incorrect!";
    }
    if (fail) {
        alert(fail);
        return false;
    }

}
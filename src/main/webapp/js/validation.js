function validateR(){
   checkboxes = document.querySelectorAll('input[type=checkbox]')
   for (let checkbox of checkboxes){
       if(checkbox.checked){
           return true;
       }
   }
   checkboxes[2].setCustomValidity("please choose R value");
   checkboxes[2].reportValidity();
   return false;
}

function validateX() {
    element = document.getElementById("X-input");
    y = element.value;
    if (parseFloat(y) > 5 || parseFloat(y) < -5 || isNaN(parseFloat(y))) {
        element.setCustomValidity("please enter a number from -5 to 5 :)");
        element.reportValidity();
        return false;
    } else {
        element.setCustomValidity("");
        element.reportValidity();
        return true;
    }
}
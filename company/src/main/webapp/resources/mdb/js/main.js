/**
 * Initialize MDB input controls. Required in order to display floating labels correctly.
 */
function initInputs() {
    document.querySelectorAll('.form-outline').forEach((formOutline) => {
        new mdb.Input(formOutline).init();
    });
}

/**
 * When using h:selectOneRadio, the value of styleClass is not added to the input (when using group attribute it is
 * ignored). This function adds form-check-input CSS class to each radio input.
 */
function fixRadio() {
    document.querySelectorAll('input[type="radio"]').forEach((input) => {
        input.classList.toggle('form-check-input');
    })
}

/**
 * Can be used with form onreset callback. Because onreset callback is called before actual form resetting, the
 * setTimeout workaround is used.
 */
function onReset() {
    setTimeout(initInputs, 0);
}

document.addEventListener("DOMContentLoaded", function(){
    initInputs();
    fixRadio();
});

var validateFlag = false;
function validate() {
    getInput = document.question.getElementsByTagName("input");

    if(validateFlag == true) {
        return;
    }
    validateFlag = true;

    for (var i = 0; i < getInput.length; i++) {
        if (getInput[i].value.trim() == "") {
            let getName = getInput[i].getAttribute("id");
            alert(getName + "를 입력하세요")
            validateFlag  = false;
            return;
        }
    }
    document.question.submit();
}

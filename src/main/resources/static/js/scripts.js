$(".answer-write button[type=submit]" ).click(addAnswer);

function addAnswer(e){
    e.preventDefault();
    console.log("click me")

    var queryString = $(".answer-write").serialize();
    console.log("query: " + queryString);

    var url = $(".answer-write").attr("action");


    $.ajax({

        type : "post",
        url : url,
        data : queryString,
        dataType : "json",
        error : onError,
        success : onSuccess
    });
}

function onError(){
}

function onSuccess(data, status){

}

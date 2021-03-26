$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e){
    console.log("click me"); //java : System.out.println고 같은 역할
    e.preventDefault(); // server가 데이터 전송이 원할하지 않게하기 위한 작업.

    var queryString = $(".answer-write").serialize(); //서버로 전달할 테이터를 읽어 오기.
    console.log("query : " + queryString);

    var url = $(".answer-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type :  'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : onError,
        success : onSuccess
    });
}

function onError(){
}

function onSuccess(data, status){
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.userId,
    data.created_date,
    data.modified_date,
    data.content,
    data.question.id,
    data.id);
// 해당 결과값 : {0},{1},{2}... 위치로 이동
    //answerTemplate : 해당 선언된 js script id
    //format : 하단에 선언한 메소드

    $(".qna-comment-slipp-articles").prepend(template);//최근 값을 위에 올리고 싶을 때 : prepend
    $("textarea[name=contents]").val("");//작성 창 초기화
    location.reload()
}

$(".qna-comment-slipp-articles").on("click", "a.link-delete-article", deleteAnswer);

//$(document).on('click', '.link-delete-article', deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();
    console.log("deleteAnswer");

    var deleteBtn = $(this);
    var url = $(this).attr("href");
    console.log("url :"+url);

    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',

        error : function (xhr, status) {
            console.log("error");
        },
        success : function (data, status) {
            console.log(data);

            if(data) {
                deleteBtn.closest("article").remove();
            }
        }

    });

}


String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

// var template = answerTemplate.format(data.writer.userId,
//     data.created_date,
//     data.modified_date,
//     data.content,
//     data.question.id,
//     data.id);

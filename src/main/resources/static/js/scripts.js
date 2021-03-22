$(".answer-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log("click !");

    var queryString = $(".answer-write").serialize();
    var url = $(".answer-write").attr("action");
    var strArr = queryString.split('=');
    console.log("queryString : " + queryString);
    console.log("url : " + url);

    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        contents: strArr[1],
        dataType: 'json',
        error: onError,
        success: onSuccess,
    });
}

function onError(data, status, contents) {
    console.log("Error");
    console.log("data : " + JSON.stringify(data));
    if (isEmpty(contents)) {
        alert("내용을 입력해주세요.")
    }
    else {
        alert("로그인한 유저만 작성 가능합니다.");
    }
}

function onSuccess(data, status) {
    console.log("Success");
    console.log("data : " + JSON.stringify(data));
    var answersTemplate = $("#answerTemplate").html();
    var template = answersTemplate.format(data.writer.userId, data.createdDateTime, data.contents, data.questionId, data.id);
    $(".qna-comment-slipp-articles").prepend(template);
    $("textarea[name=contents]").val("");
    location.reload();
}

$("a.link-delete-article").click(deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();
    console.log("deleteAnswer");

    var deleteBtn = $(this);
    var url = deleteBtn.attr("href");
    console.log("url" + url);

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log("error")
        },
        success: function (data, status) {
            location.reload();
            console.log("delete success")
            if (data) {
                deleteBtn.closest("article").remove();
            } else {
                alert("작성자만 삭제 가능합니다.");
            }
        }
    });
}

String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

var isEmpty = function (val) {
    return val === "" || val === null || val === undefined;
}

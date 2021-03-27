$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
    console.log("click me");
    e.preventDefault();

    var queryString = $(".answer-write").serialize();
    console.log("query : " + queryString);

    var url = $(".answer-write").attr("action");

    console.log("url : " + url);
    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess
    });
}

function onError() {
    alert("로그인이 필요합니다.");
}

function onSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.author.name, data.formattedCreatedDate, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").prepend(template);
    $("textarea[name=contents]").val("");
}

$(document).on('click', '.link-delete-answer', deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();
    var deleteButton = $(this);
    var url = deleteButton.attr("href");
    console.log("url : " + url)

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log("error")
        },
        success: function (data, status) {
            console.log("success")
            if (data) {
                deleteButton.closest("article").remove();
            } else {
                alert("작성자 본인만 삭제 가능합니다.");
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

function checkEmptyFields() {
    let data = document.question.getElementsByTagName("input");

    for (let i = 0; i < data.length; i++) {
        if (data[i].value.trim() === "") {
            let name = data[i].getAttribute("name");
            alert(name + "이(가) 비어있습니다.")
            return;
        }
    }

    document.question.submit();
}

function checkQuestionFields() {
    let title = document.question.querySelector("#title");
    if (title.value.trim() === "") {
        alert(title.getAttribute("name") + "이(가) 비어있습니다.")
        return;
    }

    let contents = document.question.querySelector("#contents");
    if (contents.value.trim() === "") {
        alert(contents.getAttribute("name") + "이(가) 비어있습니다.")
        return;
    }

    document.question.submit();
}


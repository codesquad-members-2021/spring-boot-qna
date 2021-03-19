$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log("click!!");

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

    function onError() {
        alert("로그인이 필요합니다.");
        $(".answer-write textarea").val("");
    }

    function onSuccess(data, status) {
        console.log(data);
        var answerTemplate = $("#answerTemplate").html();
        var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
        $(".qna-comment-slipp-articles").append(template);

        $(".answer-write textarea").val("");

        var count = "<strong>" + data.question.countOfAnswer+ "</strong>개의 의견";
        document.getElementById("qna-comment-count").innerHTML = count;

    }
}

$(".qna-comment-slipp-articles").on("click", "a.link-delete-article", deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();

    var deleteBtn = $(this);
    var url = deleteBtn.attr("href");
    console.log("url : " + url);

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log("error");
        },
        success: function (data, status) {
            console.log("success");
            deleteBtn.closest("article").remove();

            var count = "<strong>" + (data.question.countOfAnswer)+ "</strong>개의 의견";
            document.getElementById("qna-comment-count").innerHTML = count;
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



$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log("click!!");

    const queryString = $(".answer-write").serialize();
    console.log("query : " + queryString);

    const url = $(".answer-write").attr("action");
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
        const answerTemplate = $("#answerTemplate").html();
        const template = answerTemplate.format(data.writer.userId, data.formattedCreateDateTime, data.contents, data.question.id, data.id);
        $(".qna-comment-slipp-articles").append(template);
        $(".answer-write textarea").val("");

        const count = "<strong>" + data.question.countOfAnswer+ "</strong>개의 의견";
        document.getElementById("qna-comment-count").innerHTML = count;
    }
}

$(".qna-comment-slipp-articles").on("click", "a.link-delete-article", deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();

    const deleteBtn = $(this);
    const url = deleteBtn.attr("href");
    console.log("url : " + url);

    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: onError,
        success: onSuccess
    });

    function onError() {
        console.log("error");
        alert("로그인이 필요합니다.");
    }

    function onSuccess(data, status) {
        console.log("success");
        deleteBtn.closest("article").remove();

        const count = "<strong>" + (data.question.countOfAnswer) + "</strong>개의 의견";
        document.getElementById("qna-comment-count").innerHTML = count;
    }
}

String.prototype.format = function () {
    const args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};



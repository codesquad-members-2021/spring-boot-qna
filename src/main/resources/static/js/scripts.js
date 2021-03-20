String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

$(".answer-write button[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();

    const answerWrite = $(".answer-write");
    const queryString = answerWrite.serialize();
    const url = answerWrite.attr("action");

    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess
    })
}

function onError() {
    console.error("error occurred")
}

function onSuccess(data, status) {
    console.log(data);

    const answer = $("#answerTemplate").html()
        .format(
            data.writer.userId,
            data.createDateTime,
            data.comment,
            data.question.id,
            data.id
        );

    $(".answer-write").before(answer);
    $(".answer-write textarea").val("");
}

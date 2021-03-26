String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

$(document).ready(() => { // Powered By Pyro
    let currHref = location.pathname
    $(".sub-nav-li").each((index, item) => {
        let hrefOfItem = $(item).children().first().attr("href");
        if (currHref === hrefOfItem) {
            $(item).addClass("active");
        }
    })

})

function onSubmitAnswer() {
    let formElement = $(".submit-write");
    let queryString = formElement.serialize(); //form data들을 자동으로 묶어준다.
    let url = formElement.attr("action");
    console.log("query : " + queryString);
    console.log("url : " + url);
    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        success: onSuccess,
        error: onFailed
    });
}

function onSuccess(data, status) {
    console.log("success");
    console.log(data);
    console.log(status);

    let answerTemplate = $("#answerTemplate").html();
    let template = answerTemplate.format(
        data.writer.name,
        data.createdDateTime,
        data.contents,
        data.questionId,
        data.id
    );
    $('.qna-comment-slipp-articles').append(template);
    $('#answerContents').val('');
}

function onFailed(data, status) {
    console.log("failed");
    console.log(data);
    console.log(status);
}


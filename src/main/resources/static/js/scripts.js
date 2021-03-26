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
    });
    $(".delete-answer-form button[type='submit']").click(deleteAnswer);
})

function deleteAnswer(event) {
    event.preventDefault();
    let deleteForm = $(this).parents("form");
    let url = deleteForm.attr("action");
    console.log(deleteForm)
    console.log(url)
    $.ajax({
        type: 'delete',
        url: url,
        success: (data, status) => {
            console.log("on delete success");
            let deleteId = data.message;
            console.log(deleteId);
            $(this).closest("article").remove();
        },
        error: () => {
            alert("답변 삭제에 실패했습니다!");
        }

    });
}

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
    alert("답변 생성에 실패했습니다!");
}


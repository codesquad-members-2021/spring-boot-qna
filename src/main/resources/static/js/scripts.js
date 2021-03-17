$(".submit-write input[type = submit]").click(addAnswer);

function addAnswer(event) {
    event.preventDefault();
    console.log("click ?");

    const queryString = $(".submit-write").serialize();
    console.log("query :" + queryString);

    const url = $(".submit-write").attr("action");
    console.log("url : " + url)

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : onError,
        success : onSuccess
    });
}

function onError() {

}

function onSuccess(data, status) {
    console.log(data);
    const answerTemplate = $("#answerTemplate").html();
    console.log(data.author.userId, data.createDateTime, data.body, data.post.postId, data.id);
    const template = answerTemplate.format(data.author.userId, data.createDateTime, data.body, data.post.postId, data.id);
    $(".qna-comment-slipp-articles").prepend(template);
    $("textarea[name=body]").val("");
}

String.prototype.format = function() {
    const args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

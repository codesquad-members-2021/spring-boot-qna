$(".submit-write input[type = submit]").on("click", addAnswer);
$(document).on("click", ".link-delete-article", deleteAnswer);

function addAnswer(event) {
    event.preventDefault();

    const queryString = $(".submit-write").serialize();

    const url = $(".submit-write").attr("action");

    $.ajax({
        type : 'post',
        url : url,
        data : queryString,
        dataType : 'json',
        error : function () {
            alert("댓글 추가 중 오류발생. 다시 시도해 주십시오.")
        },
        success : onSuccess
    });
}

function deleteAnswer(event) {

    event.preventDefault();

    const deleteAnchor = $(this);
    const url = deleteAnchor.attr("href");

    $.ajax({
        type : 'delete',
        url : url,
        dataType: 'json',
        error : function () {
            alert("댓글 삭제중 오류 발생")
        },
        success : function (data, status) {
            if(data) {
                deleteAnchor.closest("article").remove();
            } else {
                alert("댓글 삭제에 실패하셨습니다.");
            }
        }
    });
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


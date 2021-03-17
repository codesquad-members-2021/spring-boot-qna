$('.submit-write button[type=submit]').on('click', addAnswer);
$('.qna-comment-slipp-articles').on('click', '.delete-answer-form button[type=submit]', deleteAnswer);

function addAnswer(e) {
  e.preventDefault();
  let queryString = $('.submit-write').serialize();
  let url = $('.submit-write').attr('action');
  $.ajax({
    type: 'post',
    url : url,
    data : queryString,
    error : onError,
    success: function (data, status) {
      let answerTemplate = $('#answerTemplate').html();
      let template = answerTemplate.format(
          data.id,
          data.questionId,
          data.writer.id,
          data.comment,
          data.time,
          data.writer.name
      );
      $('.qna-comment-slipp-articles').append(template);
      let count = $('.qna-comment-count').children('strong');
      count.text(Number(count.text()) + 1);
      $('textarea[name=comment]').val('');
    },
  });
}

function deleteAnswer(e) {
  e.preventDefault();
  let deleteBtn = $(this);
  let url = $(this).parent().attr('action');

  $.ajax({
    type: 'delete',
    url: url,
    error: onError,
    success: function () {
      deleteBtn.closest('article').remove();
      let count = $('.qna-comment-count').children('strong');
      count.text(Number(count.text()) - 1);
    },
  });
}

function onError(xhr, status) {
  if (xhr.status === 401) {
    window.location.replace('/users/login');
  }
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

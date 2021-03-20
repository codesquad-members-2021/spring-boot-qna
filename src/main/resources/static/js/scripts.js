String.prototype.format = function() {
  let args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

const redirectUrl = {
  LOGINPAGE : "http://localhost:8080/user/login"
}

const errorMessage = {
  NEEDLOGIN : "로그인이 필요한 서비스입니다."
}

const request = {
  post : function post(url, body) {
    return fetch(url, {
      method: 'post',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      body: body
    })
  }
};

function processResponse(errorMessage, url) {
  return response => {
    if (response.status === 200) {
      return response.json();
    }
    alert(errorMessage);
    window.location.href = url;
  }
}

function addAnswer(e) {
  e.preventDefault();
  let url = document.querySelector('form.answer-write').action;
  let body = 'contents=' + document.querySelector('form.answer-write textarea').value;
  request.post(url, body)
      .then(processResponse(errorMessage.NEEDLOGIN, redirectUrl.LOGINPAGE))
      .then(answer => {
        let answerTemplate = document.querySelector('#answerTemplate').innerHTML;
        let template = answerTemplate.format(answer.writer.userId, answer.date, answer.contents);
        document.querySelector('.qna-comment-slipp-articles').insertAdjacentHTML('beforebegin', template);
        document.querySelector('form.answer-write textarea').value = '';
      })
}

let answerWrite = document.querySelector('form.answer-write button[type=submit]');
if (answerWrite !== null) {
  answerWrite.addEventListener('click', e => addAnswer(e));
}

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
  NEEDLOGIN : "로그인이 필요한 서비스입니다.",
  ILLEGALUSER : "접근권한이 없는 유저입니다."
}

const request = {
  post : (url, body) => {
    return fetch(url, {
      method : 'post',
      headers : {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      body : body
    })
  },
  delete : (url) => {
    return fetch(url, {
      method : 'delete',
      headers : {
        'Content-Type': 'application/json'
      }
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

function updateAnswerCount() {
  let linkDeleteArticle = document.querySelectorAll('a.link-delete-article');
  let qnaCommentCount = document.querySelector(".qna-comment-count strong");
  qnaCommentCount.textContent = linkDeleteArticle.length;
}

function addAnswerEvent() {
  let answerWrite = document.querySelector('form.answer-write button[type=submit]');
  if (answerWrite !== null) {
    answerWrite.addEventListener('click', addAnswer);
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
        let template = answerTemplate.format(answer.writer.userId, answer.date, answer.contents, answer.id);
        document.querySelector('.qna-comment-slipp-articles').insertAdjacentHTML('afterbegin', template);
        document.querySelector('form.answer-write textarea').value = '';
        addDeleteEventToAllAnswer();
        updateAnswerCount();
      })
}

function addDeleteEventToAllAnswer() {
  let linkDeleteArticle = document.querySelectorAll('a.link-delete-article');
  linkDeleteArticle.forEach(targetQuery => {
    targetQuery.addEventListener('click', deleteAnswer);
  });
}

function deleteAnswer(e) {
  e.preventDefault();
  let url = e.target.href;
  request.delete(url)
      .then(processResponse(errorMessage.ILLEGALUSER, redirectUrl.LOGINPAGE))
      .then(() => {
        let linkDeleteArticle = document.querySelectorAll('a.link-delete-article');
        linkDeleteArticle.forEach(targetQuery => {
          if (targetQuery.href === url) {
            targetQuery.closest('article').remove();
            updateAnswerCount();
          }
        })
      })
}

function init() {
  addAnswerEvent();
  addDeleteEventToAllAnswer();
}

init();

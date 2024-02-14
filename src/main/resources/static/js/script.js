function getToken() {
    let auth = Cookies.get('Authorization');

    if (auth === undefined) {
        return '';
    }

    return auth;
}
function submitPost(id) {
    let title = $('#createTitle').val();
    let content = $('#createContent').val();

    // 전송할 데이터 객체 생성
    let data = {
        'title': title,
        'content': content,
    };
    // AJAX를 사용하여 서버에 POST 요청 보내기
    $.ajax({
        type: 'POST',
        url: `/api/posts/create`,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function () {
            window.location.reload();
        },
        error: function () {
            window.location.reload();
        }
    });
}
function logout() {
    // 토큰 삭제
    Cookies.remove('Authorization', {path: '/'});
    window.location.reload();
}
function showPost() {
    $(`#create-box`).show();
}

function getPosts() {
    // AJAX를 사용하여 서버에 POST 요청 보내기
    $.ajax({
        type: 'GET',
        url: '/api/posts',
        contentType: 'application/json',
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                addHtml(response[i].id, response[i].username, response[i].title);
            }
        },
        error: function (xhr, status, error) {
            var errorMessage = xhr.responseJSON.message;
            alert(errorMessage);
        }
    });
}

function getmyPosts() {
    // AJAX를 사용하여 서버에 POST 요청 보내기
    $.ajax({
        type: 'GET',
        url: '/api/posts/myPosts',
        contentType: 'application/json',
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                addHtml(response[i].id, response[i].username, response[i].title);
            }
        },
        error: function (xhr, status, error) {
            var errorMessage = xhr.responseJSON.message;
            alert(errorMessage);
        }
    });
}

function addHtml(id, username, title) {
    let tempHtml =
        `<div class="card" onclick="showDetails('${id}')"/>
            <p>${username}</p>
            <h1>${title}</h1>
        </div>`;
    $('#cards-box').append(tempHtml);
}

function showDetails(id) {
    $('#detail-box').empty();
    $.ajax({
        type: 'GET',
        url: `/api/posts/postId/${id}`,
        contentType: 'application/json',
        success: function (response) {
            let id = response['id'];
            let modifiedDate = response['modifiedDate'];
            let title = response['title'];
            let username = response['username']
            let content = response['content'];
            addHtmlDetails(id, modifiedDate, title, username, content);
        },
        error: function (xhr, status, error) {
            var errorMessage = xhr.responseJSON.message;
            alert(errorMessage);
        }
    });
}

function addHtmlDetails(id, modifiedDate, title, username, content) {
    let tempHtml =
        `<div class="card">
            <p>${username}</p>
            <p>${modifiedDate}</p>
            <h1>${title}</h1>
            <h2>${content}</h2>
            <button id="${id}-edit" onclick="editPost()">수정</button>
            <div id="showForEdit">

                <label for="editTitle">제목:</label> <!-- Label for password input -->
                <input type="text" id="editTitle" name="editTitle" required><br><br> <!-- Password input -->
                
                <label for="editContent">내용:</label> <!-- Label for password input -->
                <input type="text" id="editContent" name="editContent" required><br><br> <!-- Password input -->
                
                <button type="submit" onclick="submitEditPost('${id}')">수정완료</button> <!-- Submit button for form -->
            </div>
            <button id="${id}-delete" onclick="deletePost('${id}')">삭제</button>
            <div id="comments-box">
                <label for="createComment">댓글달기:</label> <!-- Label for password input -->
                <input type="text" id="createComment" name="createComment" required><br><br> <!-- Password input -->
                
                <button type="submit" onclick="createCommentInPost('${id}')">댓글 작성 완료</button> <!-- Submit button for form -->
            </div>
        </div>`;
    $('#detail-box').append(tempHtml);
    $(`#showForEdit`).hide();
    getCommentsByPostId(id);
}
function createCommentInPost(id) {
    let commentContent = $('#createComment').val();

    // 전송할 데이터 객체 생성
    let data = {
        'postId': id,
        'commentContent': commentContent,
    };

    // AJAX를 사용하여 서버에 POST 요청 보내기
    $.ajax({
        type: 'POST',
        url: `/api/comments/create`,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function () {
            window.location.reload();
        },
        error: function () {
            window.location.reload();
        }
    });
}
function getCommentsByPostId(postid) {
    $.ajax({
        type: 'GET',
        url: `/api/comments?postId=${postid}`,
        contentType: 'application/json',
        success: function (response) {
            for (let i = 0; i < response.length; i++) {
                let id = response[i]['id'];
                let modifiedDate = response[i]['modifiedAt'];
                let username = response[i]['username']
                let commentContent = response[i]['commentContent'];
                addHtmlComment(postid, id, modifiedDate, username, commentContent);
            }
        },
        error: function (xhr, status, error) {
            var errorMessage = xhr.responseJSON.message;
            alert(errorMessage);
        }
    });
}
function addHtmlComment(postid, id, modifiedAt, username, commentContent) {
    let tempHtml =
        `<div class="comment">
            <p>${username}</p>
            <p>${modifiedAt}</p>
            <h2>${commentContent}</h2>
            <button id="${id}-edit" onclick="editComment('${id}')">수정</button>
            <div id="${id}-showForEditComment">
                <label for="${id}-editCommentValue">내용:</label> <!-- Label for password input -->
                <input type="text" id="${id}-editCommentValue" name="${id}-editCommentValue" required><br><br> <!-- Password input -->
                <button type="submit" onclick="submitEditComment('${id}', '${postid}')">수정완료</button> <!-- Submit button for form -->
            </div>
            
            <button id="${id}-delete" onclick="deleteComment('${id}')">삭제</button>
        </div>`;
    $('#comments-box').append(tempHtml);
    $(`#${id}-showForEditComment`).hide();
}

function editComment(id) {
    $(`#${id}-showForEditComment`).show();
}
function editPost() {
    $(`#showForEdit`).show();
}

function submitEditComment(id, postid) {
    let commentContent = $(`#${id}-editCommentValue`).val();

    // 전송할 데이터 객체 생성
    let data = {
        'postId': postid,
        'commentContent': commentContent
    };

    // AJAX를 사용하여 서버에 POST 요청 보내기
    $.ajax({
        type: 'PUT',
        url: `/api/comments/${id}`,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function () {
            window.location.reload();
        },
        error: function () {
            window.location.reload();
        }
    });
}

function submitEditPost(id) {
    let title = $('#editTitle').val();
    let content = $('#editContent').val();

    // 전송할 데이터 객체 생성
    let data = {
        'title': title,
        'content': content,
    };

    // AJAX를 사용하여 서버에 POST 요청 보내기
    $.ajax({
        type: 'PUT',
        url: `/api/posts/${id}`,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function () {
            window.location.reload();
        },
        error: function () {
            window.location.reload();
        }
    });
}
function deleteComment(id) {
    // AJAX를 사용하여 서버에 POST 요청 보내기
    $.ajax({
        type: 'DELETE',
        url: `/api/comments/${id}`,
        contentType: 'application/json',
        success: function () {
            window.location.reload();
        },
        error: function () {
            window.location.reload();
        }
    });
}


function deletePost(id) {
    // AJAX를 사용하여 서버에 POST 요청 보내기
    $.ajax({
        type: 'DELETE',
        url: `/api/posts/${id}`,
        contentType: 'application/json',
        success: function () {
            window.location.reload();
        },
        error: function () {
            window.location.reload();
        }
    });
}


function signup() {
    let username = $('#username').val();
    let password = $('#password').val();
    let email = $('#email').val();
    let encodedEmail = $('#encodedEmail').val();
    let info = $('#info').val();

    // 전송할 데이터 객체 생성
    let data = {
        'username': username,
        'password': password,
        'email': email,
        'encodedEmail': encodedEmail,
        'info': info
    };

    // AJAX를 사용하여 서버에 POST 요청 보내기
    $.ajax({
        type: 'POST',
        url: '/api/users/signup',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            alert(response.message);
            window.location.href = 'http://localhost:8080/api/users/login-page';
        },
        error: function (xhr, status, error) {
            var errorMessage = xhr.responseJSON.message;
            alert(errorMessage);
            window.location.reload();

        }
    });
}

function login() {
    let username = $('#username').val();
    let password = $('#password').val();

    // 전송할 데이터 객체 생성
    let data = {
        'username': username,
        'password': password,
    };

    // AJAX를 사용하여 서버에 POST 요청 보내기
    $.ajax({
        type: 'POST',
        url: '/api/users/login',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function () {
            window.location.href = "http://localhost:8080/";
        },
        error: function (xhr, status, error) {
            var errorMessage = xhr.responseJSON.message;
            alert(errorMessage);
            window.location.reload();

        }
    });
}

function sendVerifyEmail() {
    let receiverEmail = $('#email').val();

    // 전송할 데이터 객체 생성
    let data = {
        'receiverEmail': receiverEmail
    };

    // AJAX를 사용하여 서버에 POST 요청 보내기
    $.ajax({
        type: 'POST',
        url: '/api/users/mails',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            alert(response.message);
        },
        error: function (xhr, status, error) {
            var errorMessage = xhr.responseJSON.message;
            alert(errorMessage);
        }
    });
}

function editInfo() {
    $(`#showForEdit`).show();
}

function editPassword() {
    $(`#showForEditPassword`).show();
}

function submitinfo() {
    let editinfo = $('#editinfo').val();

    // 전송할 데이터 객체 생성
    let data = {
        'info': editinfo
    };

    // AJAX를 사용하여 서버에 POST 요청 보내기
    $.ajax({
        type: 'PUT',
        url: '/api/users',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function () {
            window.location.reload();
        },
        error: function (xhr, status, error) {
            alert("수정에러 발생");
        }
    });
}

function submitEditPassword() {
    let prePassword = $('#prePassword').val();
    let postPassword = $('#postPassword').val();

    // 전송할 데이터 객체 생성
    let data = {
        'prePassword': prePassword,
        'postPassword': postPassword
    };

    // AJAX를 사용하여 서버에 POST 요청 보내기
    $.ajax({
        type: 'PATCH',
        url: '/api/users/password',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (response) {
            alert(response.message);
            window.location.reload();
        },
        error: function (xhr, status, error) {
            var errorMessage = xhr.responseJSON.message;
            alert(errorMessage);
        }
    });
}
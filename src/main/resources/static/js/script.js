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
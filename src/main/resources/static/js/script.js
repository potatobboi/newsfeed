function submitForm() {
    let username = $('#username').val();
    let password = $('#password').val();
    let info = $('#info').val();

    // 전송할 데이터 객체 생성
    let data = {
        'username': username,
        'password': password,
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

            if (response.statusCode === 200) {
                window.location.href =  'http://localhost:8080/api/users/login-page';
            } else {
                window.location.reload();
            }
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
            alert(Cookies.get('Authorization'))
            let auth = Cookies.get('Authorization');


            if (auth === undefined) {
                alert("로그인에 실패하였습니다.")
                window.location.href = "http://localhost:8080/api/users/login-page";
            }

            window.location.href = "http://localhost:8080/";
        }
    });
}
$(function () {
    $('#btnlogin').on("click", function () {

        $.ajax({
            type: "post",
            url: "emailCheck",
            data: {
                "userEmail": $('#userEmail').val(),
                "userPw": $('#userPw').val()
            },
            success: function (data) {
                if ($.trim(data) == "YES") {
                    console.log(data)
                    $("#invalidId").css('display', 'none');
                    $("#loginForm").submit();
                } else if ($.trim(data) == "NO") {
                    console.log(data)
                    $('#userEmail').focus();
                    $("#invalidId").css('display', 'block');
                }
            }
        });
    });
});

$(document).ready(function() {
    // ID가 message에서 엔터키를 누를 경우
    $("#openpopup").keydown(function (key) {
        if (key.keyCode == 13) {
			$.ajax({
				type: "post",
				url: "emailCheck",
				data: {
					"userEmail": $('#userEmail').val(),
					"userPw": $('#userPw').val()
				},
				success: function (data) {
					if ($.trim(data) == "YES") {
						console.log(data)
						$("#invalidId").css('display', 'none');
						$("#loginForm").submit();
					} else if ($.trim(data) == "NO") {
						console.log(data)
						$('#userEmail').focus();
						$("#invalidId").css('display', 'block');
					}
				}
			});
        }
    });
});
$(document).ready(function () {
    var signin1 = true;
    var signin2 = true;
    var signin3 = true;
    var signin4 = true;
    var signin5 = true;

    $("#signInEmail, #signInPw, #signInName, #signInPhone").keyup(function () {
        var signInEmail = $("#signInEmail").val();
        var signInPw = $("#signInPw").val();
        var signInName = $("#signInName").val();     
        var signInPhone = $("#signInPhone").val();
        switch (
            !((signInEmail.length >= 5) && (signInPw.length >= 8) && (signInName.length >= 5) && (signInPhone.length >= 9))
        ) {
            case true:
                $('#btnsignin').prop('disabled', true);
                break;
            case false:
                $('#btnsignin').prop('disabled', false);
                break;
        }
    });
                    
    $("#signInEmail").on("change keyup paste", function() {
        var signInEmail = $("#signInEmail").val();
        var RegEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
        var check = document.getElementById("emailCheckResult");
        if (!RegEmail.test(signInEmail)) {
            
            $("#btnEmailCheck").prop('disabled', true);
            
             $("#btnEmailCheck").css('background-color', '#FAFAFA')
            
            $("#btnEmailCheck").css('color', '#000000')
            
        }  else {
            $("#btnEmailCheck").prop('disabled', false);

            $("#btnEmailCheck").css('background-color', '#0095F6')
            
            $("#btnEmailCheck").css('color', '#FFFFFF')
        }
        
    });
    
    $("#signInEmail").on("keyup", function() {
        var check = document.getElementById("emailCheckResult");
        if(check.value=="1") {
            check.value="0";
        } 
    });

    $("#signInEmail, #signInPw, #signInName, #signInPhone").blur(function () {
           var RegEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
        var RegPw = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d$@$!%*#?&]{8,}$/;
        var RegName = /^(?=.*[a-zA-Z가-힣]){1,}.{5,15}$/;
        var RegPhone = /^[0-9]{2,3}[0-9]{3,4}[0-9]{4}$/;
        var signInEmail = $("#signInEmail").val();
        var signInPw = $("#signInPw").val();
        var signInName = $("#signInName").val();
        var signInPhone = $("#signInPhone").val();
        var check = document.getElementById("emailCheckResult");
        
        if (!RegEmail.test(signInEmail)) {
            $("#signInEmail").removeClass("is-valid");

            $("#signInEmail").addClass("is-invalid");

            $("#invalidEmail").css('display', 'block');
                      
            var signin1 = false;
        } else if(check.value=="0") {
            $("#checkEmailYn").css('display', 'block');
            $("#signInEmail").removeClass("is-valid");
            $("#signInEmail").addClass("is-invalid");
            var signin1 = false;
        } else if(check.value=="1") {
            $("#signInEmail").removeClass("is-invalid");

            $("#signInEmail").addClass("is-valid");

            $("#invalidEmail").css('display', 'none');
                                                     
            $("#checkEmailYn").css('display', 'none');
            var signin1 = true;
        }
        
        else {
            $("#signInEmail").removeClass("is-invalid");

            $("#signInEmail").addClass("is-valid");

            $("#invalidEmail").css('display', 'none');
                                                           
            var signin1 = true;
        }
        
        if (!RegPw.test(signInPw)) {
            $("#signInPw").removeClass("is-valid");

            $("#signInPw").addClass("is-invalid");

            $("#invalidPw").css('display', 'block');

            var signin2 = false;
        } else {
            $("#signInPw").removeClass("is-invalid");

            $("#signInPw").addClass("is-valid");

            $("#invalidPw").css('display', 'none');

            var signin2 = true;
        }
        if (!RegName.test(signInName)) {
            $("#signInName").removeClass("is-valid");

            $("#signInName").addClass("is-invalid");

            $("#invalidName").css('display', 'block');

            var signin3 = false;
        } else {
            $("#signInName").removeClass("is-invalid");

            $("#signInName").addClass("is-valid");

            $("#invalidName").css('display', 'none');

            var signin3 = true;
        }
        
        if (!RegPhone.test(signInPhone)) {
            $("#signInPhone").removeClass("is-valid");

            $("#signInPhone").addClass("is-invalid");

            $("#invalidPhone").css('display', 'block');

            var signin4 = false;
        } else {
            $("#signInPhone").removeClass("is-invalid");

            $("#signInPhone").addClass("is-valid");

            $("#invalidPhone").css('display', 'none');

            var signin4 = true;
        }

        $("#btnEmailCheck").on("click", function() {
            var datas = {
                userEmail : $("#signInEmail").val()
            };
            
            $.ajax({
                url : "/register",
                type : "post",
                data : datas,
                
                success : function(data) {
                    if (data.count == 1) {
                        $("#duplicatedEmail").css('display', 'block');
                        $("#signInEmail").removeClass("is-valid");
                        $("#signInEmail").addClass("is-invalid");
                         $('#btnsignin').prop('disabled', true);
                         document.getElementById("emailCheckResult").value="0";
                         var signin5 = false;
                    } 
                    else {
                        $("#duplicatedEmail").css('display', 'none');
                        $("#checkEmailYn").css('display', 'none');
                        $("#signInEmail").removeClass("is-invalid");
                        $("#signInEmail").addClass("is-valid");
                        document.getElementById("emailCheckResult").value="1";						
                        var signin5 = true;
                    }

                },
                error : function() {
                    alert("통신 실패")
                },
            });
        });
      
        $("#btnsignin").on("click", function () {
            if (!signin1 || !signin2 || !signin3 || !signin4 || !signin5) {
                console.log(signin1);
                console.log(signin2);
                console.log(signin3);
                console.log(signin4);
                console.log(signin5);
                return false;
            } else if(document.getElementById("emailCheckResult").value=="0") {
                $("#checkEmailYn").css('display', 'block');
                $("#signInEmail").removeClass("is-valid");
                $("#signInEmail").addClass("is-invalid");
                return false;
            } 
            else {
                console.log(signin1);
                $("#signinform").submit();
                return true;
            }
        });
    });
});
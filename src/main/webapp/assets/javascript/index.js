// Example starter JavaScript for disabling form submissions if there are invalid fields

window.onload = function() {

};

(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('click', function validityCheck (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
})();

let loginBtn = document.getElementById('signIn');
loginBtn.addEventListener("click", login, true );

function isEmail(email) {
    return /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email);
};

function login(e) {
    // e.preventDefault();
    let uName = document.getElementById('username').value;
    let pass = document.getElementById('password').value;

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            console.log(xhttp.response)
            if(!xhttp.responseText){
                alert("Invalid Email Address or Password")

            } else{
                // window.location='home.html';
            }

        }
    };
    xhttp.open("POST", "/ers/login", true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhttp.send([
        encodeURIComponent("username") + '=' + encodeURIComponent(uName),
        encodeURIComponent("password") + '=' + encodeURIComponent(pass)
    ].join('&'));
}







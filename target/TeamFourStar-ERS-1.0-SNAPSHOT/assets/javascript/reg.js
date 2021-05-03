// Example starter JavaScript for disabling form submissions if there are invalid fields

window.onload = function () {

};

(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('click', function validityCheck(event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
})();

let loginBtn = document.getElementById('regSubmit');
loginBtn.addEventListener("click", register, true);

function isEmail(email) {
    return /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email);
};

let regBtn = document.getElementById('regSubmit');
regBtn.addEventListener("click", register, true);

function register(e) {
    e.preventDefault();
    let uName = document.getElementById('username').value;
    let pass = document.getElementById('password').value;
    let empID = document.getElementById('empId').value;

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4) {
            console.log(xhttp.response)
            if (this.status == 201) {
                console.log(typeof (xhttp.response))


                window.location = '/';

            } else if (this.status==401){

                alert("You are Not an Employee with us. We are unable to process financial reimbursement for you!")

            }else if (this.status==406){
                alert("Invalid Employee ID")
            }

        }

    }

    xhttp.open("POST", "/ers/registration", false);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhttp.send([
        encodeURIComponent("username") + '=' + encodeURIComponent(uName),
        encodeURIComponent("password") + '=' + encodeURIComponent(pass),
        encodeURIComponent("employeeid") + '=' + encodeURIComponent(empID)
    ].join('&'));
};








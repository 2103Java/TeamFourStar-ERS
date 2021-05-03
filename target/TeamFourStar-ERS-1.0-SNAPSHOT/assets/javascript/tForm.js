window.onload = cUser;
let user;

function cUser() {
    document.getElementById("ticketSubmit").addEventListener('click', postTicket);
    document.getElementById("logout").addEventListener('click',logout);
    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // console.log(xhttp.response)
            if (!xhttp.responseText) {
                alert("You are not currently logged in!")
                (window.location='index.html').reload();
            } else {

                user = JSON.parse(xhttp.responseText);

                console.log(user);
            }
        }
    }
    xhttp.open("GET", "/ers/current", false);
    xhttp.send();

};

function postTicket(e) {
    e.preventDefault();
    let amount = document.getElementById('amount').value;
    let description = document.getElementById('tickDesc').value;
    let uID = user.userID;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(xhttp.response)
            alert("Your request for reimbursement has been opened. Please check your HomePage for updates.")
        }


    };
    xhttp.open("POST", "/ers/ticket_form", true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhttp.send([
        encodeURIComponent("userid") + '=' + encodeURIComponent(uID),
        encodeURIComponent("amount") + '=' + encodeURIComponent(amount),
        encodeURIComponent("description") + '=' + encodeURIComponent(description)
    ].join('&'));
}
function logout() {
    console.log("logging out")
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            window.location="/"
        }


    }
    xhttp.open("GET", "/ers/logout", true);
    xhttp.send();
}
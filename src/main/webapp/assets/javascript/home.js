// Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
})()

function isEmail(email) {
    return /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email);
}

window.onload = renderTable;

let user;

function renderTable() {
    cUser();
    document.getElementById("logout").addEventListener('click',logout);
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(xhttp.response)
            if (!xhttp.responseText) {


            } else {

                resultList = JSON.parse(xhttp.responseText);

                //render the tables;

                addRows(resultList);

            }

        }
    };
    xhttp.open("GET", "/ers/tickets", true);
    xhttp.send();
}

function addRows(result) {
    let table = document.getElementById("ticketTable");
    let data = result;
    if (!data) {
        return;
    } else {
        // this avoids duplicates
        table.innerHTML = "";
        Array.prototype.forEach.call(data, ticket => {
            let tID = ticket.ticketNum;
            let tAmount = ticket['amount'];
            let tStatus = "";
            let tApproval = "";
            if (ticket.openStatus == false && ticket.approvalStatus == false) {
                tApproval = "Denied";
            } else if (ticket.openStatus == false && ticket.approvalStatus == true) {
                tApproval = "Approved";
            }

            if (ticket.openStatus) {
                tStatus = "Open"
                tApproval = "Pending"
            } else {
                tStatus = "Closed"
            }


            if (ticket.approvalStatus == null) {

            }


            table.innerHTML += `<tr id="${tID}">
                 <th scope="row">${tID}</th>
                  <td>$${tAmount}</td>
                  <td>${tStatus}</td>
                   <td>${tApproval}</td>
                 </tr>`

        });
        let trList = table.getElementsByTagName("tr");
        for (let index = 0; index < trList.length; index++) {
            trList[index].addEventListener("click", function () {
                console.log(trList[index].getAttribute("id"));
                getTicket(trList[index].getAttribute("id"))
            });
        }

    }


}

function cUser() {
    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            // console.log(xhttp.response)
            if (!xhttp.responseText) {
                alert("You are not currently logged in!")

            } else {

                user = JSON.parse(xhttp.responseText);

                console.log(user);
            }
        }
    }
    xhttp.open("GET", "/ers/current", false);
    xhttp.send();

}

function toggleDetails(ticket) {


    if (!ticket) {
        console.log("no ticket")
        return;
    } else {
        let ticketName = document.getElementById("ticketID");
        ticketName.innerText = ticket.ticketNum;
        let ticketDesc = document.getElementById('ticketDesc');
        ticketDesc.innerText = ticket.description;

        if (user.isAdmin) {
            let footer = document.getElementById('adminBro');
            footer.innerHTML = `<div class="modal-footer">
        <button type="button" id="approveBtn" class="btn btn-info" onClick="approveRequest(${ticket.ticketNum})"data-bs-dismiss="modal">Approve</button>
             <button type="button" class="btn btn-danger" id="denyBtn" onClick="denyRequest(${ticket.ticketNum})"data-bs-dismiss="modal">Deny</button>
           <button type="button" class="btn btn-secondary" id="pendingBtn" data-bs-dismiss="modal">Close</button>
          </div>`
        }

        var myModal = new bootstrap.Modal(document.getElementById('ticketModal'));
        myModal.show();
    }
}

function getTicket(id) {

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(xhttp.response)
            if (!xhttp.responseText) {


            } else {

                let ticket = JSON.parse(xhttp.responseText);
                toggleDetails(ticket);


            }
        }
    }
    xhttp.open("GET", "/ers/ticket?ticketnumber=" + id, true);
    xhttp.send();
}

function denyRequest(num) {
    console.log("denied!")
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(xhttp.response)
            if (!xhttp.responseText) {
                alert("Ticket was Denied");


            } else {


            }
        }
    }
    xhttp.open("PUT", "/ers/admin?ticketnumber=" + num + "&action=deny", true);
    xhttp.send();

}

function approveRequest(num) {
    console.log("approved!")
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(xhttp.response)
            location.reload();
            renderTable();
        }
    }
    xhttp.open("PUT", "/ers/admin?ticketnumber=" + num + "&action=approve", true);
    xhttp.send();

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

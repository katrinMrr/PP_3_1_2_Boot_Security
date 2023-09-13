$(async () => {
    await usersTopLeft();
    await usersTable();
})

async function usersTopLeft() {
    const requestURL = "/admin/users/current";
    let prev = "";
    await fetch(requestURL).then(rs => rs.json()).then(u => {
        prev = u.name + " with role " + u.rolesSet.map(role => role.nameRole);
        document.getElementById("text-prev").textContent = prev;
        console.log(u);
        document.querySelector("#userTable tbody").innerHTML = ` <tr>     
                                            <td><p >${u.id}</p></td>
                                            <td><p >${u.name}</p></td>
                                            <td><p >${u.birthday}</p></td>
                                            <td><p >${u.gender}</p></td>
                                            <td><p >${u.username}</p></td>
                                        <td><p >${u.rolesSet.map(r => r.nameRole)}</p></td>
                                       </tr>`;
    })
}


async function usersTable() {
    const requestURL = "/admin/users";
    await fetch(requestURL).then(rs => rs.json()).then(u => {
        u.forEach(function (u) {
            let users = document.createElement("tr");
            users.innerHTML = `
                   <td>${u.id}</td>
                   <td>${u.name}</td>
                   <td>${u.birthday}</td>
                   <td>${u.gender}</td>
                   <td>${u.username}</td>
                   <td>${u.rolesSet.map(r => r.nameRole.substring(5))}</td>
                   <td><button type="button" class="btn btn-info text-white"
                                                            data-bs-toggle="modal"
                                                            href="#editModal"
                                                     onclick="openModal(${u.id}, 'editModal')">
                                                        Edit
                                                    </button></td>
                   <td>           <button type="button" class="btn btn-danger text-white"
                                                            data-bs-toggle="modal"
                                                            href="#deleteModal" onclick="openModal(${u.id}, 'deleteModal')">
                                                        Delete
                                                    </button></td>`
            document.querySelector("#usersTable tbody").append(users);
        })
    })
}

async function openModal(id, modalName) {
    let modal = document.getElementById(modalName)
    const requestURL = "/admin/users/" + id;
    await fetch(requestURL).then(rs => rs.json()).then(u => {
        console.log(u);
        modal.querySelector('input[name ="id"]').value = u.id;
        modal.querySelector('input[name ="name"]').value = u.name;
        modal.querySelector('input[name ="birthday"]').value = u.birthday;
        if (u.gender === "Man") {
            modal.querySelectorAll('input[name ="gender"]')[0].checked = true;
        } else if (u.gender === "Woman") {
            modal.querySelectorAll('input[name ="gender"]')[1].checked = true;
        }
        modal.querySelector('input[name ="username"]').value = u.username;
        if (u.rolesSet.length === 1) {
            modal.querySelector('select[name ="role"]').value = "USER"
        } else {
            modal.querySelector('select[name ="role"]').value = "ADMIN"
        }
        // if (modalName === "editModal") {
        //     edit(u.id);
        // } else {
        //     deleteUser(u.id);
        // }
    })
}

async function edit(id) {
    const edit = "admin/users/edit/" + id
    await fetch(edit, {
        method: 'PATCH',
        body: JSON.stringify()
    }).then((response) => response.json())
        .then((data) => console.log(data))
        .catch((error) => console.error(error));
}


async function deleteUser(id) {
    const deleteU = "admin/users/delete/" + id;
    await fetch(deleteU, {
        method: 'DELETE'
    })
}


async function newUser() {

    let newUserForm = document.getElementById("nav-newUser")
    const requestURL = "admin/users/new";
    let role = newUserForm.querySelector('select[name ="role"]')[newUserForm
        .querySelector('select[name ="role"]').selectedIndex].value;
    let newUser = {
        name: newUserForm.querySelector('input[name ="name"]').value,
        birthday: newUserForm.querySelector('input[name ="birthday"]').value,
        gender: newUserForm.querySelectorAll('input[name ="gender"]:checked')[0].value,
        username: newUserForm.querySelector('input[name ="username"]').value,
        isAdmin: false
    }
    if (role === "ADMIN") {
        newUser.isAdmin = true;
    }
    console.log(newUser);
    await fetch(requestURL, {
        method: 'PUT',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(newUser)
    })
}

// function validateForm(form) {
//     let fail = false;
//     const name = form.name.value;
//     const email = form.email.value;
//     const password = form.password.value;
//     const role = form.role.value;
//
//     if (name === "") {
//         fail = "Заполните поле name";
//     } else if (email === "") {
//         fail = "Заполните поле email"
//     } else if (password === "") {
//         fail = "Заполните поле password"
//     } else if (role === "") {
//         fail = "Заполните поле role"
//     }
//     if (fail) {
//         alert(fail)
//     } else window.location = "http://admin/users/newUserForm";
//
// }


$(async () => {
    await userTopLeft();
})

async function userTopLeft() {
    const requestURL = "/user/profile";
    let prev = "";
    await fetch(requestURL).then(rs => rs.json()).then(u => {
        // console.log(u);
        prev = u.name + " with role " + u.rolesSet.map(role => role.nameRole);
        document.getElementById("prevUser").textContent = prev;

        document.querySelector("#tableUser tbody").innerHTML = ` <tr>     
                                            <td><p >${u.id}</p></td>
                                            <td><p >${u.name}</p></td>
                                            <td><p >${u.birthday}</p></td>
                                            <td><p >${u.gender}</p></td>
                                            <td><p >${u.username}</p></td>
                                        <td><p >${u.rolesSet.map(r => r.nameRole)}</p></td>
                                       </tr>`;
    })
}









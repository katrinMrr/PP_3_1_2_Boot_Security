$(async () => {
    await userTopLeft();
    await usersTable();
})

const editForm = document.getElementById('editModal')
editForm.addEventListener('submit', editUser)

const deleteForm = document.getElementById('deleteUser')
deleteForm.addEventListener('submit', deleteUser)

const applicantForm = document.getElementById('newUserForm')
applicantForm.addEventListener('submit', newUser)

let currentId;

async function userTopLeft() {
    const requestURL = "/user/profile";
    let prev = "";
    await fetch(requestURL).then(rs => rs.json()).then(u => {
        console.log(u);
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

async function usersTable() {
    const requestURL = "/admin/users";
    document.querySelector("#usersTable tbody").innerHTML = ``;
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
    })
    currentId = id;
}

async function editUser(event) {
    event.preventDefault();
    const edit = "admin/users/edit/";
    let editModal = document.getElementById("editModal");
    let role = editModal.querySelector('select[name ="role"]')[editModal
        .querySelector('select[name ="role"]').selectedIndex].value;
    let editUser = {
        id: editModal.querySelector('input[name ="id"]').value,
        name: editModal.querySelector('input[name ="name"]:required').value,
        birthday: editModal.querySelector('input[name ="birthday"]').value,
        username: editModal.querySelector('input[name ="username"]:required').value,
        password: editModal.querySelector('input[name ="password"]').value,
    }
    if (role === "ADMIN") {
        editUser.isAdmin = Boolean(true);
    }
    let userGender = editModal.querySelectorAll('input[name ="gender"]:checked');
    if (userGender !== null && userGender !== undefined && userGender.length !== 0) {
        editUser.gender = userGender[0].value;
    }
    console.log(editUser);
    await fetch(edit, {
        method: 'PATCH',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(editUser)
    })
    document.getElementById("closeButton2").click();
    await usersTable();
}

async function deleteUser(event) {
    event.preventDefault();
    const deleteU = "admin/users/delete/" + currentId;
    await fetch(deleteU, {
        method: 'DELETE'
    })
    document.getElementById("closeButton").click();
    await usersTable();
}

async function newUser(event) {
    event.preventDefault();
    let newUserForm = document.getElementById("newUserForm")
    console.log(newUserForm);
    const requestURL = "admin/users/new";
    let role = newUserForm.querySelector('select[name ="role"]')[newUserForm
        .querySelector('select[name ="role"]').selectedIndex].value;

    let newUser = {
        name: newUserForm.querySelector('input[name ="name"]:required').value,
        birthday: newUserForm.querySelector('input[name ="birthday"]').value,
        username: newUserForm.querySelector('input[name ="username"]:required').value,
        password: newUserForm.querySelector('input[name ="password"]:required').value,
    }
    if (role === "ADMIN") {
        newUser.isAdmin = Boolean(true);
    }
    let userGender = newUserForm.querySelectorAll('input[name ="gender"]:checked');
    if (userGender !== null && userGender !== undefined && userGender.length !== 0) {
        newUser.gender = userGender[0].value;
    }
    if (newUser.birthday === "") {
        newUser.birthday = new Date()
    }
    console.log(newUser);
    await fetch(requestURL, {
        method: 'PUT',
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(newUser)
    })
    document.getElementById("nav-table-tab").click();
    await usersTable();
    event.target.reset();
}
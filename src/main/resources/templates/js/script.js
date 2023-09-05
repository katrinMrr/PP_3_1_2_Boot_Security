alert(1);
// const requestURL = "http://localhost8080/admin/users"
//
// function sendRequest(method, url, body = null) {
//     return fetch(url).then(response => {
//         return response.json()
//     });
// }
//
// sendRequest("GET", requestURL)
//     .then(data => console.log(data))
//     .catch(err => console.log(err))
//
//
// const headers = {}
//
// function sendRequestPost(method, url, body = null) {
//     return fetch(url, {
//         method: method, body: JSON.stringify(body),
//         headers: headers
//     })
//         .then(response => {
//             if (response.ok) {
//                 return response.json()
//             }
//             return response.json().then()
//         });
// }
//
// sendRequestPost("POST", requestURL)
//     .then(data => console.log(data))
//     .catch(err => console.log(err))
//
//
// //--------------------------------------------------------------
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
//     } else window.location = "http://admin/users";
//
// }

//--------------------------------------------------------------
let inputLogin = document.getElementById("login");
console.log(inputLogin);



function input() {
    // let login_ok = false;
    // let username = "";
    // let password = "";
    // username = prompt("login", "");
    // username = username.toLowerCase();
    // password = prompt("password", "");
    // password = password.toLowerCase();
    // if (username === "login" && password === "password") {
    //     login_ok = true;
    //     window.location = "http://localhost:8080/admin/users";
    // }
    // if (login_ok === false) alert("Неверный логин или пароль!");
    //
    // console.log(password);
    // console.log(username);
    let inputLogin = document.getElementById("login");
    console.log(inputLogin);
}

//--------------------------------------------------------------
// const nameInput = form.querySelector('.validationCustom01');
// const emailInput = form.querySelector('.validationCustomUsername');
// const roleInput = form.querySelector('.validationCustom05');

// Получаем значения полей формы
// const name = nameInput.value;
// const login = emailInput.value;
// const role = roleInput.value;

// Проверяем, что поля заполнены
// if (!login || !name || !role) {
//     alert('Пожалуйста, заполните все поля');
//     return window.location = "http:/localhost8080/admin/users/newUserForm";
// } else {
//     form.submit();
//     window.location = "http://localhost8080/admin/users"
// }

// Если всё в порядке, отправляем форму

//--------------------------------------------------------------
// let newUser = document.getElementById("newUser")
// newUser.onsubmit = async (e) => {
//     e.preventDefault();
//
//     let response = await fetch('/article/formdata/post/user', {
//         method: 'PUT',
//         body: new FormData(newUser)
//     });
//
//     let result = await response.json();
//
//     alert(result.message);
// };
//
// //--------------------------------------------------------------
// const buttonDelete = $("#delete");
// buttonDelete.click(
//     function () {
//         $.ajax("/rest", {
//             method: "DELETE",
//             data: {id: $(this).attr("value")}, //в rest-контроллер будет передан id=1 (см. value из тэга button выше)
//             dataType: "text",
//             success: function (msg) {
//                 $("#users")
//                     .find("#" + msg) //ищем div с id=1
//                     .remove();
//
//             }
//         })
//     }
// )
//
// //--------------------------------------------------------------
// const buttonEdit = $("#edit");
// buttonEdit.click(
//     function () {
//         $.ajax("/rest", {
//             method: "patch",
//             data:
//                 {
//                     id: $(this).attr("value"),//в rest-контроллер будет передан id=1 (см. value из тэга button выше)
//                     login: $(this).parent().find("#login").attr("value") //в rest-контроллер будет передан login=u1 (см. value из тэга input выше)
//                 },
//             dataType: "text",
//             success: function (msg) {
//                 $("#users")
//                     .find("#" + msg) //ищем div
//                     .text(";;;"); //изменяем текст
//
//             }
//         })
//     }
// )









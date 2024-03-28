import { login } from "./api/auth.js";

const loginBtn = document.querySelector("#login-btn");

document.addEventListener("DOMContentLoaded", (e) => {
  let userInfo = JSON.parse(localStorage.getItem("userInfo"));

    if(userInfo && userInfo["auth"]){
        window.location.replace("./home.html");

        return; 
    }
})

loginBtn.addEventListener("click", (e) => {
  e.preventDefault();

  login()
})


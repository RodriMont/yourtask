import { login } from "./api/auth.js";

const loginBtn = document.querySelector("#login-btn");

loginBtn.addEventListener("click", (e) => {
  e.preventDefault();

  login()
})


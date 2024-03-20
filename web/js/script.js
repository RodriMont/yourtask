import { login } from "./api/auth.js";
import { registrazione } from "./api/registrazione.js";
import { loginUI } from "./ui/login.js";

const registrazioneBtn = document.querySelector("#btn-registrazione");
const loginBtn = document.querySelector("#login-btn");

document.addEventListener("DOMContentLoaded", () => {
  loginUI();
});

registrazioneBtn.addEventListener("click", async (e) => {
  e.preventDefault();

  await registrazione();
    
  
});

loginBtn.addEventListener("click", (e) => {
  e.preventDefault();

  login()
})


import { registrazione } from "./api/registrazione.js";
import { loginUI } from "./ui/login.js";

const registrazioneBtn = document.querySelector("#btn-registrazione");

document.addEventListener("DOMContentLoaded", () => {
  loginUI();
});

registrazioneBtn.addEventListener("click", async (e) => {
  e.preventDefault();

  await registrazione();
    
  
});

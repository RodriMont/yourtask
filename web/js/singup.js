import { registrazione } from "./api/registrazione.js";
const singUpBtn = document.querySelector("#sing-up-btn");


singUpBtn.addEventListener("click", (e) => {
    e.preventDefault();
    registrazione();
    
})
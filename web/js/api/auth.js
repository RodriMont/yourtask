import { validateEmail } from "../helpers/validators.js";
import axios from "https://cdn.jsdelivr.net/npm/axios@1.3.5/+esm";


export const login = async () => {
    const inputEmail = document.querySelector("#sing-in-email");
    const inputPassword = document.querySelector("#sing-in-pasword");
    const email = inputEmail.value;
    const password = inputPassword.value;

    if([email, password].includes("")){
        alert("Tutti campi sono obbligatori")
        return;
    }

    if(!validateEmail(email)){
        alert("Email non valido");
        return; 
    }

    try{
        const {data} = await axios.post("http://127.0.0.1:5000/login", {email, password});

        console.log(data)
        if(!data.auth) {
            alert(data.message)
            return
        }

        inputEmail.value = "";
        inputPassword.value = "";
        window.location.href = "./home.html";

        let userInfo = {
            email,
            auth: data.auth,
            id: data["id"]
        }
        
        localStorage.setItem("userInfo", JSON.stringify(userInfo));

    }catch(e){
        console.warn(e)
       alert("Server error");
    }
}
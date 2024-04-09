import axios from "https://cdn.jsdelivr.net/npm/axios@1.3.5/+esm";

export async function registrazione() {
  const email = document.querySelector("#email");
  const password = document.querySelector("#password");
  const username = document.querySelector("#username");

  if ([email, password, username].includes("")) {
    alert("Tutti campi sono obbligatori");
    return false;
  }

  const data = {
    email: email.value,
    username: username.value,
    password: password.value,
  };
  try {

    const {data: dataRequest} = await axios.post(
      "http://127.0.0.1:5000/registrazione",
      data
    );


    if (!dataRequest.ok) {
      alert(dataRequest["message"]);
      return false;
    }

    alert("Utente creato");

    window.location.replace("./index.html");



    email.value = ""
    username.value = ""
    password.value = ""
    

    return true;
  } catch (err) {
    console.log("error: " + err);
  }
}

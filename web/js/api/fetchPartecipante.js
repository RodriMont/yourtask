import axios from "https://cdn.jsdelivr.net/npm/axios@1.3.5/+esm";

const fetchPartecipanti = async () => {
  const idProgetto = localStorage.getItem("idProgetto");
  console.log(idProgetto)
  try {
     const response = await fetch(
      `http://127.0.0.1:5000/utenti_progetto?id_progetto=${idProgetto}`
    );
    const data = await response.json();
 
    console.log(data)




  } catch (err) {
    console.log(err);
  }
};

export { fetchPartecipanti };

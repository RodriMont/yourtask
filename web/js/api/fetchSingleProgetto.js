import axios from "https://cdn.jsdelivr.net/npm/axios@1.3.5/+esm";
import { parseDate } from "../helpers/parseDate.js";


const fetchSingleProject = async () => {
    const id = localStorage.getItem("idProgetto");

    try{

        const {data} = await axios.get(`http://127.0.0.1:5000/progetto`, {
            params: {
                id
            }
        })

        const progettoContainer = document.querySelector(".progetto-container");
        const {nomeProgetto, dataAvvio, dataScadenza} = data;
        progettoContainer.innerHTML = `<div class="progetto ">
        <p class="bold margini-Ntask">${nomeProgetto}</p>
        <div class="dettagli">
            <div class="margini-d-dettagli">
                <p>Inizio</p>
                <img src="../image/calendar.png" alt="Immagine" width="20px">
                <span>${parseDate(dataAvvio)}</span>
            </div>
            <div>
                <p>Scadenza</p>
                <img src="../image/calendar.png" alt="Immagine" width="20px">
                <span>${parseDate(dataScadenza)}</span>
            </div>
            <div class="livello">
                <img src="../image/livello3.png" alt="livello progetto" width="60px">
            </div>
            <div class="partecipanti">
                <p>Partecipanti</p>
                <img src="../image/imagine1.jpg" alt="partecipante" width="80px">
                <img src="../image/imagine1.jpg" alt="partecipante" width="80px">
                <div class="more">
                <img src="../image/partecip.png" alt="partecipante" width="65px">
                </div>
            </div>
        </div>
    </div>`





    }catch(err){
        console.log(err)
    }
}

export {
    fetchSingleProject
}
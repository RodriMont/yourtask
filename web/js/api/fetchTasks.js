import axios from "https://cdn.jsdelivr.net/npm/axios@1.3.5/+esm";
import { parseDate } from "../helpers/parseDate.js";


const fetchSingleProject = async () => {

    try{

        const {data} = await axios.get(`http://127.0.0.1:5000/progetto`, {
            params: {
                id: localStorage.getItem("idProgetto")
            }
        })


        const {data: tasks} = await axios.get("http://127.0.0.1:5000/task_utente", {
            params: {
                id_progetto : localStorage.getItem("idProgetto"),
                id_utente: JSON.parse(localStorage.getItem("userInfo"))["id"]
            }
        });

        console.log(tasks)

        const nomeHTML = document.querySelector(".nome_progetto h1");
        nomeHTML.textContent = data.nomeProgetto

        const progettoContainer = document.querySelector(".progetto-container");
        let html = "";
        
        tasks.forEach(task => {
            html += `<div class="progetto ">
            <p class="bold margini-Ntask">${task.nome_task}</p>
            <div class="dettagli">
                <div class="margini-d-dettagli">
                    <p>Inizio</p>
                    <img src="../image/calendar.png" alt="Immagine" width="20px">
                    <span>${parseDate(task.data_avvio)}</span>
                </div>
                <div>
                    <p>Scadenza</p>
                    <img src="../image/calendar.png" alt="Immagine" width="20px">
                    <span>${parseDate(task.data_scadenza)}</span>
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
        </div> `
        });


        progettoContainer.innerHTML = html;





    }catch(err){
        console.log(err)
    }
}

export {
    fetchSingleProject
}
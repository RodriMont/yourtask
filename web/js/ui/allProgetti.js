import { fetchProgetti } from "../api/fetchProgetti.js";


document.addEventListener("DOMContentLoaded", (e) => {
    
    fetchProgetti()
});

document.addEventListener("click", (e) => {
    if(e.target.classList.contains("puntini_img")){
        const parent = e.target.parentElement;
        const idProject = parent.getAttribute("data_progetto_id")
        localStorage.setItem("idProjectPartecipante", idProject)
    }
})
import { fetchPartecipanti } from "../api/fetchPartecipante.js"

document.addEventListener("DOMContentLoaded", async () => {
    await fetchPartecipanti()
})
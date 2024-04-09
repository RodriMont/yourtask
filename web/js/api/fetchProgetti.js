import axios from "https://cdn.jsdelivr.net/npm/axios@1.3.5/+esm";

export async function fetchProgetti() {
  const id = JSON.parse(localStorage.getItem("userInfo"))["id"];
  try {
    const { data } = await axios.get("http://127.0.0.1:5000/progetti_utente", {
      params: {
        id,
      },
    });

    const progettoContainer = document.querySelector(".progetto-container");
    if (!progettoContainer) return;

    data.forEach((element) => {
      const progettoDiv = document.createElement("div");
      progettoDiv.classList.add("progetto");

      const { nome_progetto, data_avvio, data_scadenza, id } = element;

      progettoDiv.innerHTML = `<p class="bold margini-Nprogetto">${nome_progetto}</p>
            <div class="dettagli" data_progetto_id="${id}">
                <div class="margini-d-dettagli">
                    <p>Inizio</p>
                    <img src="../image/calendar.png" alt="Immagine" width="20px">
                    <span>${data_avvio}</span>
                </div>
                <div>
                    <p>Scadenza</p>
                    <img src="../image/calendar.png" alt="Immagine" width="20px">
                    <span>${data_scadenza}</span>
                </div>
                <div class="progress barra">
                    <div class="progress" role="progressbar" aria-label="Basic example" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100">
                        <div class="progress-bar" style="width: 50%"></div>
                      </div>
                </div>
                <div class="puntini">
                    <button data_progetto_id="${id}">
                        <img src="../image/tre_puntini.png" width="20px" class="puntini_img">
                    </button> 
                </div>
            </div>`;

      progettoContainer.appendChild(progettoDiv);
    });
  } catch (err) {
    console.log(err);
  }
}

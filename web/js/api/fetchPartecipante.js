const fetchPartecipanti = async () => {
  const idProgetto = localStorage.getItem("idProjectPartecipante");
  try {
    const response = await fetch(
      `http://127.0.0.1:5000/utenti_progetto?id_progetto=${idProgetto}`
    );
    const data = await response.json();

    const container = document.querySelector(".partecipanti");
      let html = ""

    data.forEach(user => {
      html += ` 
    <div class="partecip">
      <div>
        <img src="../image/imagine1.jpg" alt="immagine profilo" width="100px">
      </div>
      <div>
        <h2 class="nome">${user.username}</h2>
      </div>
    </div>  
`;

container.innerHTML = html
    });
    
  } catch (err) {
    console.log(err);
  }
};

export { fetchPartecipanti };

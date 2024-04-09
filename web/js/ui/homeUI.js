
document.body.addEventListener("click", (e) => {
  if (e.target.matches(".puntini_img")) handleClickThreeDots(e);
  if (e.target.matches(".popUp_membri")) handlePopUpMembri(e);
  if (e.target.matches(".progetto")) handleProgetto(e)  ;
});

const handleClickThreeDots = (e) => {
  let open = false;

  if (open) return;

  const partBtn = document.createElement("div");
  const treeDots = e.target.parentElement;
  partBtn.textContent = "Partecipanti";
  partBtn.classList.add("popUp_membri");
  treeDots.appendChild(partBtn);

  open = true;
};

const handlePopUpMembri = (e) => {
  e.stopPropagation();

  const button = e.target.parentElement;

  button.addEventListener("click", () => {
    window.location.href = "./partprogetto.html";
  });

  /* const popUp = document.querySelector(".popUp_membri");

    popUp.addEventListener("click", (e) => {

      window.location.href = "./partprogetto.html";
    });

    progettoContainer.addEventListener("click", (e) => {
      window.location.href = "./progetto.html";
    }); */
};

const handleProgetto = (e) => {
  const idProgetto = e.target.children[1].getAttribute("data_progetto_id");
  localStorage.setItem("idProgetto", idProgetto)
  window.location.href = "./progetto.html";

};


export { handleClickThreeDots };

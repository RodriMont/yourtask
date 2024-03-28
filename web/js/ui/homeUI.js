const threeDotsBtn = document.querySelector(".puntini button");
const progettoContainer = document.querySelector(".progetto");
let open = false;

threeDotsBtn.addEventListener("click", (e) => {
    e.preventDefault();
    e.stopPropagation();

    if(open) return;
    
    const partBtn = document.createElement("div");
    
    partBtn.textContent = "Partecipanti";
    partBtn.classList.add("popUp_membri");
    threeDotsBtn.appendChild(partBtn)
    open = true;

    const popUp = document.querySelector(".popUp_membri");

    popUp.addEventListener("click", (e) => {
        e.preventDefault();
        e.stopPropagation();
    
        window.location.href = "./partprogetto.html";
    })

})



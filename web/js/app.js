const btnLogout = document.querySelector(".logout");

btnLogout.addEventListener("click", (e) => {
    e.stopPropagation();
    localStorage.removeItem("userInfo");
    window.location.href = "./index.html"
})
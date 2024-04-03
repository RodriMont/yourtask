import { fetchProgetto } from "./api/fetchProgetto.js";

const btnLogout = document.querySelector(".logout");


btnLogout.addEventListener("click", (e) => {
  e.stopPropagation();
  localStorage.removeItem("userInfo");
  window.location.href = "./index.html";
});

document.addEventListener("DOMContentLoaded", () => {
    fetchProgetto();
})

document.onreadystatechange = function () {
  if (document.readyState == "interactive") {
    const userInfo = localStorage.getItem("userInfo");

    if (!userInfo) window.location.href = "index.html";
  }
};

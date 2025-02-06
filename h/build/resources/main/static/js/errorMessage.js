document.addEventListener("DOMContentLoaded", function () {
    const errorMessage = document.getElementById("model-error-message");
    const modal = document.getElementById("error-modal");
    const closeModal = document.getElementById("close-modal");

    if (errorMessage && errorMessage.textContent.trim() !== "") {
        modal.classList.remove("hidden");
    }

    closeModal.addEventListener("click", function () {
        modal.classList.add("hidden");
    });
});

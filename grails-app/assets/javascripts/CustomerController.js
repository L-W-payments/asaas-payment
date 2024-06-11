function CustomerController(reference) {
    const deleteButtonReference = reference.querySelector(".js-delete-button");

    function init() {
        bindActions();
    }

    function bindActions() {
        deleteButtonReference.addEventListener("atlas-button-click", function () {
            const buttonAction = deleteButtonReference.dataset.action;

            if (buttonAction === "delete") {
                openConfirmDeleteModal();
            }
        });
    }

    function openConfirmDeleteModal() {
        Atlas.notifications.showConfirmation({
            illustration: "triangle-exclamation-mark-siren",
            title: "Deseja remover esta conta?",
            confirmButton: {
                description: "Confirmar remoção",
                theme: "danger"
            },
            onConfirm: confirmDelete,
            disableAutoClose: false
        })
    }

    async function confirmDelete(modal) {
        const response = await Atlas.request.post(deleteButtonReference.dataset.deleteUrl);

        if (response.success) {
            modal.closeModal();
            redirectToLogout();
            return;
        }

        Atlas.notifications.showAlert(response.alert, "error");
    }

    function redirectToLogout() {
        window.location.href = deleteButtonReference.dataset.redirectUrl
    }

    init();
}

let customerController;

document.addEventListener("AtlasContentLoaded", () => {
    customerController = new CustomerController(document.querySelector(".js-person-form"));
});
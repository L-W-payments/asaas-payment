function PaymentController(reference) {

    var deleteButtonReference = reference.querySelector(".js-delete-button");

    function init() {
        bindActions();
    }

    function bindActions() {
        deleteButtonReference.addEventListener("atlas-button-click", function () {
            console.log("Entrou aqui")
            var buttonAction = deleteButtonReference.dataset.action;
            if (buttonAction === "delete") {
                openConfirmDeleteModal();
                return;
            }
        });
    }

    function openConfirmDeleteModal() {
        Atlas.notifications.showConfirmation({
            illustration: "triangle-exclamation-mark-siren",
            title: "Deseja remover esta cobrança?",
            confirmButton: {
                description: "Confirmar Remoção",
                theme: "danger"
            },
            onConfirm: confirmDelete,
            disableAutoClose: false
        })
    }

    function confirmDelete(modal) {
        Atlas.request.post(deleteButtonReference.dataset.deleteUrl).then(function (response) {
            if (response.success) {
                Atlas.notifications.showAlert("Cobrança removida com sucesso!", "success");
                tableReference.fetchRecords(true);
                modal.closeModal();
                return;
            }
            Atlas.notifications.showAlert(response.alert, "error");
        });
    }

}


var paymentController;

document.addEventListener("AtlasContentLoaded", function () {
    var reference = document.querySelector(".js-show-payment-panel");
    paymentController = new PaymentController(reference);
});
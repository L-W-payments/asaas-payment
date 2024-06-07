function PaymentListController(reference) {

    var tableReference = reference.querySelector(".js-payment-list-table");
    var inputReference = reference.querySelector(".js-payment-search-input");
    var filterReference = reference.querySelector(".js-payment-filter-input");
    var currentRow = null;

    function init() {
        bindInputReference();
        bindTableSearch();
        bindTableActions();
        bindFilterReference();
    }

    function bindFilterReference() {
        filterReference.addEventListener("atlas-apply-filter", function () {
            tableReference.fetchRecords(true);
        });

        filterReference.addEventListener("atlas-clean-filter", function () {
            tableReference.fetchRecords(true);
        });
    }

    function bindInputReference() {
        inputReference.addEventListener("atlas-input-trigger-search", function () {
            tableReference.fetchRecords(true);
        });
    }

    function bindTableSearch() {
        tableReference.addEventListener("atlas-table-before-search", function () {
            filterReference.enableButtons();
            inputReference.readonly = true;

            var filterData = filterReference.getFilterData();
            filterData.payerName = inputReference.value;

            tableReference.params = filterData;
        });

        tableReference.addEventListener("atlas-table-after-search", function () {
            inputReference.readonly = false;
            tableReference.params = {};
        });
    }

    function bindTableActions() {
        tableReference.addEventListener("atlas-table-action-click", function (event) {
            var button = event.detail.button;
            var buttonAction = button.dataset.action;

            if (buttonAction === "edit") {
                window.location = event.detail.row.href;
                return;
            }

            if (buttonAction === "delete") {
                openConfirmDeleteModal();
                currentRow = event.detail.row;
                return;
            }

            if (buttonAction === "restore") {
                openConfirmRestoreModal();
                currentRow = event.detail.row;
            }
        });
    }

    function confirmDelete(modal) {
        Atlas.request.post(currentRow.dataset.actionUrl).then(function (response) {
            if (response.success) {
                Atlas.notifications.showAlert("Cobrança removida com sucesso!", "success");
                tableReference.fetchRecords(true);
                modal.closeModal();
                return;
            }
            Atlas.notifications.showAlert(response.alert, "error");
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

    function confirmRestore(modal) {
        Atlas.request.post(currentRow.dataset.actionUrl).then(function (response) {

            if (response.success) {
                Atlas.notifications.showAlert("Cobrança restaurada com sucesso!", "success");
                tableReference.fetchRecords(true);
                modal.closeModal();
                return;
            }

            Atlas.notifications.showAlert(response.alert, "error");
        });
    }

    function openConfirmRestoreModal() {
        Atlas.notifications.showConfirmation({
            illustration: "balloon-dollarsign-arrow",
            title: "Deseja restaurar esta cobrança?",
            confirmButton: {
                description: "Confirmar restauração",
                theme: "primary"
            },
            onConfirm: confirmRestore,
            disableAutoClose: false
        })
    }

    init();
}

var paymentListController;

document.addEventListener("AtlasContentLoaded", function () {
    var reference = document.querySelector(".js-payment-panel");
    paymentListController = new PaymentListController(reference);
});
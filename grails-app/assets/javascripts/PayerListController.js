function PayerListController(reference) {

    var tableReference = reference.querySelector(".js-payer-list-table");
    var inputReference = reference.querySelector(".js-payer-search-input");
    var filterReference = reference.querySelector(".js-payer-filter-input")
    var deleteRow = null;

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

            tableReference.params = {
                name: inputReference.value,
                filter: filterData.list
            }
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
            if (buttonAction == "edit") {
                window.location = event.detail.row.href;
                return;
            }
            if (buttonAction == "delete") {
                openConfirmDeleteModal();
                deleteRow = event.detail.row;
                return;
            }

        });
    }

    function confirmDelete(modal) {
        Atlas.request.post(deleteRow.dataset.deleteUrl).then(function (response) {
            if (response.success) {
                Atlas.notifications.showAlert("Pagador removido com sucesso!", "success");
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
            title: "Deseja remover este pagador?",
            confirmButton: {
                description: "Confrimar Remoção",
                theme: "danger"
            },
            onConfirm: confirmDelete,
            disableAutoClose: false
        })
    }


    init();
}

var payerListController;

document.addEventListener("AtlasContentLoaded", function () {
    var reference = document.querySelector(".js-payer-panel");
    payerListController = new PayerListController(reference);
});
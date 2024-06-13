function UserListController(reference) {
    const tableReference = reference.querySelector(".js-user-list-table");
    const inputReference = reference.querySelector(".js-user-search-input");
    const filterReference = reference.querySelector(".js-payer-filter-input");
    let currentRow = null;

    function init() {
        bindInputReference();
        bindTableSearch();
        bindTableActions();
        bindFilterReference();
    }

    function bindInputReference() {
        inputReference.addEventListener("atlas-input-trigger-search", () => {
            tableReference.fetchRecords(true);
        });
    }

    function bindTableSearch() {
        tableReference.addEventListener("atlas-table-before-search", () => {
            filterReference.enableButtons();
            inputReference.readonly = true;

            const filterData = filterReference.getFilterData();
            filterData.email = inputReference.value;

            tableReference.params = filterData;
        });

        tableReference.addEventListener("atlas-table-after-search", () => {
            inputReference.readonly = false;
            tableReference.params = {};
        });
    }

    function bindFilterReference() {
        filterReference.addEventListener("atlas-apply-filter", function () {
            tableReference.fetchRecords(true);
        });

        filterReference.addEventListener("atlas-clean-filter", function () {
            tableReference.fetchRecords(true);
        });
    }

    function bindTableActions() {
        tableReference.addEventListener("atlas-table-action-click", (event) => {
            const button = event.detail.button;
            const buttonAction = button.dataset.action;

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

    async function confirmDelete(modal) {
        const response = await Atlas.request.post(currentRow.dataset.actionUrl);

        modal.closeModal();

        if (response.success) {
            Atlas.notifications.showAlert("Usuário removido com sucesso!", "success");
            tableReference.fetchRecords(true);
            return;
        }

        Atlas.notifications.showAlert(response.alert, "error");
    }

    function openConfirmDeleteModal() {
        Atlas.notifications.showConfirmation({
            illustration: "triangle-exclamation-mark-siren",
            title: "Deseja remover este usuário?",
            confirmButton: {
                description: "Confirmar remoção",
                theme: "danger"
            },
            onConfirm: confirmDelete,
            disableAutoClose: false
        })
    }

    async function confirmRestore(modal) {
        const response = await Atlas.request.post(currentRow.dataset.actionUrl);

        modal.closeModal();

        if (response.success) {
            Atlas.notifications.showAlert("Usuário restaurado com sucesso!", "success");
            tableReference.fetchRecords(true);
            return;
        }

        Atlas.notifications.showAlert(response.alert, "error");
    }

    function openConfirmRestoreModal() {
        Atlas.notifications.showConfirmation({
            illustration: "user-avatar-groups",
            title: "Deseja restaurar este usuário?",
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

let userListController;

document.addEventListener("AtlasContentLoaded", () => {
    userListController = new UserListController(document.querySelector(".js-user-panel"));
});
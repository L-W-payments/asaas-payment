function NotificationController(reference) {
    const init = () => {
        bindNotification();
    }

    /**
     * @returns {Promise<Notification>}
     */
    async function fetchNotifications() {
        return await Atlas.request.get("/notification/list");
    }

    function bindNotification() {
        reference.addEventListener("atlas-dropdown-opened", async () => {
            const data = await fetchNotifications();

            if (data.success) {
                return reference.innerHTML = data.template;
            }

            Atlas.notifications.showAlert("Erro ao carregar notificações", "error");
        });
    }

    init();
}

let notificationController = null;

document.addEventListener("AtlasContentLoaded", () => {
    notificationController = new NotificationController(document.getElementById("notifications-dropdown"));
})

/**
 * @typedef {Object} Notification
 * @property {boolean} success
 * @property {string} template
 */
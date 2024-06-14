function LoginController() {
    const init = () => {
        removeRegisteredParamsFromUrl();
    }

    function removeRegisteredParamsFromUrl() {
        const url = new URL(window.location.href);
        const params = url.searchParams;

        if (params.has("registered")) {
            params.delete("registered");

            window.history.replaceState({}, document.title, url.toString());
        }
    }

    init();
}

const loginController = new LoginController();
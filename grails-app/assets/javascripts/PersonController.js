function PersonController(reference) {
    const postalCodeField = reference.querySelector("atlas-postal-code");

    const viaCepURL = "https://viacep.com.br/ws/${cep}/json"

    const inputObject = {
        localidade: reference.querySelector("#city"),
        uf: reference.querySelector("#state"),
        bairro: reference.querySelector("#district"),
        logradouro: reference.querySelector("#street")
    };

    const init = () => {
        bindPostalCode();
    }

    function bindPostalCode() {
        postalCodeField.addEventListener("atlas-input-change", () => {
            handleAddress();
        });

        postalCodeField.addEventListener("paste", () => {
            handleAddress();
        });
    }

    async function handleAddress() {
        if (!postalCodeField.checkValidity() || postalCodeField.getElementValue().length < 9){
            populateInputAddress();
            return;
        }

        const postalCodeData = await Atlas.request.get(viaCepURL.replace("${cep}", postalCodeField.getElementValue()));

        if (postalCodeData.erro) {
            populateInputAddress();
            return;
        }

        populateInputAddress(postalCodeData);
    }

    function populateInputAddress(address) {
        if (!address) {
            Object.entries(inputObject).forEach(([name, input]) => {
                input.value = "";
                input.disabled = false;
            });
            return;
        }

        Object.entries(inputObject).forEach(([name, input]) => {
            input.value = address[name];
            input.disabled = true;
        });
    }

    init();
}

let personController = null;

document.addEventListener("AtlasContentLoaded", () => {
    personController = new PersonController(document.querySelector(".js-person-form"));
});
function PaymentReceiptController(reference) {

    const init = () => {
        bindDownloadButton();
    }

    function bindDownloadButton() {
        reference.querySelector(".js-download-button").addEventListener("atlas-button-click", () => {
            downloadAsPdf();
        });
    }

    function downloadAsPdf() {
        html2canvas([reference.querySelector(".js-receipt-content")], {
            onrendered: (canvas) => {
                const imgData = canvas.toDataURL("image/png");
                const pdf = new jspdf.jsPDF();

                pdf.addImage(imgData, "PNG", 0, 0);
                pdf.save("recibo.pdf");
            }
        });
    }

    init();
}

let paymentReceiptController = null;

document.addEventListener("AtlasContentLoaded", () => {
    paymentReceiptController = new PaymentReceiptController(document.querySelector("body"));
});
package br.com.bookstore.purchase.purchase.builders;

import br.com.bookstore.purchase.purchase.Purchase;
import br.com.bookstore.purchase.purchase.Status;

public class PurchaseBuilder {

    public static Purchase.Builder createPurchase(){
        return Purchase
                .builder()
                .id(1L)
                .specificIdBooks("6b9bcc2e-34e9-4330-8a25-d3b1bd1ee2a0")
                .specificIdClient("a79e858b-d7f0-43d6-a594-b12021a6ccfd")
                .amountToPay(200.00)
                .specificID("69661bd1-6092-4068-bd28-c60517f8a16b")
                .status(Status.PENDING);
    }
}

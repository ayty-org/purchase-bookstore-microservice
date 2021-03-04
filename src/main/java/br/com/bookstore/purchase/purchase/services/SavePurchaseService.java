package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.purchase.PurchaseSaveDTO;

@FunctionalInterface
public interface SavePurchaseService {
    void insert(PurchaseSaveDTO purchaseSaveDTO);
}

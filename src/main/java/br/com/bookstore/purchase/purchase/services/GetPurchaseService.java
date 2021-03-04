package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;

@FunctionalInterface
public interface GetPurchaseService {
    PurchaseReturnDTO findById(Long id);
}

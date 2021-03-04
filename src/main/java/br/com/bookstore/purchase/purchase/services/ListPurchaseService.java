package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;

import java.util.List;

@FunctionalInterface
public interface ListPurchaseService {
    List<PurchaseReturnDTO> findAll();
}

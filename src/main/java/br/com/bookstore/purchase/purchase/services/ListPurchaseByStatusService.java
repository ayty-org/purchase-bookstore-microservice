package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;
import br.com.bookstore.purchase.purchase.Status;

import java.util.List;

@FunctionalInterface
public interface ListPurchaseByStatusService {
    List<PurchaseReturnDTO> findAllPurchaseByStatus(Status status);
}

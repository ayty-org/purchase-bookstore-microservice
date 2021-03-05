package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.exceptions.PurchaseNotFoundException;
import br.com.bookstore.purchase.purchase.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePurchaseServiceImpl implements DeletePurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Override
    public void delete(Long id) {
        if(!purchaseRepository.existsById(id)) {
            throw new PurchaseNotFoundException();
        }

        purchaseRepository.deleteById(id);
    }
}

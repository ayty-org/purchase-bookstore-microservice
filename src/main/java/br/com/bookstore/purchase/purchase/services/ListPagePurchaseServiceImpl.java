package br.com.bookstore.purchase.purchase.services;

import br.com.bookstore.purchase.feign.FeignGetBook;
import br.com.bookstore.purchase.feign.FeignGetClient;
import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;
import br.com.bookstore.purchase.purchase.services.utils.ReturnAllPurchase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListPagePurchaseServiceImpl implements ListPagePurchaseService{

    private final ReturnAllPurchase returnAllPurchase;
    private final FeignGetBook feignGetBook;
    private final FeignGetClient feignGetClient;

    @Override
    public Page<PurchaseReturnDTO> findPage(Pageable pageable) {
        List<PurchaseReturnDTO> purchaseReturnDTOList = returnAllPurchase.findAllPurchase();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), purchaseReturnDTOList.size());
        return new PageImpl(purchaseReturnDTOList.subList(start, end), pageable, purchaseReturnDTOList.size());
    }
}

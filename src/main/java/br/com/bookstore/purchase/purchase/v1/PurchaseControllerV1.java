package br.com.bookstore.purchase.purchase.v1;

import br.com.bookstore.purchase.purchase.PurchaseReturnDTO;
import br.com.bookstore.purchase.purchase.PurchaseSaveDTO;
import br.com.bookstore.purchase.purchase.services.ListPurchaseService;
import br.com.bookstore.purchase.purchase.services.SavePurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/api/purchase")
public class PurchaseControllerV1 {

    private final SavePurchaseService savePurchaseService;
    private final ListPurchaseService listPurchaseService;

    @GetMapping(value = "/")
    public List<PurchaseReturnDTO> findAll(){
        return listPurchaseService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody PurchaseSaveDTO purchaseSaveDTO){
        savePurchaseService.insert(purchaseSaveDTO);
    }
}

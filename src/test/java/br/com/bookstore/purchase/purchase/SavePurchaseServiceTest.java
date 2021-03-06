package br.com.bookstore.purchase.purchase;

import br.com.bookstore.purchase.feign.FeignGetBook;
import br.com.bookstore.purchase.feign.FeignGetClient;
import br.com.bookstore.purchase.purchase.services.SavePurchaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.bookstore.purchase.purchase.builders.PurchaseSaveBuilder.createPurchaseSave;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Valida a funcionalidade dos serviços responsáveis pela compra salva")
class SavePurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private FeignGetBook feignGetBook;

    @Mock
    private FeignGetClient feignGetClient;

    private SavePurchaseServiceImpl savePurchaseService;

    @BeforeEach
    void setUp() {
        this.savePurchaseService = new SavePurchaseServiceImpl(purchaseRepository, feignGetBook, feignGetClient);
    }

    @Test
    @DisplayName("save devolve a compra quando bem sucedida")
    void SaveReturnsPurchaseWhenSuccessful() {
        savePurchaseService.insert(createPurchaseSave().build());

        ArgumentCaptor<Purchase> purchaseArgumentCaptor = ArgumentCaptor.forClass(Purchase.class);
        verify(purchaseRepository).save(purchaseArgumentCaptor.capture());

        Purchase result = purchaseArgumentCaptor.getValue();

        assertAll( "Purchase",
                ()-> assertThat(result.getSpecificIdBooks(), is("fe07d7bb-2cac-4c47-b9f0-19aa2df60949,")),
                ()-> assertThat(result.getSpecificIdClient(), is("69661bd1-6092-4068-bd28-c60517f8a16b")),
                ()-> assertThat(result.getAmountToPay(), is(100.00)),
                ()-> assertThat(result.getStatus(), is(Status.PENDING))
        );
    }
}
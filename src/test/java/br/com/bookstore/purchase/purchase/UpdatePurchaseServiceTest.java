package br.com.bookstore.purchase.purchase;

import br.com.bookstore.purchase.purchase.services.UpdatePurchaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.bookstore.purchase.purchase.builders.PurchaseBuilder.createPurchase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Valida a funcionalidade dos serviços responsáveis pela compra de atualizações")
class UpdatePurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    private UpdatePurchaseServiceImpl updatePurchaseService;

    @BeforeEach
    void setUp() {
        this.updatePurchaseService = new UpdatePurchaseServiceImpl(purchaseRepository);
    }

    @Test
    @DisplayName("atualize a compra quando for bem-sucedida")
    void UpdateReturnsPurchaseUpdateWhenSuccessful(){
        when(purchaseRepository.findById(1L)).thenReturn(Optional.of(createPurchase().build()));
        updatePurchaseService.update(1L);

        ArgumentCaptor<Purchase> purchaseArgumentCaptor = ArgumentCaptor.forClass(Purchase.class);
        verify(purchaseRepository).save(purchaseArgumentCaptor.capture());

        Purchase result = purchaseArgumentCaptor.getValue();

        assertAll("Purchase",
                () -> assertThat(result.getSpecificID(), is("69661bd1-6092-4068-bd28-c60517f8a16b")),
                () -> assertThat(result.getStatus(), is(Status.DONE))
        );
    }
}
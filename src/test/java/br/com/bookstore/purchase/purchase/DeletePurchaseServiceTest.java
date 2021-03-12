package br.com.bookstore.purchase.purchase;

import br.com.bookstore.purchase.exceptions.PurchaseNotFoundException;
import br.com.bookstore.purchase.purchase.services.DeletePurchaseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Valida a funcionalidade dos serviços responsáveis por excluir compra")
class DeletePurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepositoryMock;

    private DeletePurchaseServiceImpl deletePurchaseService;

    @BeforeEach
    void setUp() {
        this.deletePurchaseService = new DeletePurchaseServiceImpl(purchaseRepositoryMock);
    }

    @Test
    @DisplayName("delete remova a compra quando bem sucedida")
    void deleteRemovePurchaseWhenSuccessful() {

        when(purchaseRepositoryMock.existsById(anyLong())).thenReturn(true);
        deletePurchaseService.delete(1L);
        verify(purchaseRepositoryMock).existsById(anyLong());
    }

    @Test
    @DisplayName("delete lança PurchaseFoundException quando a compra não é encontrada")
    void deleteThrowPurchaseWhenPurchaseNotFound() {

        when(purchaseRepositoryMock.existsById(anyLong())).thenReturn(false);
        Assertions.assertThrows(PurchaseNotFoundException.class, ()-> deletePurchaseService.delete(1L));
        verify(purchaseRepositoryMock, times(0)).deleteById(anyLong());
    }
}
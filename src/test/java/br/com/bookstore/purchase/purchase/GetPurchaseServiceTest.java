package br.com.bookstore.purchase.purchase;

import br.com.bookstore.purchase.feign.GetBook;
import br.com.bookstore.purchase.feign.GetClient;
import br.com.bookstore.purchase.purchase.services.GetPurchaseServiceImpl;
import br.com.bookstore.purchase.purchase.services.utils.ReturnSetBooksOfFeign;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Validates the functionality of the services responsible for searching for purchase by id ")
public class GetPurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepositoryMock;

    @Mock
    private GetBook getBook;
    @Mock
    private GetClient getClient;

    private GetPurchaseServiceImpl getPurchaseService;

    @BeforeEach
    void setUp() {
        this.getPurchaseService = new GetPurchaseServiceImpl(purchaseRepositoryMock, (ReturnSetBooksOfFeign) getBook, getClient);
    }

    @Test
    @DisplayName("findById returns purchase when successful")
    void findByIdReturnBookWhenSuccessful(){

    }
}
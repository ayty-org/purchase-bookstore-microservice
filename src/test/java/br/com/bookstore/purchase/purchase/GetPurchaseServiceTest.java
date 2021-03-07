package br.com.bookstore.purchase.purchase;

import br.com.bookstore.purchase.exceptions.PurchaseNotFoundException;
import br.com.bookstore.purchase.feign.GetBook;
import br.com.bookstore.purchase.feign.GetClient;
import br.com.bookstore.purchase.purchase.services.GetPurchaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.bookstore.purchase.purchase.builders.BookBuilder.createBook;
import static br.com.bookstore.purchase.purchase.builders.ClientBuilder.createClient;
import static br.com.bookstore.purchase.purchase.builders.PurchaseBuilder.createPurchase;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Service")
@DisplayName("Validates the functionality of the services responsible for searching for purchase by id ")
public class GetPurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepositoryMock;

    @Mock
    private GetClient getClient;

    @Mock
    private GetBook getBook;

    private GetPurchaseServiceImpl getPurchaseService;

    @BeforeEach
    void setUp() {
        this.getPurchaseService = new GetPurchaseServiceImpl(purchaseRepositoryMock, getBook, getClient);
    }

    @Test
    @DisplayName("findById")
    void findByIdPurchasePurchaseNotFoundExceptionWhenPurchaseWhen() {
        when(purchaseRepositoryMock.findById(anyLong())).thenReturn(Optional.of(createPurchase().build()));

        when(getClient.findSpecificID(anyString())).thenReturn(createClient().build());

        BookDTO bookDTO = createBook().build();

        when(getBook.findSpecificID(anyString())).thenReturn(bookDTO);

        PurchaseReturnDTO result = this.getPurchaseService.findById(1L);

        assertAll( "Purchase",
                ()-> assertThat(result.getClient().getSpecificID(), is("a79e858b-d7f0-43d6-a594-b12021a6ccfd")),
                ()-> assertThat(result.getPurchasedBooks().get(0).getSpecificID(), is("6b9bcc2e-34e9-4330-8a25-d3b1bd1ee2a0")),
                ()-> assertThat(result.getAmountToPay(), is(200.00)),
                ()-> assertThat(result.getStatus(), is(Status.PENDING))
        );

        verify(purchaseRepositoryMock).findById(anyLong());
    }

    @Test
    @DisplayName("findById throws PurchaseNotFoundException when purchase is not found")
    void findByIdPurchaseThrowPurchaseNotFoundExceptionWhenPurchaseNotFound() {
        when(purchaseRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(PurchaseNotFoundException.class, ()-> getPurchaseService.findById(1L));
        verify(purchaseRepositoryMock, times(1)).findById(anyLong());
    }
}
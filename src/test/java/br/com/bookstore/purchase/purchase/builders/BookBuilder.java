package br.com.bookstore.purchase.purchase.builders;

import br.com.bookstore.purchase.purchase.BookDTO;
import br.com.bookstore.purchase.purchase.CategoryDTO;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static br.com.bookstore.purchase.purchase.builders.CategoryBuilder.createCategory;

public class BookBuilder {

    public static BookDTO.Builder createBook(){
        Set<CategoryDTO> categoryDTOS = new HashSet<>();
        categoryDTOS.add(createCategory().build());
        return BookDTO.builder()
                .title("O Pequeno Príncipe")
                .sinopse("O Pequeno Príncipe representa a espontaneidade.")
                .isbn("978-3-16-148410-0")
                .autor("Antoine de Saint")
                .yearOfPublication(LocalDate.of(1943, 4, 6))
                .sellPrice(10.00)
                .specificID("6b9bcc2e-34e9-4330-8a25-d3b1bd1ee2a0")
                .quantityAvailable(2)
                .categories(categoryDTOS);
    }
}

package br.com.bookstore.purchase.purchase.builders;

import br.com.bookstore.purchase.purchase.CategoryDTO;

public class CategoryBuilder {

    public static CategoryDTO.Builder createCategory(){
        return CategoryDTO
                .builder()
                .name("Romance");
    };
}

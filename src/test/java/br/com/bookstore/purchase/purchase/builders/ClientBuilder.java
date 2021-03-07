package br.com.bookstore.purchase.purchase.builders;

import br.com.bookstore.purchase.purchase.ClientDTO;
import br.com.bookstore.purchase.purchase.Sex;

public class ClientBuilder {
    public static ClientDTO.Builder createClient() {
        return ClientDTO
                .builder()
                .id(1L)
                .name("Aktsuki")
                .age(22)
                .email("teste@email")
                .specificID("a79e858b-d7f0-43d6-a594-b12021a6ccfd")
                .phone("teste-phone")
                .sexo(Sex.MASCULINO);
    }
}

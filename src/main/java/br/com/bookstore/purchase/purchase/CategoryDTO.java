package br.com.bookstore.purchase.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

}

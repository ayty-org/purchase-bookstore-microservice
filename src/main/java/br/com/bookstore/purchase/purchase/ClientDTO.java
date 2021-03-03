package br.com.bookstore.purchase.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class ClientDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotEmpty(message = "The client name cannot be empty")
    private String name;

    @NotNull(message = "The client age cannot be null")
    @Min(value = 2)
    private Integer age;

    @NotEmpty(message = "The client phone cannot be empty")
    private String phone;

    @NotEmpty(message = "The client email cannot be empty")
    @Email
    private String email;

    @NotNull(message = "The client sexo cannot be null")
    @Enumerated(EnumType.STRING)
    private Sex sexo;

    @NotNull
    private String specificID = UUID.randomUUID().toString();
}
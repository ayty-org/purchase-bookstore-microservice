package br.com.bookstore.purchase.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class BookDTO {

    @NotNull
    private String title;

    @Size(max = 500)
    @NotNull
    private String sinopse;

    @Size(min = 1)
    @NotNull
    private String autor;

    @Size(min = 17, max = 17, message = "ISBN must contain 17 characters" + "\n Ex.: 978-3-16-148410-0")
    @NotNull(message = "ISBN cannot be null")
    private String isbn;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate yearOfPublication;

    @NotNull
    @DecimalMin(value = "0.00", message = "The min value to sell price is {value} .")
    private double sellPrice;

    @NotNull
    @Min(0)
    private int quantityAvailable;

    @NotNull
    private String specificID;

    @NotNull
    private Set<CategoryDTO> categories = new HashSet<>();
}
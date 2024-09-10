package rsh.catalog.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProductPayload(

        @NotNull(message = "{errors.catalog.product.create.title_not_specified}") @Size(min = 3, max = 50, message = "{errors.catalog.product.create.invalid_title}") String title,

        @Size(max = 1000, message = "{errors.catalog.product.create.invalid_details}") String details) {
}

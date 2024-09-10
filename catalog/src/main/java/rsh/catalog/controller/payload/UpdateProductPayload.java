package rsh.catalog.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProductPayload(
        @NotNull(message = "{errors.catalog.product.update.title_not_specified}") @Size(min = 3, max = 50, message = "{errors.catalog.product.update.invalid_title}") String title,

        @Size(max = 100, message = "{errors.catalog.product.update.invalid_details}") String details) {

}

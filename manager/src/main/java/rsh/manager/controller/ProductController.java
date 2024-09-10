package rsh.manager.controller;

import java.util.Locale;
import java.util.NoSuchElementException;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import rsh.manager.client.BadRequestException;
import rsh.manager.client.ProductsRestClient;
import rsh.manager.controller.payload.UpdateProductPayload;
import rsh.manager.entity.Product;

@Controller
@RequestMapping("/catalog/products/{productId:\\d+}")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsRestClient productsRestClient;

    private final MessageSource messageSource;

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") int productId) {
        return this.productsRestClient.findProduct(productId)
                .orElseThrow(() -> new NoSuchElementException("errors.catalog.product.not_found"));
    }

    @GetMapping
    public String getProduct() {
        return "/catalog/products/product";
    }

    @GetMapping("update")
    public String getProductUpdatePage() {
        return "/catalog/products/update";
    }

    @PostMapping("update")
    public String updateProduct(
            @ModelAttribute(name = "product", binding = false) Product product,
            UpdateProductPayload payload,
            Model model) {
        try {
            this.productsRestClient.updateProduct(product.id(), payload.title(), payload.details());
            return "redirect:/catalog/products/%d".formatted(product.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "/catalog/products/update";
        }
    }

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        this.productsRestClient.deleteProduct(product.id());
        return "redirect:/catalog/products/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model,
            HttpServletResponse response, Locale Locale) {

        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(), new Object[0], exception.getMessage(), Locale));

        return "errors/404";
    }

}

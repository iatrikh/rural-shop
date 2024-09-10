package rsh.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import rsh.manager.client.BadRequestException;
import rsh.manager.client.ProductsRestClient;
import rsh.manager.controller.payload.CreateProductPayload;
import rsh.manager.entity.Product;

@Controller
@RequiredArgsConstructor
@RequestMapping("/catalog/products")
public class ProductsController {

    private final ProductsRestClient productsRestClient;

    @GetMapping("list")
    public String getProductsList(Model model) {
        model.addAttribute("products", this.productsRestClient.findAllProducts());
        return "/catalog/products/list";
    }

    @GetMapping("create")
    public String getCreateProduct() {
        return "/catalog/products/create";
    }

    @PostMapping("create")
    public String postCreateProduct(CreateProductPayload payload, Model model) {
        try {
            Product product = this.productsRestClient.createProduct(payload.title(), payload.details());
            return "redirect:/catalog/products/%d".formatted(product.id());
        } catch (BadRequestException exception) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "/catalog/products/create";
        }
    }
}

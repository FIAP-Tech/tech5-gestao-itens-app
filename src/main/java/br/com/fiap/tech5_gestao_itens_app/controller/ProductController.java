package br.com.fiap.tech5_gestao_itens_app.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.tech5_gestao_itens_app.dto.ProductRequest;
import br.com.fiap.tech5_gestao_itens_app.dto.ProductResponse;
import br.com.fiap.tech5_gestao_itens_app.service.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
public class ProductController {

    private final ProductService productService;

    private static final Logger logger = Logger.getLogger(ProductController.class.getName());

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ProductResponse getByIdProduct(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);

    }

    @PostMapping("/{idProduto}/atualizar-estoque")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> atualizarEstoqueProduto(@PathVariable Long idProduto, @RequestParam Integer quantidadeVendida) {
        return productService.atualizarEstoqueProduto(idProduto, quantidadeVendida)
                .map(order -> ResponseEntity.ok("Estoque atualizado com Sucesso"))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}

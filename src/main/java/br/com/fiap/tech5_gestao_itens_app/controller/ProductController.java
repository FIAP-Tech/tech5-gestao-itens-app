package br.com.fiap.produtos.controller;

import br.com.fiap.produtos.dto.ProductRequest;
import br.com.fiap.produtos.dto.ProductResponse;
import br.com.fiap.produtos.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    private static final Logger logger = Logger.getLogger(ProductController.class.getName());



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getByIdProduct(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }


    @PutMapping("/{id}")
    public String updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);

    }

    @PostMapping("/{idProduto}/atualizar-estoque")
    public ResponseEntity<String> atualizarEstoqueProduto(@PathVariable Long idProduto, @RequestParam Integer quantidadeVendida) {
        return productService.atualizarEstoqueProduto(idProduto, quantidadeVendida)
                .map(order -> ResponseEntity.ok("Estoque atualizado com Sucesso"))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}

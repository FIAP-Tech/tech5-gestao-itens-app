package br.com.fiap.produtos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.produtos.dto.ProductRequest;
import br.com.fiap.produtos.dto.ProductResponse;
import br.com.fiap.produtos.model.Product;
import br.com.fiap.produtos.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder().name(productRequest.name()).description(productRequest.description()).quantity(productRequest.quantity()).price(productRequest.price()).build();
        productRepository.save(product);
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getQuantity(), product.getPrice());
    }


    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getQuantity(), product.getPrice())).toList();
    }

    public ProductResponse getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getQuantity(), product.getPrice());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }



    public String updateProduct(Long id, ProductRequest productRequest) {
        var produto = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (produto != null) {
            produto.setName(productRequest.name());
            produto.setDescription(productRequest.description());
            produto.setPrice(productRequest.price());
            productRepository.save(produto);
            log.info("Produto atualizado!");
            return "Produto atualizado!";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteProduct(Long id) {
        var produto = productRepository.findById(id);
        if (produto.isPresent()) {
            productRepository.delete(produto.get());
            log.info("Produto removido!");
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Produto não existe na base de dados!");
        }
    }

    public Optional<Product> atualizarEstoqueProduto(Long idProduto, Integer quantidadeVendida) {
        Optional<Product> optionalProduct = productRepository.findById(idProduto);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setQuantity(product.getQuantity() - quantidadeVendida);

            return Optional.of(productRepository.save(product));
        } else {
            return Optional.empty();
        }
    }
}

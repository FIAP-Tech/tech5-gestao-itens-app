package br.com.fiap.produtos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.produtos.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

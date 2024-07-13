package br.com.fiap.tech5_gestao_itens_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.tech5_gestao_itens_app.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

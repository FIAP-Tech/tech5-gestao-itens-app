package br.com.fiap.tech5_gestao_itens_app.dto;

import java.math.BigDecimal;

public record ProductRequest(Long id, String name, String description, Integer quantity, BigDecimal price) {

}

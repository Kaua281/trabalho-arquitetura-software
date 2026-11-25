package br.edu.ucsal.ms_vendas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "VENDA")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long idCliente;
    @Column(nullable = false)
    private Long idProduto;
    @Column(nullable = false)
    private Integer quantidade;
    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal precoUnitario;
    @Column(nullable = false)
    private LocalDateTime dataVenda;

}

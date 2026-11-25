package br.edu.ucsal.ms_vendas.config;

import br.edu.ucsal.ms_vendas.model.Venda;
import br.edu.ucsal.ms_vendas.repository.VendaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Carga de vendas de demonstração, ativada apenas com o perfil "dev".
 *
 * As vendas são gravadas direto no banco, sem passar pelo fluxo de registro:
 * não consultam Clientes/Produtos e não dão baixa no Estoque. Servem apenas
 * para testar as consultas (GET). Os ids e preços correspondem aos dados de
 * demonstração carregados pelos microsserviços de Clientes e Produtos.
 */
@Configuration
@Profile("dev")
public class Config {

    @Order(1)
    @Bean
    CommandLineRunner carregaListaVendasMock(VendaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                LocalDateTime agora = LocalDateTime.now();

                List<Venda> vendas = List.of(
                        // Tony Stark comprou 1 Armadura Mark V
                        criarVenda(1L, 1L, 1, "8500000.00", agora.minusDays(10)),
                        // Natasha Romanov comprou 2 Escudos do Capitao America
                        criarVenda(2L, 2L, 2, "25000.00", agora.minusDays(7)),
                        // Stark Industries comprou 3 Mjolnir
                        criarVenda(3L, 3L, 3, "120000.00", agora.minusDays(3)),
                        // Natasha Romanov comprou 5 Lancas Teia do Homem-Aranha
                        criarVenda(2L, 6L, 5, "8500.00", agora.minusDays(1))
                );
                repository.saveAll(vendas);
            }
        };
    }

    private Venda criarVenda(Long idCliente, Long idProduto, Integer quantidade,
                             String precoUnitario, LocalDateTime dataVenda) {
        Venda venda = new Venda();
        venda.setIdCliente(idCliente);
        venda.setIdProduto(idProduto);
        venda.setQuantidade(quantidade);
        venda.setPrecoUnitario(new BigDecimal(precoUnitario));
        venda.setDataVenda(dataVenda);
        return venda;
    }
}

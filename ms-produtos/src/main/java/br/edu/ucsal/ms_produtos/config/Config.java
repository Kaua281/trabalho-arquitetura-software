package br.edu.ucsal.ms_produtos.config;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import br.edu.ucsal.ms_produtos.model.Produto;
import br.edu.ucsal.ms_produtos.repository.ProdutoRepository;

@Configuration
public class Config {

    @Order(1)
    @Bean
    CommandLineRunner carregaListaProdutosMock(ProdutoRepository repository) {
        return args -> {
            if (repository.count() == 0) {

                List<Produto> produtos = new ArrayList<>(List.of(
                        new Produto(
                                "Armadura Mark V",
                                "Armadura tecnologica portatil desenvolvida por Tony Stark.",
                                new BigDecimal("8500000.00")
                        ),
                        new Produto(
                                "Escudo do Capitao America",
                                "Escudo de vibranium utilizado pelo Capitao America.",
                                new BigDecimal("25000.00")
                        ),
                        new Produto(
                                "Mjolnir",
                                "Martelo do poderoso Deus Thor, capaz de controlar raios.",
                                new BigDecimal("120000.00")
                        ),        
                        new Produto(
                            "Olho de Agamotto",
                            "Artefato mistico utilizado pelo Doutor Estranho associado ao controle e manipulacao do tempo.",
                            new BigDecimal("45000000.00")
                        ),
                        new Produto(
                                "StormBreaker",
                                "Machado poderoso utilizado por Thor, capaz de canalizar energia cosmica e permitir viagens entre mundos.",
                                new BigDecimal("120000.00")
                        ),
                        new Produto(
                                "Lanca Teia do Homem-Aranha",
                                "Dispositivo utilizado por Peter Parker para disparar teias sinteticas e auxiliar em seus deslocamentos urbanos.",
                                new BigDecimal("8500.00")
                         ),
                        new Produto(
                                "Manopla do Infinito",
                                "Artefato supremo contendo as seis Joias do Infinito. Controle absoluto sobre espaco, tempo, realidade, poder, mente e alma.",
                                new BigDecimal("9999999999.99")
                        )
                ));
                produtos.forEach(repository::save);
            }
        };
    }
}

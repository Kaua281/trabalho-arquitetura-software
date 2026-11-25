package br.edu.ucsal.ms_vendas.client;

import br.edu.ucsal.ms_vendas.dto.integracao.EstoqueSaidaRequestDTO;
import br.edu.ucsal.ms_vendas.exceptions.EstoqueInsuficienteException;
import br.edu.ucsal.ms_vendas.exceptions.EstoqueNaoEncontradoException;
import br.edu.ucsal.ms_vendas.exceptions.ServicoIndisponivelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class EstoqueClient {
    private final RestClient restClient;
    public EstoqueClient(RestClient.Builder builder, @Value("${servicos.estoque.url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public void darBaixa(Long idProduto, Integer quantidade) {
        EstoqueSaidaRequestDTO dto = new EstoqueSaidaRequestDTO(idProduto, quantidade);
        try {
            restClient.post().uri("/saidas").contentType(MediaType.APPLICATION_JSON).body(dto).retrieve()
                    .onStatus(status -> status.isSameCodeAs(HttpStatus.CONFLICT), (request, response) -> {
                        throw new EstoqueInsuficienteException("Estoque insuficiente para o produto "+idProduto);
                    })
                    .onStatus(status -> status.isSameCodeAs(HttpStatus.NOT_FOUND), (request, response) -> {
                        throw new EstoqueNaoEncontradoException("Estoque não encontrado para o produto "+idProduto);
                    }).onStatus(HttpStatusCode::isError, (request, response) -> {
                        throw new ServicoIndisponivelException("Servico de estoque indisponivel");
                    })

                    .toBodilessEntity();
        } catch (ResourceAccessException e) {
            throw new ServicoIndisponivelException("Serviço de estoque indisponível");
        }
    }

    public void devolver(Long idProduto, Integer quantidade) {
        EstoqueSaidaRequestDTO dto = new EstoqueSaidaRequestDTO(idProduto, quantidade);
        try {
            restClient.post().uri("/entradas").contentType(MediaType.APPLICATION_JSON).body(dto).retrieve().toBodilessEntity();
        } catch (Exception e) {
            log.error("Falha ao devolver {} para o produto {}", quantidade, idProduto, e);
        }

    }
}

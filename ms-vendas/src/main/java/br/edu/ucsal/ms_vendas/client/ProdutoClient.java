package br.edu.ucsal.ms_vendas.client;

import br.edu.ucsal.ms_vendas.dto.integracao.ProdutoResponseDTO;
import br.edu.ucsal.ms_vendas.exceptions.ProdutoNaoEncontradoException;
import br.edu.ucsal.ms_vendas.exceptions.ServicoIndisponivelException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Component
public class ProdutoClient {
    private final RestClient restClient;
    public ProdutoClient(RestClient.Builder builder, @Value("${servicos.produtos.url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        try {
            ProdutoResponseDTO produto = restClient.get().uri("/{id}", id).retrieve()
                    .onStatus(status -> status.value() == 404, (request, response) -> {
                        throw new ProdutoNaoEncontradoException("Produto com id " + id + " não encontrado.");
                    })
                    .onStatus(HttpStatusCode::isError, (request, response) -> {
                        throw new ServicoIndisponivelException("Falha ao consultar o serviço de produtos");
                    })
                    .body(ProdutoResponseDTO.class);
            if(produto == null || produto.preco() == null || produto.preco().compareTo(BigDecimal.ZERO) <= 0){
                throw new ServicoIndisponivelException("Resposta inválida do serviço de produtos");
            }
            return produto;

        } catch (
                ResourceAccessException e) {
            throw new ServicoIndisponivelException("Serviço de produtos indisponível.");
        }
    }

}

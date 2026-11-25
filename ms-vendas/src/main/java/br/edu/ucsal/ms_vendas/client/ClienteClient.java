package br.edu.ucsal.ms_vendas.client;


import br.edu.ucsal.ms_vendas.dto.integracao.ClienteResponseDTO;
import br.edu.ucsal.ms_vendas.exceptions.ClienteNaoEncontradoException;
import br.edu.ucsal.ms_vendas.exceptions.ServicoIndisponivelException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Component
public class ClienteClient {
    private final RestClient restClient;
    public ClienteClient(RestClient.Builder builder, @Value("${servicos.clientes.url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public ClienteResponseDTO buscarPorId(Long idCliente){
        try {
            return restClient.get().uri("/{id}", idCliente).retrieve()
                    .onStatus(status -> status.value() == 404, (request, response) -> {
                        throw new ClienteNaoEncontradoException("Cliente com id "+ idCliente+" não encontrado.");
                    })
                    .onStatus(HttpStatusCode::isError, (request, response) ->  {
                        throw new ServicoIndisponivelException("Falha ao consultar o serviço de clientes");
                    })
                    .body(ClienteResponseDTO.class);

        } catch (ResourceAccessException e) {
            throw new ServicoIndisponivelException("Serviço de clientes indisponível.");
        }

    }

}

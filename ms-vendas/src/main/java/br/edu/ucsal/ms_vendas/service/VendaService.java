package br.edu.ucsal.ms_vendas.service;

import br.edu.ucsal.ms_vendas.client.ClienteClient;
import br.edu.ucsal.ms_vendas.client.EstoqueClient;
import br.edu.ucsal.ms_vendas.client.ProdutoClient;
import br.edu.ucsal.ms_vendas.dto.integracao.ProdutoResponseDTO;
import br.edu.ucsal.ms_vendas.dto.request.VendaRequestDTO;
import br.edu.ucsal.ms_vendas.dto.response.VendaResponseDTO;
import br.edu.ucsal.ms_vendas.exceptions.VendaNaoEncontradaException;
import br.edu.ucsal.ms_vendas.model.Venda;
import br.edu.ucsal.ms_vendas.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {
    private final VendaRepository vendaRepository;
    private final ProdutoClient produtoClient;
    private final EstoqueClient estoqueClient;
    private final ClienteClient clienteClient;

    public VendaResponseDTO registrar(VendaRequestDTO request) {
        clienteClient.buscarPorId(request.idCliente());
        ProdutoResponseDTO produto = produtoClient.buscarPorId(request.idProduto());
        estoqueClient.darBaixa(produto.id(), request.quantidade());
        Venda salva;
        try {
            Venda venda = new Venda();
            venda.setIdCliente(request.idCliente());
            venda.setIdProduto(request.idProduto());
            venda.setQuantidade(request.quantidade());
            venda.setPrecoUnitario(produto.preco());
            venda.setDataVenda(LocalDateTime.now());
            salva = vendaRepository.save(venda);
        } catch (Exception e) {
            estoqueClient.devolver(produto.id(), request.quantidade());
            throw e;
        }
        return converterParaDTO(salva);
    }

    public List<VendaResponseDTO> listar() {
        return vendaRepository.findAll().stream().map(this::converterParaDTO).toList();
    }

    public VendaResponseDTO buscarPorId(Long id) {
        Venda venda = vendaRepository.findById(id).orElseThrow(() -> new VendaNaoEncontradaException("Venda com id " + id + " não encontrada."));
        return converterParaDTO(venda);
    }

    private VendaResponseDTO converterParaDTO(Venda venda) {
        BigDecimal total = venda.getPrecoUnitario().multiply(BigDecimal.valueOf(venda.getQuantidade()));
        return new VendaResponseDTO(venda.getId(), venda.getIdCliente(), venda.getIdProduto(), venda.getQuantidade(), venda.getPrecoUnitario(), total);
    }
}

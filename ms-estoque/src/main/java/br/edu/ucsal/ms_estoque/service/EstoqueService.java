package br.edu.ucsal.ms_estoque.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ucsal.ms_estoque.dto.request.EstoqueRequestDTO;
import br.edu.ucsal.ms_estoque.dto.response.EstoqueResponseDTO;
import br.edu.ucsal.ms_estoque.dto.request.MovimentacaoRequestDTO;
import br.edu.ucsal.ms_estoque.exception.EstoqueDuplicadoException;
import br.edu.ucsal.ms_estoque.exception.EstoqueInsuficienteException;
import br.edu.ucsal.ms_estoque.exception.EstoqueNaoEncontradoException;
import br.edu.ucsal.ms_estoque.model.Estoque;
import br.edu.ucsal.ms_estoque.repository.EstoqueRepository;
import br.edu.ucsal.ms_estoque.util.EstoqueMapper;

@Service public class EstoqueService {
    
    private final EstoqueRepository repository;

    public EstoqueService(EstoqueRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<EstoqueResponseDTO> listar() {
        return repository.findAll().stream().map(EstoqueMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public EstoqueResponseDTO buscarPorId(Long id) {
        return EstoqueMapper.toResponse(obterPorId(id));
    }

    @Transactional(readOnly = true)
    public EstoqueResponseDTO buscarPorProdutoId(Long produtoId) {
        Estoque estoque = repository.findByProdutoId(produtoId).orElseThrow(
            () -> new EstoqueNaoEncontradoException("Estoque não encontrado para o produto " + produtoId));
        return EstoqueMapper.toResponse(estoque);
    }

    @Transactional
    public EstoqueResponseDTO criar(EstoqueRequestDTO dto) {
        if (repository.existsByProdutoId(dto.produtoId())) {
            throw new EstoqueDuplicadoException("Já existe estoque cadastrado para o produto " + dto.produtoId());
        }
        Estoque salvo = repository.save(EstoqueMapper.toEntity(dto));
        return EstoqueMapper.toResponse(salvo);
    }

    @Transactional
    public EstoqueResponseDTO atualizar(Long id, EstoqueRequestDTO dto) {
        Estoque estoque = obterPorId(id);

        boolean mudouProduto = !estoque.getProdutoId().equals(dto.produtoId());
        if (mudouProduto && repository.existsByProdutoId(dto.produtoId())) {
            throw new EstoqueDuplicadoException("Já existe estoque cadastrado para o produto " + dto.produtoId());
        }

        estoque.setProdutoId(dto.produtoId());
        estoque.setQuantidade(dto.quantidade());
        return EstoqueMapper.toResponse(repository.save(estoque));
    }

    @Transactional
    public void deletar(Long id) {
        Estoque estoque = obterPorId(id);
        repository.delete(estoque);
    }

    @Transactional
    public EstoqueResponseDTO registrarEntrada(MovimentacaoRequestDTO dto) {
        Estoque estoque = repository.buscarParaAtualizar(dto.produtoId())
                .orElseGet(() -> new Estoque(dto.produtoId(), 0));

        estoque.setQuantidade(estoque.getQuantidade() + dto.quantidade());
        return EstoqueMapper.toResponse(repository.save(estoque));
    }

    @Transactional
    public EstoqueResponseDTO registrarSaida(MovimentacaoRequestDTO dto) {
        Estoque estoque = repository.buscarParaAtualizar(dto.produtoId()).orElseThrow(
                () -> new EstoqueNaoEncontradoException("Estoque não encontrado para o produto " + dto.produtoId()));
        
        if (estoque.getQuantidade() < dto.quantidade()) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para o produto " + dto.produtoId()
                    + ": disponível" + estoque.getQuantidade() + ", solicitdo " + dto.quantidade());
        }

        estoque.setQuantidade(estoque.getQuantidade() - dto.quantidade());
        return EstoqueMapper.toResponse(repository.save(estoque));
    }

    private Estoque obterPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->  new EstoqueNaoEncontradoException("Estoque não encontrado com id " + id));
    }
}
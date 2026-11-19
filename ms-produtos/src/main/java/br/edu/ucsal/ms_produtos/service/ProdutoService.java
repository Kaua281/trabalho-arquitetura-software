package br.edu.ucsal.ms_produtos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ucsal.ms_produtos.dto.request.ProdutoRequestDTO;
import br.edu.ucsal.ms_produtos.dto.response.ProdutoResponseDTO;
import br.edu.ucsal.ms_produtos.model.Produto;
import br.edu.ucsal.ms_produtos.repository.ProdutoRepository;

@Service
public class ProdutoService {
	private final ProdutoRepository produtoRepository;

	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	public ProdutoResponseDTO cadastrar(ProdutoRequestDTO dados) {
		
		Produto produto = new Produto();
		produto.setNome(dados.nome());
		produto.setDescricao(dados.descricao());
		produto.setPreco(dados.preco());
		
		Produto produtoSalvo = produtoRepository.save(produto);
		
		return converterParaResponse(produtoSalvo);
	}
	
	public List<ProdutoResponseDTO> listar(){
		return produtoRepository.findAll().stream().map(this::converterParaResponse).toList();
		
	}
	
	public ProdutoResponseDTO buscarPorId(Long id) {
		
		Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
		
		return converterParaResponse(produto);
		
	}
	
	public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dados) {
		
		Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
		
		produto.setNome(dados.nome());
		produto.setDescricao(dados.descricao());
		produto.setPreco(dados.preco());
		
		Produto produtoAtualizado = produtoRepository.save(produto);
		
		return converterParaResponse(produtoAtualizado);
		
	}
	
	public void deletar(Long id) {
		if(!produtoRepository.existsById(id)) {
			throw new RuntimeException("Produto não encontrado");
		}
		
		produtoRepository.deleteById(id);
	}

	private ProdutoResponseDTO converterParaResponse(Produto produto) {
		return new ProdutoResponseDTO(
				produto.getId(),
				produto.getNome(),
				produto.getDescricao(),
				produto.getPreco()
				);
	}
	
	
	
}

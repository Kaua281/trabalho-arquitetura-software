package br.edu.ucsal.ms_produtos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ucsal.ms_produtos.dto.request.ProdutoRequestDTO;
import br.edu.ucsal.ms_produtos.dto.response.ProdutoResponseDTO;
import br.edu.ucsal.ms_produtos.service.ProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
	private final ProdutoService produtoService;

	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}
	
	
	@PostMapping
	public ResponseEntity<ProdutoResponseDTO> cadastrar(@RequestBody @Valid ProdutoRequestDTO dados) {
		ProdutoResponseDTO produto = produtoService.cadastrar(dados);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(produto);
	}
	
	@GetMapping
	public ResponseEntity<List<ProdutoResponseDTO>> listar(){
		List<ProdutoResponseDTO> produtos = produtoService.listar();
		return ResponseEntity.status(HttpStatus.OK).body(produtos);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
		ProdutoResponseDTO produto = produtoService.buscarPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body(produto);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ProdutoRequestDTO dados) {
		ProdutoResponseDTO produto = produtoService.atualizar(id, dados);
		return ResponseEntity.status(HttpStatus.OK).body(produto);

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		produtoService.deletar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
}

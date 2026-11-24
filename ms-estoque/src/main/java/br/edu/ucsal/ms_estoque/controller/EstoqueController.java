package br.edu.ucsal.ms_estoque.controller;

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

import br.edu.ucsal.ms_estoque.dto.request.EstoqueRequestDTO;
import br.edu.ucsal.ms_estoque.dto.EstoqueResponseDTO;
import br.edu.ucsal.ms_estoque.dto.request.MovimentacaoRequestDTO;
import br.edu.ucsal.ms_estoque.service.EstoqueService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService service;

    public EstoqueController(EstoqueService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EstoqueResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<EstoqueResponseDTO> buscarPorProdutoId(@PathVariable Long produtoId) {
        return ResponseEntity.ok(service.buscarPorProdutoId(produtoId));
    }

    @PostMapping
    public ResponseEntity<EstoqueResponseDTO> criar(@Valid @RequestBody EstoqueRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstoqueResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EstoqueRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/entradas")
    public ResponseEntity<EstoqueResponseDTO> entrada(@Valid @RequestBody MovimentacaoRequestDTO dto) {
        return ResponseEntity.ok(service.registrarEntrada(dto));
    }

    @PostMapping("/saidas")
    public ResponseEntity<EstoqueResponseDTO> saida(@Valid @RequestBody MovimentacaoRequestDTO  dto) {
        return ResponseEntity.ok(service.registrarSaida(dto));
    }
}
package br.edu.ucsal.ms_vendas.controller;

import br.edu.ucsal.ms_vendas.dto.request.VendaRequestDTO;
import br.edu.ucsal.ms_vendas.dto.response.VendaResponseDTO;
import br.edu.ucsal.ms_vendas.service.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaController {
    private final VendaService vendaService;

    @PostMapping
    public ResponseEntity<VendaResponseDTO> registrar(@RequestBody @Valid VendaRequestDTO request) {
        VendaResponseDTO vendaDTO = vendaService.registrar(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vendaDTO.id()).toUri();
        return ResponseEntity.created(uri).body(vendaDTO);
    }

    @GetMapping
    public ResponseEntity<List<VendaResponseDTO>> listar() {
        return ResponseEntity.ok(vendaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vendaService.buscarPorId(id));
    }
}

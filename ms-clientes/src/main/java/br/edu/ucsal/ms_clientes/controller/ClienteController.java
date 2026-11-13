package br.edu.ucsal.ms_clientes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ucsal.ms_clientes.dto.response.ClienteResponse;
import br.edu.ucsal.ms_clientes.model.Cliente;
import br.edu.ucsal.ms_clientes.service.ClienteService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	private final ClienteService service;
	
	
	@GetMapping
	public ResponseEntity<List<ClienteResponse>> listarTodos(){
		List<Cliente> clientesList = service.listarTodos();
		List<ClienteResponse> response = clientesList.stream()
				.map(ClienteResponse::converterParaDTO)
				.toList();
		
		return ResponseEntity.ok(response);
	}

}

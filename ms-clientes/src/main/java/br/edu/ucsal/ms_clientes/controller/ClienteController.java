package br.edu.ucsal.ms_clientes.controller;

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

import br.edu.ucsal.ms_clientes.dto.request.ClienteRequest;
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

	@GetMapping("/{id}")
	public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id){
		Cliente cliente = service.buscarPorId(id);
		ClienteResponse response = ClienteResponse.converterParaDTO(cliente);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> apagarPorId(@PathVariable Long id){
		service.apagarPorId(id);

		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<ClienteResponse> criarNovoCliente(@RequestBody ClienteRequest request){
		Cliente cliente = service.criarNovoCliente(request);

		ClienteResponse response = ClienteResponse.converterParaDTO(cliente);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClienteResponse> atualizarCliente(@PathVariable Long id, @RequestBody ClienteRequest request){
		Cliente cliente = service.atualizaCliente(id, request);

		ClienteResponse response = ClienteResponse.converterParaDTO(cliente);

		return ResponseEntity.ok(response);
	}
}

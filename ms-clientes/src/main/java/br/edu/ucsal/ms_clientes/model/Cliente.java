package br.edu.ucsal.ms_clientes.model;

import java.time.Instant;

import br.edu.ucsal.ms_clientes.dto.request.ClienteRequest;
import br.edu.ucsal.ms_clientes.util.TipoClienteEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "clientes")
public abstract class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(nullable = false)
	String email;
	
	@Column(name = "telefone_celular", nullable = true, length = 20)
	String telefone;
	
	@Enumerated(EnumType.STRING)
	@Column
	TipoClienteEnum tipoClienteEnum;
	
	@Column
	Instant dataCriado;

	public Cliente(String email, String telefone, TipoClienteEnum tipoClienteEnum, Instant dataCriado) {
		super();
		this.email = email;
		this.telefone = telefone;
		this.tipoClienteEnum = tipoClienteEnum;
		this.dataCriado = dataCriado;
	}
	
	public void atualizaDados(ClienteRequest request) {
		this.email = request.email();
		this.telefone = request.telefone();
		
		atualizaDadosPorTipo(request);
	}

	protected abstract void atualizaDadosPorTipo(ClienteRequest request);
	
	

}

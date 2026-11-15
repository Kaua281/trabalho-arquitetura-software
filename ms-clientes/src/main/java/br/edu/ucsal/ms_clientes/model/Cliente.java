package br.edu.ucsal.ms_clientes.model;

import java.time.Instant;

import br.edu.ucsal.ms_clientes.util.TipoCliente;
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
	TipoCliente tipoCliente;
	
	@Column
	Instant dataCriado;

	public Cliente(String email, String telefone, TipoCliente tipoCliente, Instant dataCriado) {
		super();
		this.email = email;
		this.telefone = telefone;
		this.tipoCliente = tipoCliente;
		this.dataCriado = dataCriado;
	}
	
	

}

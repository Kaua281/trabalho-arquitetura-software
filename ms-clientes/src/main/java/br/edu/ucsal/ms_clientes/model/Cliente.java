package br.edu.ucsal.ms_clientes.model;

import java.time.Instant;
import java.time.LocalDate;

import br.edu.ucsal.ms_clientes.util.TipoCliente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "clientes")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column
	String nome;
	
	@Column
	String email;
	
	
	@Column(name = "telefone_celular", nullable = true, length = 20)
	String telefone;
	
	@Column
	String nomeFantasia;
	
	@Enumerated(EnumType.STRING)
	@Column
	TipoCliente tipoCliente;
	
	@Column
	String idReceitaFederal;
	
	@Column
	LocalDate dataNascimento;
	
	@Column
	Instant dataCriado;

	public Cliente(String nome, String email, String telefone, String nomeFantasia, TipoCliente tipoCliente,
			String idReceitaFederal, LocalDate dataNascimento, Instant dataCriado) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.nomeFantasia = nomeFantasia;
		this.tipoCliente = tipoCliente;
		this.idReceitaFederal = idReceitaFederal;
		this.dataNascimento = dataNascimento;
		this.dataCriado = dataCriado;
	}
	
	
	
}

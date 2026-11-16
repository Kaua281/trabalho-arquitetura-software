package br.edu.ucsal.ms_clientes.model;

import java.time.Instant;
import java.time.LocalDate;

import br.edu.ucsal.ms_clientes.util.TipoClienteEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "clientes_id")
@Table (name = "clientes_pf")
public class ClientePF extends Cliente{	

	@Column(name = "nome_completo", nullable = false, length = 150)
	private String nomeCompleto;
	
	@Column(nullable = false, unique = true)
	private String CPF;
	
	@Column(name = "data_nasc")
	private LocalDate dataNascimento;

	public ClientePF(String email, String telefone, TipoClienteEnum tipo, Instant criadoEm,
			String nomeCompleto, String CPF, LocalDate dataNascimento) {
		super(email, telefone, tipo, criadoEm);
		this.nomeCompleto = nomeCompleto;
		this.CPF = CPF;
		this.dataNascimento = dataNascimento;
	}
	
	
	

}

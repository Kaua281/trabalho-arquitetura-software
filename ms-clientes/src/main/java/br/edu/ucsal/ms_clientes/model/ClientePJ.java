package br.edu.ucsal.ms_clientes.model;

import java.time.Instant;

import br.edu.ucsal.ms_clientes.util.TipoCliente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn(name = "clientes_id")
@Table (name ="clientes_pj")
@NoArgsConstructor
@Getter
@Setter
public class ClientePJ extends Cliente {
	
	@Column(name = "razao_social", nullable = false, length = 150)
	private String razaoSocial;
	
	@Column(name = "nome_fantasia", length = 150)
	private String nomeFantasia;
	
	@Column(nullable = false, unique = true, length = 18)
	private String CNPJ;
	
	@Column(name = "insc_estadual", nullable = true, length = 14)
	private String inscricaoEstadual;

	public ClientePJ(String email, String telefone, TipoCliente tipo, Instant criadoEm,
			String razaoSocial, String nomeFantasia, String CNPJ, String inscricaoEstadual) {
		super(email, telefone, tipo, criadoEm);
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.CNPJ = CNPJ;
		this.inscricaoEstadual = inscricaoEstadual;
	}
	

	
}

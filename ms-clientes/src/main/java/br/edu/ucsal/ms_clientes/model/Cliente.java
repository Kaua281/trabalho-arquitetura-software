package br.edu.ucsal.ms_clientes.model;

import java.time.LocalDate;

import br.edu.ucsal.ms_clientes.util.TipoCliente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
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
	String nomeFantasia;
	
	@Column
	TipoCliente tipoCliente;
	
	@Column
	String idReceitaFederal;
	
	@Column
	LocalDate dataNascimento;
	
}

package br.edu.ucsal.ms_clientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ucsal.ms_clientes.model.ClientePJ;

public interface ClientePJRepository extends JpaRepository<ClientePJ, Long>{
	boolean existsByCNPJ(String CNPJ);

}

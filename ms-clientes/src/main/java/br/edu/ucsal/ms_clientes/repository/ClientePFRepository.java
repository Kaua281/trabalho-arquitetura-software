package br.edu.ucsal.ms_clientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ucsal.ms_clientes.model.ClientePF;

public interface ClientePFRepository extends JpaRepository<ClientePF, Long> {
	boolean existsByCPF(String CPF);

}

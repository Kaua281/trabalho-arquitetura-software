package br.edu.ucsal.ms_clientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ucsal.ms_clientes.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}

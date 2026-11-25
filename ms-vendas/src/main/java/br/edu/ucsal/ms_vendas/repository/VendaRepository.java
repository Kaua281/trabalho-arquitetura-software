package br.edu.ucsal.ms_vendas.repository;

import br.edu.ucsal.ms_vendas.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {}

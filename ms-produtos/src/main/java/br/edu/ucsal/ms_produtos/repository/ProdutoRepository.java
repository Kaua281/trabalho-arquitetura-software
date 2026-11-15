package br.edu.ucsal.ms_produtos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ucsal.ms_produtos.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}

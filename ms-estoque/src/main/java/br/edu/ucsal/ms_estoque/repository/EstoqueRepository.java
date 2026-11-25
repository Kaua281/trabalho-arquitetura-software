package br.edu.ucsal.ms_estoque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ucsal.ms_estoque.model.Estoque;
import jakarta.persistence.LockModeType;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    
	Optional<Estoque> findByProdutoId(Long produtoId);

    boolean existsByProdutoId(Long produtoId);

    //trava a linha no banco enquanto a transação não terminar.
    //Evita que duas baixas simultaneas de um mesmo produto.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select e from Estoque e Where e.produtoId = :produtoId")
    Optional<Estoque> buscarParaAtualizar(@Param("produtoId") Long produtoId);
}
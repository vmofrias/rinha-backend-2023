package com.ldsk.rinhabackend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ldsk.rinhabackend.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID>{

	@Query(nativeQuery = true,
            value = "select p.* from pessoa p " +
                    "where p.apelido like %:term% or " +
                    "p.nome like %:term% or " +
                    "p.nascimento like %:term% or " +
                    "p.stack like %:term%")
    List<Pessoa> findAllByTerm(@Param("term") String term, Pageable pageable);

    Optional<Pessoa> findByApelido(String apelido);
	
}

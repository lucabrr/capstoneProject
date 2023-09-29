package com.ThunderGym.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ThunderGym.classes.Cliente;

@Repository
public interface IclientePageable extends PagingAndSortingRepository<Cliente, Long> {
	@Query("SELECT c FROM Cliente c WHERE LOWER(c.nomeCompleto) LIKE LOWER(CONCAT('%', :nomeCompleto, '%'))")
    Page<Cliente> cercaPerNomeCompletoSimile(String nomeCompleto, Pageable pageable);
	@Query("SELECT c FROM Cliente c WHERE (c.dataUltimoIngresso IS NULL OR c.dataUltimoIngresso < :date)")
	Page<Cliente> findUsersByNonCurrentMonthEntryDate(@Param("date") LocalDate startDate,Pageable page);
	@Query("SELECT c FROM Cliente c WHERE (c.ingresso <= 5)")
	Page<Cliente> findClientiInScandenza(Pageable page);
}

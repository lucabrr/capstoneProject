package com.ThunderGym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ThunderGym.classes.Cliente;
@Repository
public interface ICliente extends CrudRepository<Cliente, Long>{

	@Query("SELECT c FROM Cliente c WHERE EXTRACT(YEAR FROM c.dataIscrizione) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(MONTH FROM c.dataIscrizione) = EXTRACT(MONTH FROM CURRENT_DATE) - 1")
	List<Cliente> findUsersByLastMonthRegistrationDateMinus1();
	
	@Query("SELECT c FROM Cliente c WHERE EXTRACT(YEAR FROM c.dataIscrizione) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(MONTH FROM c.dataIscrizione) = EXTRACT(MONTH FROM CURRENT_DATE)")
	List<Cliente> findUsersByLastMonthRegistrationDate();
	
	@Query("SELECT c FROM Cliente c WHERE c.ingresso > 0")
	List<Cliente> findValidCliente();
	
	@Query("SELECT c FROM Cliente c WHERE EXTRACT(YEAR FROM c.dataUltimoIngresso) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(MONTH FROM c.dataUltimoIngresso) = EXTRACT(MONTH FROM CURRENT_DATE)")
	List<Cliente> findUsersByLastMonthEntryDate();
	
	@Query("SELECT c FROM Cliente c WHERE (EXTRACT(YEAR FROM c.dataUltimoIngresso) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(MONTH FROM c.dataUltimoIngresso) <> EXTRACT(MONTH FROM CURRENT_DATE)) OR c.dataUltimoIngresso IS NULL")
	List<Cliente> findUsersByNonCurrentMonthEntryDate();
	}
	
	

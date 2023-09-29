package com.ThunderGym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.ThunderGym.classes.Fattura;

public interface IFattura extends CrudRepository<Fattura, Long>{
	@Query("SELECT SUM(f.importo) FROM Fattura f WHERE EXTRACT(MONTH FROM f.dataFattura) = EXTRACT(MONTH FROM CURRENT_DATE) AND EXTRACT(YEAR FROM f.dataFattura) = EXTRACT(YEAR FROM CURRENT_DATE)")
	Double findFatturaOfLastMonth();
	
	 @Query("SELECT SUM(f.importo) FROM Fattura f WHERE EXTRACT(MONTH FROM f.dataFattura) = EXTRACT(MONTH FROM CURRENT_DATE)-1 AND EXTRACT(YEAR FROM f.dataFattura) = EXTRACT(YEAR FROM CURRENT_DATE)")
	Double findImportoOfLastMonthMinus1();
}

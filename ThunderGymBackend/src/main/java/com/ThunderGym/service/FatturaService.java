package com.ThunderGym.service;

import java.time.LocalDate;
import java.util.HashMap;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.ThunderGym.classes.Cliente;
import com.ThunderGym.classes.Fattura;
import com.ThunderGym.repository.IFattura;
import com.github.javafaker.Faker;
@Service
public class FatturaService {
	@Autowired ObjectProvider<Fattura> fatturaProvider;
	@Autowired IFattura fatturaDao;
	@Value("${prezzo}") private double prezzo;
	Faker fk = new Faker();
	
	public Fattura creaFatturaMenoMese(Cliente _cliente){
		Fattura f = fatturaProvider.getObject();
		f= f.builder()
				.codiceFattura(fk.code().isbn10())
				.cliente(_cliente)
				.importo(_cliente.getIngresso() * prezzo)
				.dataFattura(LocalDate.now().minusMonths(1))
				.build();
		return f;
	}
	
	public Fattura creaFatturaManuale(Cliente _cliente, int _ingressi){
		Fattura f = fatturaProvider.getObject();
		f= f.builder()
				.codiceFattura(fk.code().isbn10())
				.cliente(_cliente)
				.importo(_ingressi * prezzo)
				.dataFattura(LocalDate.now())
				.build();
		return f;
	}
	public Fattura creaFattura(Cliente _cliente){
		Fattura f = fatturaProvider.getObject();
		f= f.builder()
				.codiceFattura(fk.code().isbn10())
				.cliente(_cliente)
				.importo(_cliente.getIngresso() * prezzo)
				.dataFattura(LocalDate.now())
				.build();
		return f;
	}
	public void salvaFattura(Fattura f) {
		try {
			fatturaDao.save(f);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public Double importoMeseCorrente() {
		Double importo = null;
		try {
			importo = fatturaDao.findFatturaOfLastMonth();
			return importo;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}return importo;
	}
	
	public Double importoMeseScorso() {
		Double importo = null;
		try {
			importo = fatturaDao.findImportoOfLastMonthMinus1();
			return importo;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}return importo;
	}
	public HashMap<String, Double> importiTotali(){
		HashMap<String,Double> listaDegliImporti = new HashMap<String, Double>();
		listaDegliImporti.put("importoMeseCorrente", importoMeseCorrente());
		listaDegliImporti.put("importoMeseScorso",  importoMeseScorso());
		return listaDegliImporti;
	}
	 
}

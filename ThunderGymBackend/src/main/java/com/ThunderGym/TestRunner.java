package com.ThunderGym;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ThunderGym.classes.Cliente;
import com.ThunderGym.classes.Fattura;
import com.ThunderGym.service.ClienteService;
import com.ThunderGym.service.FatturaService;

@Component
public class TestRunner  implements CommandLineRunner{
@Autowired ClienteService cs;
@Autowired FatturaService fs;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		for(int i = 0; i < 18; i++) {
		
			Cliente c = cs.creaClienteJF("y");
			//System.out.println(c.toString());
			cs.salvaCliente(c);	
			Fattura f = fs.creaFattura(c);
			fs.salvaFattura(f);
		}
		for(int i = 0; i < 22; i++) {
			
			Cliente c = cs.creaClienteJF("x");
			//System.out.println(c.toString());
			cs.salvaCliente(c);	
			Fattura f = fs.creaFatturaMenoMese(c);
			fs.salvaFattura(f);
		}
		
		
		System.out.println(fs.importoMeseCorrente());
		System.out.println(fs.importoMeseScorso());
		
	}

}

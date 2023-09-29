package com.ThunderGym.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ThunderGym.classes.Cliente;
import com.ThunderGym.classes.Fattura;
import com.ThunderGym.repository.ICliente;
import com.ThunderGym.repository.IFattura;
import com.ThunderGym.repository.IclientePageable;
import com.github.javafaker.Faker;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClienteService {
	
	@Autowired ObjectProvider<Cliente> clienteProvider;
	@Autowired ICliente clienteDao;
	@Autowired IclientePageable clientePageableDTO;
	
	Random random = new Random();
	Faker fk = new Faker();
	
	public Cliente creaCliente(String _nome, String _cognome, LocalDate _dataDiNascita, String _email, String _numeroTelefono, LocalDate _dataIscrizione, int _ingresso ,LocalDate _dataUltimoIngresso) {
	    Cliente c = new Cliente();
	    c.setNome(_nome);
	    c.setCognome(_cognome);
	    c.setDataDiNascita(_dataDiNascita);
	    c.setEmail(_email);
	    c.setNumeroTelefono(_numeroTelefono);
	    c.setDataIscrizione(_dataIscrizione);
	    c.setIngresso(_ingresso);
	    c.setDataUltimoIngresso(_dataUltimoIngresso);
	    c.setNomeCompleto(c.getNome()+c.getCognome());
	    return c;
	}

	
	public Cliente creaClienteJF(String x){
		Cliente c = clienteProvider.getObject();
		c.setNome(fk.name().firstName());
		c.setCognome(fk.name().lastName());
		c.setDataDiNascita(fk.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		c.setEmail(fk.internet().emailAddress());
		c.setNumeroTelefono(fk.phoneNumber().phoneNumber());
		c.setDataIscrizione(LocalDate.now());
		c.setDataUltimoIngresso(LocalDate.now());
		if(x.equals("x")) {c.setDataIscrizione(LocalDate.now().minusMonths(1));c.setDataUltimoIngresso(LocalDate.now().minusMonths(1));}
		c.setIngresso(random.nextInt(20) + 1);
		c.setNomeCompleto(c.getNome()+c.getCognome());
		
		return c;
		
	}
	public Cliente salvaCliente (Cliente c){
		Cliente cliente = new Cliente();
		try {
			cliente = clienteDao.save(c);
			return cliente;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} return cliente;
	};
	
	public Cliente trovaDaId(long id) {
		Cliente c = new Cliente();
		try {
		Optional<Cliente> cliente=	clienteDao.findById(id);
		if(cliente.isPresent()) {
			 c = (Cliente) cliente.get();
			 return c;
		}else { throw new EntityNotFoundException("Cliente non trovato per l'ID");}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return c;
		
	}
	public List<Cliente> allCLienti(){
		List<Cliente> clienti = new ArrayList<Cliente>();
		try {
			return clienti =  (List<Cliente>) clienteDao.findAll();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}return clienti;
	}
	public List<Cliente> clientiMesePrecedente(){
		List<Cliente> clienti = new ArrayList<Cliente>();
		try {
			return clienti =  (List<Cliente>) clienteDao.findUsersByLastMonthRegistrationDateMinus1();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}return clienti;
	}
	public List<Cliente> clientiMeseCorrente (){
		List<Cliente> clienti = new ArrayList<Cliente>();
		try {
			return clienti =  (List<Cliente>) clienteDao.findUsersByLastMonthRegistrationDate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}return clienti;
	}
	public HashMap<String,Integer> iscrittiMesePassatoECorrente(){
		 HashMap<String,Integer> listaIscritti = new HashMap<String, Integer>();
		 listaIscritti.put("iscrittiMeseCorrente", clientiMeseCorrente().size());
		 listaIscritti.put("iscrittiMeseScorso", clientiMesePrecedente().size());
		 return listaIscritti;
		
	}


	public List <Cliente> clientiValidi(){
		List<Cliente> clienti = new ArrayList<Cliente>();
		try {
			return clienti = clienteDao.findValidCliente();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return clienti;
	}
	public List <Cliente> clientiAttivi(){
		List<Cliente> clienti = new ArrayList<Cliente>();
		try {
			return clienti = clienteDao.findUsersByLastMonthEntryDate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return clienti;
	}
	
	public List <Cliente> clientiInattivi(){
		List<Cliente> clienti = new ArrayList<Cliente>();
		try {
			return clienti = clienteDao.findUsersByNonCurrentMonthEntryDate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return clienti;
		
	}
	public Page <Cliente> clientiInattiviPageable(Pageable page){
		
		try {
			LocalDate date = LocalDate.now().minusDays(30);
			return  clientePageableDTO.findUsersByNonCurrentMonthEntryDate(date,page);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw new RuntimeException("Errore");
		}
		
}
	public Page <Cliente> clienteNomeSimile(String nome ,Pageable page){
		try {
			return  clientePageableDTO.cercaPerNomeCompletoSimile(nome, page);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Errore");
		}
		
	}
	
	public Page <Cliente> ClienteInScandenza(Pageable page){
		try {
			return clientePageableDTO.findClientiInScandenza(page);
		} catch (Exception e) {
			throw new RuntimeException("Errore");
		}
	}
	
}

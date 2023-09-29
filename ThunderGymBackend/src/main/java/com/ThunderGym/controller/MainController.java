package com.ThunderGym.controller;

import java.awt.Dimension;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ThunderGym.classes.Cliente;
import com.ThunderGym.classes.Fattura;
import com.ThunderGym.service.ClienteService;
import com.ThunderGym.service.FatturaService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/dashboard")
public class MainController {
	
	@Autowired ClienteService cs;
	@Autowired FatturaService fs;

	@GetMapping("/allClienti")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Cliente>> getAllClienti(){
		return new ResponseEntity<List<Cliente>>(cs.allCLienti(),HttpStatus.ACCEPTED);
	}
	@GetMapping("/clienteId/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Cliente> getById(@PathVariable Long id){
		return new ResponseEntity<Cliente>(cs.trovaDaId(id),HttpStatus.ACCEPTED);
	}
	@GetMapping("/nClienti")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Integer> getNumberOfAllClienti(){
		return new ResponseEntity<Integer>(cs.allCLienti().size(),HttpStatus.ACCEPTED);
	}
	@GetMapping("/validClienti")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Integer> getNumberOfValidClienti(){
		return new ResponseEntity<Integer>(cs.clientiValidi().size(),HttpStatus.ACCEPTED);
	}
	@GetMapping("/activeClienti")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Integer> getNuberOfActiveClienti(){
		return new ResponseEntity<Integer>(cs.clientiAttivi().size(),HttpStatus.ACCEPTED);
	}
	@GetMapping("/inactiveClienti")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Cliente>> getNuberOfInactiveClienti(){
		return new ResponseEntity<List<Cliente>>(cs.clientiInattivi(),HttpStatus.ACCEPTED);
	}
	@PostMapping("/inactiveClientiPageable")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<Cliente>> getNuberOfInactiveClientiPageable(Pageable page){
		return new ResponseEntity<Page<Cliente>>(cs.clientiInattiviPageable(page),HttpStatus.ACCEPTED);
	}
	@GetMapping("/importi")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HashMap<String,Double>> getImporti(){
		return new ResponseEntity<HashMap<String,Double>>(fs.importiTotali(),HttpStatus.ACCEPTED);
	}
	@GetMapping("/iscritti")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HashMap<String,Integer>> getIscritti(){
		return new ResponseEntity<HashMap<String,Integer>>(cs.iscrittiMesePassatoECorrente(),HttpStatus.ACCEPTED);
	}
	@PostMapping("/findCliente")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<Cliente>> findCliente(Pageable page, @RequestBody Cliente cliente){
		
		return new ResponseEntity<Page<Cliente>>(cs.clienteNomeSimile(cliente.getNome(),page),HttpStatus.ACCEPTED);
	}
	@PostMapping("/clienteInScadenza")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<Cliente>> findClienteInScandenza(Pageable page){
		return new ResponseEntity<Page<Cliente>>(cs.ClienteInScandenza(page),HttpStatus.ACCEPTED);
	}
	@PostMapping("/newCliente")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> creaCliente(@RequestBody Cliente cliente){
		try {
			cliente.setDataIscrizione(LocalDate.now());
			cliente.setDataUltimoIngresso(LocalDate.now());
			cliente.setNomeCompleto(cliente.getNome()+cliente.getCognome());
			
			cs.salvaCliente(cliente);
			Fattura f = fs.creaFattura(cliente);
			fs.salvaFattura(f);
			String message = "Cliente " + cliente.getNome()+ " " + cliente.getCognome()+ " " +"creato";
			return new ResponseEntity<String>(message,HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
					
		}
	}
	@PostMapping("/modificaClient")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> findCliente(@RequestBody Cliente cliente){
		try {
			
			Cliente c = cs.salvaCliente(cliente);
			
			return new ResponseEntity<Cliente>(c,HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	@PatchMapping("/rinnovaIngresso/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?>rinnovaIngressoCliente(@PathVariable long id, @RequestBody Cliente clienteIngresso){
		try {
			Cliente cliente = cs.trovaDaId(id);
	
			cliente.setIngresso(clienteIngresso.getIngresso()+cliente.getIngresso());
			Cliente clienteDaTornare = cs.salvaCliente(cliente);
			Fattura f = fs.creaFatturaManuale(cliente, clienteIngresso.getIngresso());
			fs.salvaFattura(f);
			return new ResponseEntity<Cliente>(clienteDaTornare, HttpStatus.ACCEPTED);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
	}
	
 }

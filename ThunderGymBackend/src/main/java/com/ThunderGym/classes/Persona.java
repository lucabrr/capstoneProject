package com.ThunderGym.classes;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@MappedSuperclass


public abstract class Persona {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String nome;
	String cognome;
	String nomeCompleto;
	LocalDate dataDiNascita;
	String email;
	String numeroTelefono;
	
	


}

package com.ThunderGym.classes;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Fattura {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	@Column(nullable = false, unique = true)
	String codiceFattura;
	@ManyToOne
	Cliente cliente;
	@Column(nullable = false)
	Double importo;
	@Column(nullable = false)
	LocalDate dataFattura;
	
	

}

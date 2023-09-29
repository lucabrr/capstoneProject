package com.ThunderGym.classes;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)

@Entity
public class Cliente extends Persona {
	
	LocalDate dataIscrizione;
	LocalDate dataUltimoIngresso;
	int ingresso;
	
	
}

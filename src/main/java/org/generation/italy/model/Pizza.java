package org.generation.italy.model;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Pizza {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@NotEmpty(message="Inserire un nome!")
	private String nome;
	@Lob
	private String descrizione;
	@NotNull(message="Inserire un prezzo!")	
	private BigInteger prezzo;
	
	@ManyToMany
	private List<Ingredienti> ingredienti;
	
	public Integer getId() {
		return id;
	}
	public List<Ingredienti> getIngredienti() {
		return ingredienti;
	}
	public void setIngredienti(List<Ingredienti> ingredienti) {
		this.ingredienti = ingredienti;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public BigInteger getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(BigInteger prezzo) {
		this.prezzo = prezzo;
	}
	public String ingredientiString(){
		
		ingredienti.stream().filter(i -> i.getNome().startsWith("A")).collect(Collectors.toList());
		
		List<String> nomiIngredienti = ingredienti.stream().map(i -> i.getNome()).collect(Collectors.toList());
		
		return String.join(", ", nomiIngredienti);
		
		/*String ingredientiString = " ";
		int a = 0;
		for(Ingredienti i : ingredienti) {		
			if(ingredientiString.isBlank()) {
				ingredientiString = i.getNome();
				a++;
				if(a < ingredienti.size()) {
					ingredientiString += ", ";
				}	
			}else {
				ingredientiString += i.getNome();
				a++;
				if(a < ingredienti.size()) {
					ingredientiString += ", ";
				}	
			}			
		}
		return ingredientiString;*/
	}
}

package tn.esprit.eventsproject.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Participant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idPart;
    String nom;
    String prenom;
    @Enumerated(EnumType.STRING)
    Tache tache;
    @ManyToMany
    Set<Event> events;
	public int getIdPart() {
		return idPart;
	}
	public void setIdPart(int idPart) {
		this.idPart = idPart;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Tache getTache() {
		return tache;
	}
	public void setTache(Tache tache) {
		this.tache = tache;
	}
	public Set<Event> getEvents() {
		return events;
	}
	public void setEvents(Set<Event> events) {
		this.events = events;
	}
	public Participant(int idPart, String nom, String prenom, Tache tache, Set<Event> events) {
		super();
		this.idPart = idPart;
		this.nom = nom;
		this.prenom = prenom;
		this.tache = tache;
		this.events = events;
	}
	
    
    

}

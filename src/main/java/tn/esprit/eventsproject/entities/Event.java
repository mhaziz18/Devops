package tn.esprit.eventsproject.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idEvent;
    String description;
    LocalDate dateDebut;
    LocalDate dateFin;
    float cout;
    @ManyToMany(mappedBy = "events")
    Set<Participant> participants;
    @OneToMany(fetch = FetchType.EAGER)
    Set<Logistics> logistics;
	public Object getLogistics() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setLogistics(Set<Logistics> logisticsSet) {
		// TODO Auto-generated method stub
		
	}
	public Set<Participant> getParticipants() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}

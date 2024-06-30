import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.services.IEventServices;
import tn.esprit.eventsproject.controllers.EventRestController;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class EventRestControllerTest {

    @Mock
    private IEventServices eventServices;

    @InjectMocks
    private EventRestController eventRestController;

    private Event event;
    private Participant participant;
    private Logistics logistics;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        event = new Event();
        participant = new Participant();
        logistics = new Logistics();
    }

    @Test
    public void testAddParticipant() {
        when(eventServices.addParticipant(participant)).thenReturn(participant);
        Participant result = eventRestController.addParticipant(participant);
        assertNotNull(result);
    }

    @Test
    public void testAddEventPart() {
        when(eventServices.addAffectEvenParticipant(event, 1)).thenReturn(event);
        Event result = eventRestController.addEventPart(event, 1);
        assertNotNull(result);
    }

    @Test
    public void testAddEvent() {
        when(eventServices.addAffectEvenParticipant(event)).thenReturn(event);
        Event result = eventRestController.addEvent(event);
        assertNotNull(result);
    }

    @Test
    public void testAddAffectLog() {
        when(eventServices.addAffectLog(logistics, "description")).thenReturn(logistics);
        Logistics result = eventRestController.addAffectLog(logistics, "description");
        assertNotNull(result);
    }

    @Test
    public void testGetLogistiquesDates() {
        List<Logistics> logisticsList = new ArrayList<>();
        logisticsList.add(logistics);
        when(eventServices.getLogisticsDates(LocalDate.now(), LocalDate.now().plusDays(1))).thenReturn(logisticsList);
        List<Logistics> result = eventRestController.getLogistiquesDates(LocalDate.now(), LocalDate.now().plusDays(1));
        assertFalse(result.isEmpty());
    }
}
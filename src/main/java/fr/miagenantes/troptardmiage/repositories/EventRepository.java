package fr.miagenantes.troptardmiage.repositories;

import java.util.Collection;

import com.googlecode.objectify.ObjectifyService;

import static com.googlecode.objectify.ObjectifyService.ofy;
import fr.miagenantes.troptardmiage.models.Event;

public class EventRepository {
    private static EventRepository eventRepository = null;

    static {
        ObjectifyService.register(Event.class);
    }

    private EventRepository() {
    }

    public static synchronized EventRepository getInstance() {
        if (null == eventRepository) {
            eventRepository = new EventRepository();
        }
        return eventRepository;
    }
    
    public Collection<Event> list() {
    	return ofy().load().type(Event.class).list();
    }
    
    public Event get(String eventId) {
    	return ofy().load().type(Event.class).id(eventId).now();
    }

	public Event create(Event event) {
		Event existingEvent = get(event.getId());
    	
    	if(existingEvent == null) {
    		ofy().save().entity(event).now();
    	}
    	return event;
	}
}

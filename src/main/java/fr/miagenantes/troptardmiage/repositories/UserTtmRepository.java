package fr.miagenantes.troptardmiage.repositories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.ObjectifyService;

import static com.googlecode.objectify.ObjectifyService.ofy;
import fr.miagenantes.troptardmiage.models.Event;
import fr.miagenantes.troptardmiage.models.Theme;
import fr.miagenantes.troptardmiage.models.UserTtm;
import fr.miagenantes.troptardmiage.utils.ValueComparator;


public class UserTtmRepository {
	private static UserTtmRepository userTtmRepository = null;

    static {
        ObjectifyService.register(UserTtm.class);
    }

    private UserTtmRepository() {
    }

    public static synchronized UserTtmRepository getInstance() {
        if (null == userTtmRepository) {
            userTtmRepository = new UserTtmRepository();
        }
        return userTtmRepository;
    }

    public UserTtm get(String id) {
        return ofy().load().type(UserTtm.class).id(id).now();
    }
    
    public static List<UserTtm> users() {
        return ofy().load().type(UserTtm.class).list();
    }


    public Map<String, Double> losers() {
        List<UserTtm> userTtms = ofy().load().type(UserTtm.class).list();
        Map<String, Boolean> events;
        Map<String, Double> classement = new HashMap<String, Double>();//<userID, ratio>
        Comparator<String> comparator = new ValueComparator<String, Double>(classement);
        Map<String, Double> classementTrie = new TreeMap<String, Double>(comparator);
        Integer missedEvt;
        Integer totalEvt;

        //comptage du nombre d'événements ratés
        for(UserTtm user : userTtms) {
        	events = user.getSubscriptions();
        	missedEvt = 0;
        	totalEvt = events.size();
        	for(Map.Entry<String, Boolean> event : events.entrySet()) {
        		if(!event.getValue()) {
        			missedEvt++;
        		}
        	}
        	classement.put(user.getUser().getNickname(), ((double) missedEvt/(double) totalEvt));
        }
        classementTrie.putAll(classement);
        
        return classementTrie;
    }

    public UserTtm create(UserTtm userTtm) {
    	UserTtm existingUser = get(userTtm.getId());
    	
    	if(existingUser == null) {
    		ofy().save().entity(userTtm).now();
    	}
        return userTtm;
    }

    public void remove(String id) {
        if (id == null) {
            return;
        }
        ofy().delete().type(UserTtm.class).id(id).now();
    }
    
    public UserTtm addTheme(String userId, Theme potentialTheme) {
    	UserTtm userTtm = get(userId);
    	//we create the Entity Theme (if it already exists, we get the existing Entity)
    	Theme theme = ThemeRepository.getInstance().create(potentialTheme);
    	//assign the theme ID to the user's themes list
    	userTtm.getThemes().add(theme.getId());
    	//assign the user ID to the theme's users list
    	theme.getUsers().add(userId);
    	
    	ofy().save().entity(theme).now();
    	ofy().save().entity(userTtm).now();
    	
    	return userTtm;
    }
    
    public Collection<Theme> getThemes(String userId) {
    	UserTtm user = get(userId);
    	return ThemeRepository.getInstance().listByIds(user.getThemes());
    }

	public UserTtm subscribe(String userId, String eventId, String title,
			String themeId, String startDate, String endDate, String city,
			Float latitude, Float longitude) throws ParseException {
		UserTtm userTtm = get(userId);
		Theme theme = ThemeRepository.getInstance().get(themeId);
		//format received dd-MM-yy
		String pattern = "dd-MM-yy";
		Date start = new SimpleDateFormat(pattern).parse(startDate);
		Date end = new SimpleDateFormat(pattern).parse(endDate);
		Event evt = EventRepository.getInstance().create(new Event(eventId, title, theme, start, end, city, new GeoPt(latitude, longitude)));
		//save user's will to go to this event
		userTtm.getSubscriptions().put(evt.getId(), Boolean.FALSE);
		//save Event ID into the list of themes
		theme.getEvents().add(evt.getId());
		
		ofy().save().entity(userTtm).now();
		ofy().save().entity(theme).now();
		
		return userTtm;
	}
	
	public UserTtm unsubscribe(String userId, String eventId) {
		UserTtm userTtm = get(userId);
		Event evt = EventRepository.getInstance().get(eventId);
		userTtm.getSubscriptions().remove(evt.getId());
		
		ofy().save().entity(userTtm).now();
		
		return userTtm;
	}

    public UserTtm confirmEvent(String userId, String eventId) {
        UserTtm userTtm = get(userId);
        Event evt = EventRepository.getInstance().get(eventId);
        userTtm.confirmEvent(evt.getId());
        
        ofy().save().entity(userTtm).now();
        
        return userTtm;
    }


}

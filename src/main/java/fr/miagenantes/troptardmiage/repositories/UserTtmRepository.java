package fr.miagenantes.troptardmiage.repositories;

import java.util.Collection;
import java.util.List;

import com.googlecode.objectify.ObjectifyService;

import fr.miagenantes.troptardmiage.models.Theme;
import fr.miagenantes.troptardmiage.models.UserTtm;
import static com.googlecode.objectify.ObjectifyService.ofy;

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
    
    public List<UserTtm> list() {
        List<UserTtm> userTtms = ofy().load().type(UserTtm.class).list();
        return userTtms;
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
    	return userTtm;
    }
    
    public Collection<Theme> getThemes(String userId) {
    	UserTtm user = get(userId);
    	return ThemeRepository.getInstance().listByIds(user.getThemes());
    }
}

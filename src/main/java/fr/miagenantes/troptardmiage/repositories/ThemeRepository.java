package fr.miagenantes.troptardmiage.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.ObjectifyService;

import static com.googlecode.objectify.ObjectifyService.ofy;
import fr.miagenantes.troptardmiage.models.Theme;

public class ThemeRepository {
	private static ThemeRepository themeRepository = null;

    static {
        ObjectifyService.register(Theme.class);
    }

    private ThemeRepository() {
    }

    public static synchronized ThemeRepository getInstance() {
        if (null == themeRepository) {
        	themeRepository = new ThemeRepository();
        }
        return themeRepository;
    }
    
    public Theme get(String id) {
    	return ofy().load().type(Theme.class).id(id).now();
    }
    
    public List<Theme> list() {
    	return ofy().load().type(Theme.class).list();
    }
    
    public Collection<Theme> listByIds(Collection<String> ids) {
    	Map<String, Theme> themes = ofy().load().type(Theme.class).ids(ids);
    	return themes.values();
    }
    
    public Theme create(Theme theme) {
    	Theme existingTheme = get(theme.getId());
    	
    	if(existingTheme == null) {
    		ofy().save().entity(theme).now();
    	}
    	return theme;
    }
}

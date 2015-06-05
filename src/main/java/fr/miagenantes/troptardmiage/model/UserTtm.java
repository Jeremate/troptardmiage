package fr.miagenantes.troptardmiage.model;

import java.util.Map;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class UserTtm {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private User user;
	@Persistent
	private Map<Long, Boolean> subscriptions; //Map<Event ID, attended to the event>
	@Persistent
	private Set<Long> themes;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Map<Long, Boolean> getSubscriptions() {
		return subscriptions;
	}
	public void setSubscriptions(Map<Long, Boolean> subscriptions) {
		this.subscriptions = subscriptions;
	}
	public Set<Long> getThemes() {
		return themes;
	}
	public void setThemes(Set<Long> themes) {
		this.themes = themes;
	}	
}
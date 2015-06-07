package fr.miagenantes.troptardmiage.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Stringify;
import com.googlecode.objectify.stringifier.Stringifier;
import com.google.appengine.api.users.User;

class LongStringifier implements Stringifier<Long> {

	@Override
	public Long fromString(String str) {
		return new Long(str);
	}

	@Override
	public String toString(Long lg) {
		return lg.toString();
	}
	
}

@Entity
public class UserTtm {
	@Id
	private String id; // Google recommends to be the user id
	private User user;
	//Map<Event ID, true if attended to the event>
	@Load @Stringify(LongStringifier.class)
	private Map<Long, Boolean> subscriptions = new HashMap<Long, Boolean>();
	@Load
	private Set<Long> themes = new HashSet<Long>();

	//constructors
	public UserTtm() {} //must have no-arg constructor in Objectify
	public UserTtm(User user) {
		this.user = user;
		this.id = user.getUserId();
	}
	public UserTtm(User user, Set<Long> themes) {
		this.user = user;
		this.id = user.getUserId();
		this.themes = themes;
	}

	// getters and setters
	public String getId() {
		return id;
	}
	public User getUser() {
		return user;
	}
	public UserTtm setUser(User user) {
		this.user = user;
		return this;
	}
	public Map<Long, Boolean> getSubscriptions() {
		return subscriptions;
	}
	public UserTtm setSubscriptions(Map<Long, Boolean> subscriptions) {
		this.subscriptions = subscriptions;
		return this;
	}
	public Set<Long> getThemes() {
		return themes;
	}
	public UserTtm setThemes(Set<Long> themes) {
		this.themes = themes;
		return this;
	}
}
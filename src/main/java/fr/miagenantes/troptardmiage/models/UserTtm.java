package fr.miagenantes.troptardmiage.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;
import com.google.appengine.api.users.User;

@Entity
public class UserTtm {
	@Id
	private String id; // Google recommends to be the user id
	private User user;
	//Map<Event ID, true if attended to the event>
	@Load
	private Map<String, Boolean> subscriptions = new HashMap<String, Boolean>();
	@Load
	private Set<String> themes = new HashSet<String>();

	//constructors
	public UserTtm() {} //must have no-arg constructor in Objectify
	public UserTtm(User user) {
		this.user = user;
		this.id = user.getUserId();
	}
	public UserTtm(User user, Set<String> themes) {
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
	public Map<String, Boolean> getSubscriptions() {
		return subscriptions;
	}
	public UserTtm setSubscriptions(Map<String, Boolean> subscriptions) {
		this.subscriptions = subscriptions;
		return this;
	}
	public Set<String> getThemes() {
		return themes;
	}
	public UserTtm setThemes(Set<String> themes) {
		this.themes = themes;
		return this;
	}
}
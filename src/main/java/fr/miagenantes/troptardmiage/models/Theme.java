package fr.miagenantes.troptardmiage.models;

import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Theme {
	@Id
	private String id;
	private String name;
	private Set<String> events = new HashSet<String>();
	private Set<String> users = new HashSet<String>();;

	//constructors
	public Theme() {} //must have no-arg constructor in Objectify
	public Theme(String themeId, String name) {
		this.id = themeId;
		this.name = name;
	}
	public Theme(String themeId, String name,
			Set<String> events, Set<String> users) {
		this.id = themeId;
		this.name = name;
		this.events = events;
		this.users = users;
	}

	//getters and setters
	public String getId() {
		return id;
	}
	public Theme setId(String id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public Theme setName(String name) {
		this.name = name;
		return this;
	}
	public Set<String> getEvents() {
		return events;
	}
	public Theme setEvents(Set<String> events) {
		this.events = events;
		return this;
	}
	public Set<String> getUsers() {
		return users;
	}
	public Theme setUsers(Set<String> users) {
		this.users = users;
		return this;
	}

}

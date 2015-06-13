package fr.miagenantes.troptardmiage.models;

import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Theme {
	@Id
	private Long id;
	@Index
	private String themeId;
	private String name;
	private String icon;
	private Set<Long> events = new HashSet<Long>();
	private Set<String> users = new HashSet<String>();;

	//constructors
	public Theme() {} //must have no-arg constructor in Objectify
	public Theme(String themeId, String name, String icon) {
		this.themeId = themeId;
		this.name = name;
		this.icon = icon;
	}
	public Theme(Long id, String themeId, String name, String icon) {
		this.id = id;
		this.themeId = themeId;
		this.name = name;
		this.icon = icon;
	}
	public Theme(Long id, String themeId, String name, String icon,
			Set<Long> events, Set<String> users) {
		this.id = id;
		this.themeId = themeId;
		this.name = name;
		this.icon = icon;
		this.events = events;
		this.users = users;
	}

	//getters and setters
	public Long getId() {
		return id;
	}
	public Theme setId(Long id) {
		this.id = id;
		return this;
	}
	public String getThemeId() {
		return themeId;
	}
	public Theme setThemeId(String themeId) {
		this.themeId = themeId;
		return this;
	}
	public String getName() {
		return name;
	}
	public Theme setName(String name) {
		this.name = name;
		return this;
	}
	public String getIcon() {
		return icon;
	}
	public Theme setIcon(String icon) {
		this.icon = icon;
		return this;
	}
	public Set<Long> getEvents() {
		return events;
	}
	public Theme setEvents(Set<Long> events) {
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

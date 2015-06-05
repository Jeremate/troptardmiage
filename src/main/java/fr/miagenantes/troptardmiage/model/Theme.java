package fr.miagenantes.troptardmiage.model;

import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Theme {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long Id;
	@Persistent
	private String themeId;
	@Persistent
	private String name;
	@Persistent
	private String linkIcon;
	@Persistent
	private Set<Long> events;
	@Persistent
	private Set<Long> users;
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getThemeId() {
		return themeId;
	}
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkIcon() {
		return linkIcon;
	}
	public void setLinkIcon(String linkIcon) {
		this.linkIcon = linkIcon;
	}
	public Set<Long> getEvents() {
		return events;
	}
	public void setEvents(Set<Long> events) {
		this.events = events;
	}
	public Set<Long> getUsers() {
		return users;
	}
	public void setUsers(Set<Long> users) {
		this.users = users;
	}
	
}

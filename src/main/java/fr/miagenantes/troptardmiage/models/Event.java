package fr.miagenantes.troptardmiage.models;

import java.util.Date;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.google.appengine.api.datastore.GeoPt;

@Entity
public class Event {
	@Id
	private Long id;
	@Index
	private String eventId;
	private String title;
	@Load
	private Ref<Theme> theme;
	private Date startDate;
	private Date endDate;
	private String city;
	private GeoPt geoPt;//latitude, longitude

	//constructors
	public Event() {} //must have no-arg constructor in Objectify
	public Event(Long id, String eventId, String title, Theme theme,
			Date startDate, Date endDate, String city, GeoPt geoPt) {
		this.id = id;
		this.eventId = eventId;
		this.title = title;
		this.theme = Ref.create(theme);
		this.startDate = startDate;
		this.endDate = endDate;
		this.city = city;
		this.geoPt = geoPt;
	}

	//getters and setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Theme getTheme() {
		return theme.get();
	}
	public void setTheme(Theme theme) {
		this.theme = Ref.create(theme);
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public GeoPt getGeoPt() {
		return geoPt;
	}
	public void setGeoPt(GeoPt geoPt) {
		this.geoPt = geoPt;
	}

}

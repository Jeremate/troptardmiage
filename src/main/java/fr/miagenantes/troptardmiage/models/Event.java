package fr.miagenantes.troptardmiage.models;

import java.util.Date;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;
import com.google.appengine.api.datastore.GeoPt;

@Entity
public class Event {
	@Id
	private String id;
	private String title;
	@Load
	private Ref<Theme> theme;
	private Date startDate;
	private Date endDate;
	private String city;
	private GeoPt geoPt;//latitude, longitude

	//constructors
	public Event() {} //must have no-arg constructor in Objectify
	public Event(String eventId, String title, Theme theme,
			Date startDate, Date endDate, String city, GeoPt geoPt) {
		this.id = eventId;
		this.title = title;
		this.theme = Ref.create(theme);
		this.startDate = startDate;
		this.endDate = endDate;
		this.city = city;
		this.geoPt = geoPt;
	}

	//getters and setters
	public String getId() {
		return id;
	}
	public Event setId(String id) {
		this.id = id;
		return this;
	}
	public String getTitle() {
		return title;
	}
	public Event setTitle(String title) {
		this.title = title;
		return this;
	}
	public Theme getTheme() {
		return theme.get();
	}
	public Event setTheme(Theme theme) {
		this.theme = Ref.create(theme);
		return this;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Event setStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}
	public Date getEndDate() {
		return endDate;
	}
	public Event setEndDate(Date endDate) {
		this.endDate = endDate;
		return this;
	}
	public String getCity() {
		return city;
	}
	public Event setCity(String city) {
		this.city = city;
		return this;
	}
	public GeoPt getGeoPt() {
		return geoPt;
	}
	public Event setGeoPt(GeoPt geoPt) {
		this.geoPt = geoPt;
		return this;
	}

}

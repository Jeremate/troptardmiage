package fr.miagenantes.troptardmiage.entities;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.GeoPt;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Event {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long Id;
	@Persistent
	private String eventId;
	@Persistent
	private String title;
	@Persistent
	private Long theme;
	@Persistent
	private Date startDate;
	@Persistent
	private Date endDate;
	@Persistent
	private String city;
	@Persistent
	private GeoPt geoPt;//latitude, longitude
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
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
	public Long getTheme() {
		return theme;
	}
	public void setTheme(Long theme) {
		this.theme = theme;
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

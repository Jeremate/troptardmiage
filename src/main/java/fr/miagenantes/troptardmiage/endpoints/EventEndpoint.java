package fr.miagenantes.troptardmiage.endpoints;

import java.util.Collection;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import fr.miagenantes.troptardmiage.Constants;
import fr.miagenantes.troptardmiage.models.Event;
import fr.miagenantes.troptardmiage.repositories.EventRepository;

@Api(
	name = "troptardmiage",
	version = "v1",
	scopes = {Constants.EMAIL_SCOPE},
	clientIds = {Constants.WEB_CLIENT_ID}
)
public class EventEndpoint {

	private String authMessage = "Login is required";
	
	@ApiMethod(
		name = "events.list",
		path = "events",
		httpMethod = HttpMethod.GET
	)
	public Collection<Event> list(User user) throws OAuthRequestException {
		if(user == null) {
			throw new OAuthRequestException(authMessage);
		}
		return EventRepository.getInstance().list();
	}

	@ApiMethod(
		name = "events.get",
		path = "events/{eventId}",
		httpMethod = HttpMethod.GET
	)
	public Event get(User user, @Named("eventId") String eventId) throws OAuthRequestException {
		if(user == null) {
			throw new OAuthRequestException(authMessage);
		}
		return EventRepository.getInstance().getByEventId(eventId);
	}
}

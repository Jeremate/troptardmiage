package fr.miagenantes.troptardmiage.endpoints;

import java.text.ParseException;
import java.util.Collection;
import java.util.Set;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import fr.miagenantes.troptardmiage.Constants;
import fr.miagenantes.troptardmiage.models.Theme;
import fr.miagenantes.troptardmiage.models.UserTtm;
import fr.miagenantes.troptardmiage.repositories.UserTtmRepository;

@Api(
	name = "troptardmiage",
	version = "v1",
	scopes = {Constants.EMAIL_SCOPE},
	clientIds = {Constants.WEB_CLIENT_ID}
)
public class UserTtmEndpoint {

	private String authMessage = "Login is required";

	@ApiMethod(
		name = "users.losers",
		path = "users/losers",
		httpMethod = HttpMethod.GET
	)
	public Set<UserTtm> losers(User user) throws OAuthRequestException {
		if(user == null) {
			throw new OAuthRequestException(authMessage);
		}
		return UserTtmRepository.getInstance().losers();
	}

	@ApiMethod(
		name = "users.get",
		path = "users",
		httpMethod = HttpMethod.GET
	)
	public UserTtm get(User user) throws OAuthRequestException {
		if(user == null) {
			throw new OAuthRequestException(authMessage);
		}
		return UserTtmRepository.getInstance().get(user.getUserId());
	}

	@ApiMethod(
		name = "users.create",
		path = "users",
		httpMethod = HttpMethod.POST
	)
	public UserTtm create(User user) throws OAuthRequestException {
		if(user == null) {
			throw new OAuthRequestException(authMessage);
		}
		return UserTtmRepository.getInstance().create(new UserTtm(user));
	}

	@ApiMethod(
		name = "users.themes",
		path = "users/themes",
		httpMethod = HttpMethod.GET
	)
	public Collection<Theme> getThemes(User user) throws OAuthRequestException {
		if(user == null) {
			throw new OAuthRequestException(authMessage);
		}
		return UserTtmRepository.getInstance().getThemes(user.getUserId());
	}

	@ApiMethod(
		name = "users.addTheme",
		path = "users/themes",
		httpMethod = HttpMethod.POST
	)
	public UserTtm addTheme(User user, @Named("themeId") String themeId,
			@Named("name") String name, @Named("linkIcon") String linkIcon)
			throws OAuthRequestException {
		if (user == null) {
			throw new OAuthRequestException(authMessage);
		}
		return UserTtmRepository.getInstance()
				.addTheme(user.getUserId(), new Theme(themeId, name, linkIcon));
	}

	@ApiMethod(
		name = "users.subscribe",
		path = "users/events",
		httpMethod = HttpMethod.POST
	)
	public UserTtm subscribe(User user, @Named("eventId") String eventId,
			@Named("title") String title, @Named("themeId") String themeId,
			@Named("startDate") String startDate,
			@Named("endDate") String endDate, @Named("city") String city,
			@Named("latitude") Float latitude,
			@Named("longitude") Float longitude) throws OAuthRequestException, ParseException {
		if (user == null) {
			throw new OAuthRequestException(authMessage);
		}
		return UserTtmRepository.getInstance().subscribe(user.getUserId(),
				eventId, title, themeId, startDate, endDate, city, latitude,
				longitude);
	}

	@ApiMethod(
		name = "users.unsubscribe",
		path = "users/events/{eventId}",
		httpMethod = HttpMethod.DELETE
	)
	public UserTtm unsubscribe(User user, @Named("eventId") String eventId) throws OAuthRequestException {
		if (user == null) {
			throw new OAuthRequestException(authMessage);
		}
		return UserTtmRepository.getInstance().unsubscribe(user.getUserId(), eventId);
	}
}
package fr.miagenantes.troptardmiage.endpoints;

import java.util.Collection;
import java.util.List;

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
	public List<UserTtm> losers(User user) throws OAuthRequestException {
		if(user == null) {
			throw new OAuthRequestException(authMessage);
		}
		//TODO : make it return the real losers
		return UserTtmRepository.getInstance().list();
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
}

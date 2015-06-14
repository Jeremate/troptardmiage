package fr.miagenantes.troptardmiage.endpoints;

import java.util.List;

import fr.miagenantes.troptardmiage.Constants;
import fr.miagenantes.troptardmiage.models.Theme;
import fr.miagenantes.troptardmiage.repositories.ThemeRepository;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

@Api(
	name = "troptardmiage",
	version = "v1",
	scopes = {Constants.EMAIL_SCOPE},
	clientIds = {Constants.WEB_CLIENT_ID}
)
public class ThemeEndpoint {
	
	private String authMessage = "Login is required";
	
	@ApiMethod(
		name = "themes.list",
		path = "themes",
		httpMethod = HttpMethod.GET
	)
	public List<Theme> list(User user) throws OAuthRequestException {
		if(user == null) {
			throw new OAuthRequestException(authMessage);
		}
		return ThemeRepository.getInstance().list();
	}
	
	@ApiMethod(
		name = "themes.get",
		path = "themes/{themeId}",
		httpMethod = HttpMethod.GET
	)
	public Theme get(User user, @Named("themeId") String themeId) throws OAuthRequestException {
		if(user == null) {
			throw new OAuthRequestException(authMessage);
		}
		return ThemeRepository.getInstance().get(themeId);
	}
}

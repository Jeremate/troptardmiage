package fr.miagenantes.troptardmiage.entities;

import fr.miagenantes.troptardmiage.entities.PMF;
import fr.miagenantes.troptardmiage.model.Theme;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "troptardmiage", namespace = @ApiNamespace(ownerDomain = "miagenantes.fr", ownerName = "miagenantes.fr", packagePath = "troptardmiage.entities"))
@ApiClass(resource = "theme")
public class ThemeEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listTheme")
	public CollectionResponse<Theme> listTheme(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<Theme> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Theme.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<Theme>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Theme obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Theme> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getTheme")
	public Theme getTheme(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		Theme theme = null;
		try {
			theme = mgr.getObjectById(Theme.class, id);
		} finally {
			mgr.close();
		}
		return theme;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param theme the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertTheme")
	public Theme insertTheme(Theme theme) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (containsTheme(theme)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(theme);
		} finally {
			mgr.close();
		}
		return theme;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param theme the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateTheme")
	public Theme updateTheme(Theme theme) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsTheme(theme)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(theme);
		} finally {
			mgr.close();
		}
		return theme;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeTheme")
	public void removeTheme(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Theme theme = mgr.getObjectById(Theme.class, id);
			mgr.deletePersistent(theme);
		} finally {
			mgr.close();
		}
	}

	private boolean containsTheme(Theme theme) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(Theme.class, theme.getId());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}

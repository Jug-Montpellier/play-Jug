/**
 * 
 */
package controllers.api;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import play.mvc.Controller;
import play.mvc.Router;
import play.mvc.Router.Route;

/**
 * @author chamerling
 * 
 */
public class API extends Controller {

	public static final String URL = "/api/";

	public static final void index() {
		List<models.APIMethod> definitions = new ArrayList<models.APIMethod>();
		Method[] methods = V1.class.getDeclaredMethods();
		for (Method method : methods) {
			APIDefinition def = method.getAnnotation(APIDefinition.class);
			if (def != null) {
				models.APIMethod api = new models.APIMethod();
				api.description = def.description();
				api.method = def.method();
				api.url = request.getBase() + getPath(method);
				api.model = def.clazz();
				definitions.add(api);
			}
		}
		render(definitions);
	}

	private static final String getPath(Method method) {
		String path = "";
		for (Route route : Router.routes) {
			if (route.path.startsWith(URL)
					&& route.action.equals(method.getDeclaringClass()
							.getCanonicalName() + "." + method.getName())) {
				return route.path;
			}
		}
		return path;
	}

}

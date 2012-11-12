/**
 * 
 */
package controllers.api;

import java.util.ArrayList;
import java.util.List;

import models.Event;
import models.EventPartner;
import models.News;
import models.Speaker;
import models.YearPartner;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Router;
import play.mvc.Router.Route;
import utils.RenderJsonExclusion;

/**
 * A dedicated controller for all the API related stuff
 * 
 * @author chamerling
 * 
 */
public class V1 extends Controller {

	public static final String PATH = API.URL + "v1";

	@APIDefinition(description = "List all the API V1 operations", clazz = String.class)
	public static void definition() {
		List<String> URLs = new ArrayList<String>();
		for (Route route : Router.routes) {
			if (route.path.startsWith(PATH)) {
				URLs.add(Http.Request.current().getBase() + route.path);
			}
		}
		renderJSON(URLs);
	}

	@APIDefinition(description = "List all the events", clazz = Event.class)
	public static void listEvents() {
		List<Event> events = Event.allByDate();
		renderJSONExclusion(events);
	}

	@APIDefinition(description = "List all the members", clazz = Speaker.class)
	public static void listMembers() {
		List<Speaker> members = Speaker.getMembers();
		render(members);
	}
	
	@APIDefinition(description = "Get member from its ID", clazz = Speaker.class)
	public static void getMember(long id) {
		Speaker s = Speaker.findById(id);
        if (s == null) {
        	renderNoResult();
        }
		renderJSON(s);
	}

	@APIDefinition(description = "List all the speakers", clazz = Speaker.class)
	public static void listSpeakers() {
		List<Speaker> members = Speaker.getSpeakers();
		render(members);
	}
	
	@APIDefinition(description = "Get speaker from its ID", clazz = Speaker.class)
	public static void getSpeaker(long id) {
		Speaker s = Speaker.findById(id);
        if (s == null) {
        	renderNoResult();
        }
		renderJSON(s);
	}

	@APIDefinition(description = "Get the next event", clazz = Event.class)
	public static void nextEvent() {
		Event event = Event.next();
		renderJSONExclusion(event);
	}
	
	@APIDefinition(description = "Get event from its ID", clazz = Event.class)
	public static void getEvent(long id) {
        Event event = Event.findById(id);
        if (event == null) {
        	renderNoResult();
        }
		renderJSONExclusion(event);
	}

	@APIDefinition(description = "List all the news", clazz = News.class)
	public static void listNews() {
		List<News> news = News.allByDate();
		renderJSON(news);
	}
	
	@APIDefinition(description = "Get news from its ID", clazz = News.class)
	public static void getNews(long id) {
        News news = News.findById(id);
        if (news == null) {
        	renderNoResult();
        }
		renderJSON(news);
	}

	@APIDefinition(description = "List all the current partners (year ones)", clazz = YearPartner.class)
	public static void currentYearPartners() {
		List<YearPartner> partners = YearPartner.getCurrent();
		renderJSON(partners);
	}

	@APIDefinition(description = "List all the past partners (year ones)", clazz = YearPartner.class)
	public static void pastYearPartners() {
		List<YearPartner> partners = YearPartner.getOldies();
		renderJSON(partners);
	}

	@APIDefinition(description = "List all the event partners", clazz = EventPartner.class)
	public static void eventPartners() {
		List<EventPartner> partners = EventPartner.findAll();
		renderJSON(partners);
	}

	/**
	 * TODO : Must be used for all JSON renderings since we want to use specific
	 * formats.
	 * 
	 * @param o
	 */
	private static void renderJSONExclusion(Object o) {
		throw new RenderJsonExclusion(o);
	}
	
	private static void renderNoResult() {
		renderJSON("{error : 'No result'}");
	}
}

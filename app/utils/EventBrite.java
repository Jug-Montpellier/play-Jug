/**
 * JUG Montpellier
 */
package utils;

import models.Event;

/**
 * @author chamerling
 * 
 */
public class EventBrite {
	
	public static final String PREFIX = "http://www.eventbrite.fr/event/";

	private EventBrite() {
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static final String getId(Event event) {
		String result = null;

		if (event == null || event.registrationURL == null
				|| event.registrationURL.length() == 0) {
			return "";
		}

		if (event.registrationURL.startsWith(PREFIX)) {
			result = event.registrationURL.substring(
					PREFIX.length(),
					event.registrationURL.length());
		}
		return result;
	}
	
	/**
	 * 
	 * @param event
	 * @return
	 */
	public static final boolean isEventBrite(Event event) {
		return event != null && event.registrationURL != null && event.registrationURL.startsWith(PREFIX);
	}
}

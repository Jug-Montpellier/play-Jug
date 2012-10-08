package utils;

import jj.play.org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import jj.play.org.eclipse.mylyn.wikitext.textile.core.TextileLanguage;

public class Textile {

    public static String toHTML(String textile) {
        String html = new MarkupParser(new TextileLanguage()).parseToHtml(textile);
        if (html.contains("<body><p>") && html.contains("</p></body>")) {
        	html = html.substring(html.indexOf("<body><p>") + 9, html.lastIndexOf("</p></body>"));
        } else {
        	html = html.substring(html.indexOf("<body>") + 6, html.lastIndexOf("</body>"));        	
        }
        return html;
    }

}

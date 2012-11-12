package models;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.Play;
import play.data.validation.MaxSize;
import play.db.jpa.Model;

@Entity
public class Event extends Model {

    public String title;

    public Date date;

    public String location;

    @Lob
    @MaxSize(2000)
    @Column(length=2000)
    public String description;

    @Lob
    @MaxSize(5000)
    @Column(length=5000)
    public String report;

    public String registrationURL;

    public int capacity;

    public boolean open;

    @Lob
    @MaxSize(5000)
    @Column(length=5000)
    public String map;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    public List<Talk> talks;
    
    @ManyToOne
    public EventPartner partner;
    
    /**
     * The current event is an old one?
     * @return
     */
    public boolean old() {
    	return date.before(new Date());
    }
    
    public static Event next() {
        return Event.find("date > ? order by date", new Date()).first();
    }

    public static List<Event> upComings() {
        return Event.find("date > ? order by date", new Date()).fetch();
    }

    public static List<Event> pasts() {
        return Event.find("date < ? order by date desc", new Date()).fetch();
    }

    public static List<Event> lasts() {
        return Event.find("date < ? order by date desc", new Date()).fetch(3);
    }

    public static Event last() {
        return Event.find("date < ? order by date desc", new Date()).first();
    }
    
    public static List<Event> allByDate() {
    	return Event.find("order by date desc").fetch();
    }

    public boolean registrationCloded() {
        return !open || date.compareTo(new Date()) < 0;
    }

    public List<Talk> talks() {
        Collections.sort(talks, new Comparator<Talk>() {
            public int compare(Talk talk1, Talk talk2) {
                return talk1.orderInEvent - talk2.orderInEvent;
            };
        });
        return talks;
    }

    public List<Speaker> speakers() {
        List<Speaker> speakers = new ArrayList<Speaker>();
        for (Talk talk : talks) {
            speakers.add(talk.speaker);
        }
        return speakers;
    }

    public static String[] attachments(Long id) {
        File eventAttachments = getAttachment(id, "");
        return eventAttachments.list();
    }

    public static File getAttachment(Long id, String filename) {
        return Play.getFile("public/event" + id + "/" + filename);
    }

    public Long particitationValidated(){
        Long count = new Long(0L);
        count = count.valueOf(Participation.find("event.id = ? and status = ?", id, Participation.ParticipationStatus.Confirmed).fetch().size());
        return count;
    }
    
    @Override
    public String toString() {
        return title;
    }

}

package models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import play.data.validation.MaxSize;
import play.db.jpa.Model;
import utils.JsonExclude;

@Entity
public class Talk extends Model {

    public int orderInEvent;

    public String title;

    @MaxSize(5)
    public String time;

    @ManyToOne
    public Speaker speaker;

    @ManyToMany(cascade=CascadeType.PERSIST)
    public Set<Tag> tags;
    
    @MaxSize(1000)
    public String teaser;

    @JsonExclude
    @ManyToOne
    public Event event;

    @Override
    public String toString() {
        return title;
    }
}

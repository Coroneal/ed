package pl.edu.agh.crawler.db.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by nyga on 16.10.14.
 */
@Entity
@Table
public class Tag {

    @Id
    @GeneratedValue
    private int id;
    private String name;

    @ManyToMany(mappedBy = "tags",cascade = {CascadeType.ALL })
    private Set<Text> texts;

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Text> getTexts() {
        return texts;
    }

    public void setTexts(Set<Text> texts) {
        this.texts = texts;
    }


}

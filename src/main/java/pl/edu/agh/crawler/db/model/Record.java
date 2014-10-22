package pl.edu.agh.crawler.db.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by nyga on 15.10.14.
 */

@Entity
@Table
public class Record {


    @Id
    @GeneratedValue
    private int id;

    private String url;

    public Record() {
    }

    public Record(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

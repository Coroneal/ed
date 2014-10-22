package pl.edu.agh.crawler.db.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by nyga on 16.10.14.
 */
@Entity
@Table
public class SocialMediaType {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToMany(mappedBy = "socialMediaType",cascade = {CascadeType.ALL })
    private Set<SocialMediaUser> users;

    public SocialMediaType() {
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

    public Set<SocialMediaUser> getUsers() {
        return users;
    }

    public void setUsers(Set<SocialMediaUser> users) {
        this.users = users;
    }

}

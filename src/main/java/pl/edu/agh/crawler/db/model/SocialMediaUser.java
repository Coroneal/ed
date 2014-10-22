package pl.edu.agh.crawler.db.model;

import javax.persistence.*;

/**
 * Created by nyga on 16.10.14.
 */
@Entity
@Table
public class SocialMediaUser {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToOne(cascade = {CascadeType.ALL })
    @JoinColumn(name = "socialMediaType_id")
    private SocialMediaType socialMediaType;

    public SocialMediaUser() {
    }

    public SocialMediaType getSocialMediaType() {
        return socialMediaType;
    }

    public void setSocialMediaType(SocialMediaType socialMediaType) {
        this.socialMediaType = socialMediaType;
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

}

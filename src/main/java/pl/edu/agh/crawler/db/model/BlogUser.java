package pl.edu.agh.crawler.db.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by nyga on 16.10.14.
 */
@Entity
@Table
public class BlogUser {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private boolean author;

    @ManyToOne(cascade = {CascadeType.ALL })
    @JoinColumn(name = "socialMediaUser_id")
    private SocialMediaUser socialMediaUser;


    @OneToMany(mappedBy = "author",cascade = {CascadeType.ALL })
    private Set<Comment> comments;

    @OneToMany(mappedBy = "author",cascade = {CascadeType.ALL })
    private Set<Text> texts;

    public BlogUser() {
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

    public boolean isAuthor() {
        return author;
    }

    public void setAuthor(boolean author) {
        this.author = author;
    }

    public SocialMediaUser getSocialMediaUser() {
        return socialMediaUser;
    }

    public void setSocialMediaUser(SocialMediaUser socialMediaUser) {
        this.socialMediaUser = socialMediaUser;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Text> getTexts() {
        return texts;
    }

    public void setTexts(Set<Text> texts) {
        this.texts = texts;
    }
}

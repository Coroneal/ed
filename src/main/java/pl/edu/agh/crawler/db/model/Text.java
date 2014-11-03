package pl.edu.agh.crawler.db.model;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by nyga on 16.10.14.
 */
@Entity
@Table
public class Text {

    @Id
    @GeneratedValue
    private int id;

    private String title;

    private String content;

    //    @Column(name = "post_date", columnDefinition = "DATETIME")
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "post_date")
    private String postDate;

    @Column(name = "listening_number")
    private int listeningNumber;

    @Column(name = "release_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Tag_Text", joinColumns = {
            @JoinColumn(name = "text_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "tag_id",
                    nullable = false, updatable = false)})
    private Set<Tag> tags;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "author_id")
    private BlogUser author;

    @OneToMany(mappedBy = "text", cascade = {CascadeType.ALL})
    private Set<Comment> comments;


    public Text() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BlogUser getAuthor() {
        return author;
    }

    public void setAuthor(BlogUser author) {
        this.author = author;
    }


    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public int getListeningNumber() {
        return listeningNumber;
    }

    public void setListeningNumber(int listeningNumber) {
        this.listeningNumber = listeningNumber;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}

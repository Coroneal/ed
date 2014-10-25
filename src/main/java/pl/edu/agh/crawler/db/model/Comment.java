package pl.edu.agh.crawler.db.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by nyga on 16.10.14.
 */

@Entity
@Table
public class Comment {


    @Id
    @GeneratedValue
    private int id;

    @Column(name = "post_date", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate;

    private String content;

    private int likes_number;

    @Transient
    private String parentId;

    @Transient
    private String jsonId;

    @ManyToOne(cascade = {CascadeType.ALL })
    @JoinColumn(name = "author_id")
    private BlogUser author;

    @ManyToOne(cascade = {CascadeType.ALL })
    @JoinColumn(name = "text_id")
    private Text text;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "reply_comment_id")
    private Comment replyTo;

    @OneToMany(mappedBy = "replyTo")
    private Set<Comment> childComments;

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getLikes_number() {
        return likes_number;
    }

    public void setLikes_number(int likes_number) {
        this.likes_number = likes_number;
    }

    public BlogUser getAuthor() {
        return author;
    }

    public void setAuthor(BlogUser author) {
        this.author = author;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Comment getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Comment replyTo) {
        this.replyTo = replyTo;
    }

    public Set<Comment> getChildComments() {
        return childComments;
    }

    public void setChildComments(Set<Comment> childComments) {
        this.childComments = childComments;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getJsonId() {
        return jsonId;
    }

    public void setJsonId(String jsonId) {
        this.jsonId = jsonId;
    }
}

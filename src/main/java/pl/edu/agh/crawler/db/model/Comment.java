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

    @Column(name = "post_date")
    private String postDate;

    private String content;

    private int likes_number;

    @ManyToOne(cascade = {CascadeType.ALL })
    @JoinColumn(name = "author_id")
    private BlogUser author;

    @ManyToOne(cascade = {CascadeType.ALL })
    @JoinColumn(name = "text_id")
    private Text text;
//
//    @ManyToOne(cascade = {CascadeType.ALL})
//    @JoinColumn(name = "reply_to_comment_id")
//    private Comment replyTo;

//    @OneToMany(mappedBy = "replyTo")
//    private Set<Comment> childComments;

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

//    public Comment getReplyTo() {
//        return replyTo;
//    }
//
//    public void setReplyTo(Comment replyTo) {
//        this.replyTo = replyTo;
//    }

//    public Set<Comment> getChildComments() {
//        return childComments;
//    }
//
//    public void setChildComments(Set<Comment> childComments) {
//        this.childComments = childComments;
//    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
}

package pl.edu.agh.crawler.db;

import pl.edu.agh.crawler.db.model.*;

import java.util.Set;

/**
 * Created by nyga on 22.10.14.
 */
public class Article {
    private Text text;
    private BlogUser author;
    private SocialMediaType type;
    private SocialMediaUser user;
    private Set<Tag> tags;
    private Set<Comment> comments;

}

package pl.edu.agh;

import org.hibernate.Query;
import org.hibernate.Session;
import pl.edu.agh.crawler.db.HibernateUtil;
import pl.edu.agh.crawler.db.model.*;

import java.text.ParseException;
import java.util.*;

/**
 * Created by nyga on 22.10.14.
 */
public class HibernateExample {

    public static void main(String[] args) throws ParseException{

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Text article = new Text();
        BlogUser author = new BlogUser();
        author.setAuthor(true);


        article.setTitle("title");
        article.setContent("Article text");
        article.setPostDate("Data");
        article.setListeningNumber(23);


        SocialMediaType type = new SocialMediaType();
        type.setName("Tweeter");



        SocialMediaUser mediaUser = new SocialMediaUser();
        mediaUser.setSocialMediaType(type);
        mediaUser.setName("tweet user");
        type = saveSocialType(type, session);
        mediaUser = saveSocialMediaUser(mediaUser,session);


        author.setSocialMediaUser(mediaUser);
        author.setName("Goerge Martin");
        author = saveUser(author,session);

        article.setAuthor(author);

        Set<Tag> tags = new HashSet<Tag>();

        Tag t1 = new Tag();
        t1.setName("tag1");
        t1 = saveTag(t1,session);
        tags.add(t1);

        Tag t2 = new Tag();
        t2.setName("tag2");
        t2 = saveTag(t2,session);
        tags.add(t2);

        article.setTags(tags);

        //SimpleDateFormat dateFormatter = new SimpleDateFormat("M dd'th' yyyy 'at' hh:mm",Locale.ENGLISH);
        //article.setPostDate(dateFormatter.parse("Oct 20th 2014 at 3:30"));

        session.save(article);

        Set<Comment> list = new HashSet<Comment>();
        BlogUser user = new BlogUser();
        user.setAuthor(false);
        user.setName("CommentUser");
        user = saveUser(user,session);
        Comment c = new Comment();
        //c.setPostDate("data");
        c.setContent("comment text");
        c.setAuthor(user);
        c.setLikes_number(12);
        c.setText(article);
        session.save(c);
        list.add(c);

        Comment c1 = new Comment();
        //c1.setPostDate("data");
        c1.setContent("comment text2");
        c1.setAuthor(user);
        c1.setLikes_number(3);
        c1.setText(article);
        session.save(c1);
        list.add(c1);

        article.setComments(list);


        session.getTransaction().commit();
    }

    public static BlogUser saveUser(BlogUser user, Session session){
        // check if crawler was there
        String hql = "FROM BlogUser u  WHERE u.name = :NAME";
        Query query = session.createQuery(hql);
        query.setParameter("NAME",user.getName());
        List results = query.list();
        if (results.isEmpty()){
            session.save(user);
            return user;
        }
        return (BlogUser) results.get(0);
    }

    public static SocialMediaType saveSocialType(SocialMediaType type, Session session){
        // check if crawler was there
        String hql = "FROM SocialMediaType s  WHERE s.name = :NAME";
        Query query = session.createQuery(hql);
        query.setParameter("NAME",type.getName());
        List results = query.list();
        if (results.isEmpty()){
            session.save(type);
            return type;
        }
        return (SocialMediaType) results.get(0);
    }

    public static SocialMediaUser saveSocialMediaUser(SocialMediaUser user, Session session){
        // check if crawler was there
        String hql = "FROM SocialMediaUser s  WHERE s.name = :NAME";
        Query query = session.createQuery(hql);
        query.setParameter("NAME",user.getName());
        List results = query.list();
        if (results.isEmpty()){
            session.save(user);
            return user;
        }
        return (SocialMediaUser) results.get(0);
    }

    public static Tag saveTag(Tag tag, Session session){
        // check if crawler was there
        String hql = "FROM Tag t  WHERE t.name = :NAME";
        Query query = session.createQuery(hql);
        query.setParameter("NAME",tag.getName());
        List results = query.list();
        if (results.isEmpty()){
            session.save(tag);
            return tag;
        }
        return (Tag) results.get(0);
    }
}

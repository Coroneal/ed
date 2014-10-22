package pl.edu.agh.crawler.db;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pl.edu.agh.crawler.db.model.BlogUser;
import pl.edu.agh.crawler.db.model.SocialMediaType;
import pl.edu.agh.crawler.db.model.SocialMediaUser;
import pl.edu.agh.crawler.db.model.Tag;

import java.util.List;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
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

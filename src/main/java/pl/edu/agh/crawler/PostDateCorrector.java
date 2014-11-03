package pl.edu.agh.crawler;

import org.hibernate.Query;
import org.hibernate.Session;
import pl.edu.agh.crawler.db.HibernateUtil;
import pl.edu.agh.crawler.db.model.Record;
import pl.edu.agh.crawler.db.model.SocialMediaUser;
import pl.edu.agh.crawler.db.model.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by nyga on 03.11.14.
 */
public class PostDateCorrector {

    public static void main(String[] args) throws SQLException, IOException {

        try {

            Session session = HibernateUtil.getSessionFactory().openSession();

            // check if crawler was there
            String hql = "FROM Text";
            Query query = session.createQuery(hql);
            List results = query.list();
            @SuppressWarnings("unchecked")
            List<Text> texts = (List) results;
            //Text text = texts.get(0);
            session.beginTransaction();

            for ( Text text : texts){

                System.out.println(text.getReleaseDate());
            }
            //session.getTransaction().commit();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}

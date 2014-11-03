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

            SimpleDateFormat format = new SimpleDateFormat("HH:mm a dd MMM yyyy");
            for ( Text text : texts){
                String[] d = text.getPostDate().split("\\s+");
                String month = d[0];
                String day  = d[1].substring(0,d[1].length()-2);
                String hour  = d[4].substring(0, d[4].length() - 2);
                String am  = d[4].substring(d[4].length()-2);
                String year  = d[2];
                //System.out.println(hour+" "+am+" "+day+" "+month+" "+year);
                String dd = hour+" "+am+" "+day+" "+month+" "+year;
                Date parsed  = format.parse(dd);
                text.setReleaseDate(parsed);
                session.save(text);
            }
            session.getTransaction().commit();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}

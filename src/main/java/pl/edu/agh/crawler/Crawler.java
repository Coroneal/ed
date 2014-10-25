package pl.edu.agh.crawler;

/**
 * Created by nyga on 14.10.14.
 */

import org.hibernate.Query;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.edu.agh.crawler.db.HibernateUtil;
import pl.edu.agh.crawler.db.model.Record;
import pl.edu.agh.crawler.html.HtmlParser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class Crawler {

    private final static String HOME_PAGE = "http://www.tuaw.com/";
    private static Session session;

    public static void main(String[] args) throws SQLException, IOException {
        session = HibernateUtil.getSessionFactory().openSession();
        hqlTruncate("Record");
        processPage(HOME_PAGE);

    }

    public static void processPage(String URL) {

        // check if crawler was there
        String hql = "FROM Record r  WHERE r.url = :URL";
        Query query = session.createQuery(hql);
        query.setParameter("URL", URL);
        List results = query.list();

        try {
            if (results.isEmpty()) {

                //store the URL to database to avoid parsing again
                session.beginTransaction();
                session.save(new Record(URL));
                session.getTransaction().commit();

                //get useful information

                Document doc = Jsoup.connect(URL).timeout(10 * 1000).get();
                if (isArticlePage(doc.baseUri())) {
                    new HtmlParser(doc, true);
                }

                //get all links and recursively call the processPage method
                Elements questions = doc.select("a[href]");
                for (Element link : questions) {
                    String href = link.attr("href");
                    if (isArticlePage(href) || href.startsWith("/page/"))
                        processPage(link.attr("abs:href"));
                }


            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    public static int hqlTruncate(String myTable) {
        String hql = String.format("delete from %s", myTable);
        Query query = session.createQuery(hql);
        return query.executeUpdate();
    }

    private static boolean isArticlePage(String link) {
        if (link.matches("http://www.tuaw.com/\\d{4}/\\d{2}/\\d{2}/.*"))
            return true;
        return false;
    }
}

package pl.edu.agh.crawler.html;

import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;
import pl.edu.agh.crawler.db.HibernateUtil;
import pl.edu.agh.crawler.db.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.*;

/**
 * Created by nyga on 15.10.14.
 */
public class HtmlParser {

    private final static String FYRO_SERVER_URL = "http://bootstrap.tuaw.fyre.co/bs3/v3.1/tuaw.fyre.co/SITE_ID/ARTICLE_ID/init";

    private boolean verbose;
    private Document doc;
    Session session;

    String title;
    String tweet;
    String authorText;
    String timestamp;
    List<String> tags;
    String content;
    int listeningNumber;
    Set<Comment> listComments;

    public HtmlParser(Document doc, boolean verbose) throws IOException, SAXException {
        this.session = HibernateUtil.getSessionFactory().openSession();
        this.verbose = verbose;
        this.doc = doc;
        parseHtml();
    }

    private void parseHtml() throws IOException, SAXException {

        Element post = doc.getElementById("post");
        Element header = post.getElementsByClass("header").first();
        title = extract(header.select("h1[class=posttitle"));
        tweet = extract(header.select("span[class=tweet"));
        authorText = extract(header.select("span[class=author"));
        timestamp = extract(header.select("span[class=timestamp"));

        Element body = doc.select("div[class=body").first();
        Elements tagsElements = body.getElementsByClass("view-tags").select("a[href]");
        tags = new ArrayList<String>();
        for ( Element tag : tagsElements) {
            String tagName = extract(tag);
            if(!tags.contains(tagName))
                tags.add(extract(tag));
        }
        body.getElementsByClass("view-tags").remove();
        Element source = body.getElementById("source");
        source.remove();
        content = extract(body);

        String articleHTML = doc.html();
        String articleId = getPropertyValue("articleId",articleHTML);
        byte[] encodedBytes = Base64.encodeBase64(articleId.getBytes());
        articleId =  new String(encodedBytes);
        String siteId = getPropertyValue("siteId",articleHTML);

        String url = FYRO_SERVER_URL.replace("SITE_ID",siteId).replace("ARTICLE_ID",articleId);
        System.out.println(articleId);
        System.out.println(siteId);
        System.out.println(url);
        System.out.println("----------------------");
        InputStream input = new URL(url).openStream();
        Reader reader = new InputStreamReader(input, "UTF-8");
        GsonBuilder builder = new GsonBuilder();
        LinkedTreeMap<String,LinkedTreeMap> o = (LinkedTreeMap<String,LinkedTreeMap>)builder.create().fromJson(reader, Object.class);
        o = o.get("headDocument");
        Object content = o.get("content");
        ArrayList<LinkedTreeMap> comments = (ArrayList<LinkedTreeMap>)  content;
        LinkedTreeMap<String,LinkedTreeMap> authors = o.get("authors");
        Object followers = o.get("followers");
        listeningNumber = ((ArrayList<String>) followers).size();
        listComments = extractComments(comments,authors);

        printHeadlines();
        saveToDB();
    }


    private Set<Comment> extractComments(ArrayList<LinkedTreeMap> comments,LinkedTreeMap<String,LinkedTreeMap> authors ){
        Set<Comment> list = new HashSet<Comment>();
        for ( LinkedTreeMap comment : comments) {

            comment = (LinkedTreeMap<String,LinkedTreeMap>) comment.get("content");
            String authorId = (String) comment.get("authorId");
            if (authorId == null)
                continue;
            String userName = (String) authors.get(authorId).get("displayName");
            long date =(new Date()).getTime() - (long) ((Double) comment.get("createdAt")).doubleValue();
            String text = Jsoup.parse((String) comment.get("bodyHtml")).text();
            int likesCount = 0;
            LinkedTreeMap<String,LinkedTreeMap> annotations = (LinkedTreeMap<String,LinkedTreeMap>) comment.get("annotations");
            if(annotations.containsKey("likedBy")) {
                Object o = annotations.get("likedBy");
                likesCount = ((ArrayList<LinkedTreeMap>) o).size();
            }
            String parentId = (String) comment.get("parentId");
            String id = (String) comment.get("id");

            BlogUser user = new BlogUser();
            user.setAuthor(false);
            user.setName(userName);
            Comment c = new Comment();
            user.setName(userName);
            c.setPostDate(new Date(date));
            c.setContent(text);
            c.setLikes_number(likesCount);
            c.setAuthor(user);
            c.setParentId(parentId);
            c.setJsonId(id);
            list.add(c);

            printComment(c);
        }
        return list;
    }

    private void saveToDB(){

        session.beginTransaction();

        Text article = new Text();
        BlogUser author = new BlogUser();
        author.setAuthor(true);
        article.setTitle(title);
        SocialMediaType type = new SocialMediaType();
        type.setName("Tweeter");
        SocialMediaUser mediaUser = new SocialMediaUser();
        mediaUser.setSocialMediaType(HibernateUtil.saveSocialType(type,session));
        mediaUser.setName(tweet);
        author.setSocialMediaUser(HibernateUtil.saveSocialMediaUser(mediaUser,session));
        author.setName(authorText);
        article.setPostDate(timestamp);
        article.setListeningNumber(listeningNumber);
        article.setContent(content);
        article.setAuthor(HibernateUtil.saveUser(author, session));

        Set<Tag> tagsObj = new HashSet<Tag>();
        for (String tagName : tags) {
            Tag tag = new Tag();
            tag.setName(tagName);
            tag = HibernateUtil.saveTag(tag,session);
            tagsObj.add(tag);
        }

        session.save(article);

        for (Comment comment: listComments) {
            comment.setAuthor(HibernateUtil.saveUser(comment.getAuthor(), session));
            comment.setText(article);
            session.save(comment);
        }

        for (Comment comment: listComments) {
            String id = comment.getParentId();
            for (Comment otherComment: listComments) {
                if(otherComment.getJsonId().equals(id))
                    comment.setReplyTo(otherComment);
            }
            session.save(comment);
        }

        article.setTags(tagsObj);
        article.setComments(listComments);
        session.getTransaction().commit();
    }

    private String getPropertyValue(String property, String context){
        int articleIdIndex = context.indexOf(property);
        String sub = context.substring(articleIdIndex);
        sub = sub.substring(sub.indexOf(":")+1,sub.indexOf(","));
        sub = sub.replaceAll("\"","");
        return sub;
    }

    private String extract(Elements elements){
        if (elements.size() == 0)
            return "";
        Element element = elements.first();
        return extract(element);
    }

    private String extract(Element element){
        if (element == null || element.text() == null)
            return "";
        return element.text();
    }

    private void printHeadlines(){
        if(verbose == false)
            return;
        System.out.println(title);
        System.out.println(tweet);
        System.out.println(authorText);
        System.out.println(timestamp);
        System.out.println(content);
        System.out.println(listeningNumber);
        System.out.println("---------------------------");
    }

    private void printComment(Comment comment){
        if(verbose == false)
            return;
        System.out.println("++++++++++");
        System.out.println(comment.getAuthor().getName());
        System.out.println(comment.getPostDate());
        System.out.println(comment.getContent());
        System.out.println(comment.getLikes_number());
    }


}


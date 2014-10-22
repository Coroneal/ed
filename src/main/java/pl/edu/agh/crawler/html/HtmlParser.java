package pl.edu.agh.crawler.html;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.xml.sax.SAXException;
import pl.edu.agh.crawler.db.HibernateUtil;
import pl.edu.agh.crawler.db.model.*;
import sun.plugin.javascript.navig.Link;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nyga on 15.10.14.
 */
public class HtmlParser {

    private final static String SELENIUM_DRIVER= "./selenium/chromedriver";

    private boolean verbose;
    private Document doc;
    WebDriver driver;
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
        System.setProperty("webdriver.chrome.driver",SELENIUM_DRIVER );
        //System.setProperty("webdriver.firefox.driver", "firefox");
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Arrays.asList("ignore-certificate-errors"));
        driver = new ChromeDriver(options);
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

        // comment was dynamically created by javascript, so sth was needed to evaluate javascript
        driver.get(doc.baseUri());
        WebElement discussionElement = driver.findElement(By.id("lf_comment_stream"));
        String html = discussionElement.getAttribute("innerHTML");
        driver.quit();
        Document discussion = Jsoup.parse(html);

        String listeningNumberText = extract(discussion.select("em[class=fyre-stream-livecount]"));
        listeningNumber = getNumberFromString(listeningNumberText);

        for( Element comment : discussion.getElementsByTag("article")){
            if (!comment.hasAttr("data-author-id") || !comment.hasAttr("data-message-id"))
                comment.remove();
        }
        Elements comments =  discussion.getElementsByTag("article");
        listComments = extractComments(comments);
        printHeadlines();
        saveToDB();
    }

    private Set<Comment> extractComments(Elements comments){
        Set<Comment> list = new HashSet<Comment>();
        for ( Element comment : comments) {

            String userName = extract(comment.getElementsByAttributeValue("itemprop", "author"));
            String date = comment.select("meta[itemprop=dateCreated]").first().attr("content");
            String text = extract(comment.select("div[itemprop=text]"));
            String likesCount = extract(comment.select("span[class=fyre-comment-like-count]"));

            BlogUser user = new BlogUser();
            user.setAuthor(false);
            user.setName(userName);
            Comment c = new Comment();
            user.setName(userName);
            c.setPostDate(date);
            c.setContent(text);
            c.setLikes_number(getNumberFromString(likesCount));
            c.setAuthor(user);
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

        article.setTags(tagsObj);
        article.setComments(listComments);
        session.getTransaction().commit();
    }

    private int getNumberFromString(String string){
        Matcher matcher = Pattern.compile("\\d+").matcher(string);
        int number;
        try {
            matcher.find();
            number = Integer.valueOf(matcher.group());
        } catch (Exception e){
            return 0;
        }
        return number;
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


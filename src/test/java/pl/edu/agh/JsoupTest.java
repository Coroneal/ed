package pl.edu.agh;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.edu.agh.crawler.db.model.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nyga on 21.10.14.
 */
public class JsoupTest {
    private static String html = "<html>\n" +
            " <head></head>\n" +
            " <body>\n" +
            "  <div class=\"fyre fyre-width-medium\">\n" +
            "   <div class=\"fyre-widget \">\n" +
            "    <iframe src=\"http://www.livefyre.com/v3/tracking/http%3A%2F%2Fwww.tuaw.com%2F2014%2F10%2F16%2Fapple-announces-the-ipad-air-2%2F\" frameborder=\"0\" style=\"position: absolute; height: 1px; width: 1px; z-index: 99999; background-color: transparent;\"></iframe>\n" +
            "    <div class=\"fyre-featured-content-wrapper\" style=\"display: none\"></div>\n" +
            "    <div class=\"fyre-stream-stats\">\n" +
            "     <div class=\"fyre-comment-count\">\n" +
            "      <span>4 comments</span>\n" +
            "     </div>\n" +
            "     <div class=\"fyre-help\">\n" +
            "      <div class=\"fyre-box-wrapper\">\n" +
            "       <a href=\"http://livefyre.com\" target=\"_blank\"><img class=\"fyre-logo-drop\" src=\"http://zor.fyre.co/wjs/v3.0.1413574849/images/down_arrow.png\"><img class=\"fyre-logo-help\" src=\"http://zor.fyre.co/wjs/v3.0.1413574849/images/lf-logo.png\" alt=\"Livefyre\"></a>\n" +
            "       <ul class=\"fyre-box-list\">\n" +
            "        <li><a href=\"http://livefyre.com\" target=\"_blank\" title=\"Get Livefyre\">Get Livefyre</a></li>\n" +
            "        <li><a href=\"http://support.livefyre.com\" target=\"_blank\" title=\"FAQ\">FAQ</a></li>\n" +
            "       </ul>\n" +
            "      </div>\n" +
            "     </div>\n" +
            "    </div>\n" +
            "    <div class=\"fyre-auth\">\n" +
            "     <div class=\"fyre-login-bar\">\n" +
            "      <a rel=\"nofollow\" class=\"fyre-user-loggedout\">Sign in</a>\n" +
            "     </div>\n" +
            "     <div class=\"fyre-live-container\">\n" +
            "      <div class=\"fyre-livecount\">\n" +
            "       <em class=\"fyre-stream-livecount\">4 people listening</em>\n" +
            "      </div>\n" +
            "      <div class=\"fyre-listener-avatars\"></div>\n" +
            "     </div>\n" +
            "    </div>\n" +
            "    <div class=\"fyre-editor\">\n" +
            "     <div class=\"fyre-editor-container fyre-editor-container-focus\">\n" +
            "      <div class=\"fyre-editor-editable editable fyre-editor-field\" id=\"fyre-editor-5\" g_editable=\"true\" role=\"textbox\" contenteditable=\"true\">\n" +
            "       <p>&nbsp;</p>\n" +
            "      </div>\n" +
            "      <span class=\"fyre-editor-spinner\" style=\"display: none;\"></span>\n" +
            "      <div class=\"fyre-editor-preview\"></div>\n" +
            "      <div class=\"fyre-editor-media-drawer\" style=\"display: none;\"></div>\n" +
            "      <div class=\"fyre-editor-toolbar\">\n" +
            "       <div class=\"fyre-mention-menu fyre-mention-menu-vertical\" role=\"menu\" aria-haspopup=\"true\" style=\"-webkit-user-select: none; display: none;\">\n" +
            "        <div class=\"fyre-mention-menu-filter\" style=\"-webkit-user-select: none;\">\n" +
            "         <div style=\"-webkit-user-select: none;\"></div>\n" +
            "         <input type=\"text\" tabindex=\"-1\" style=\"-webkit-user-select: none;\">\n" +
            "        </div>\n" +
            "        <div class=\"fyre-mention-menu-content\" style=\"-webkit-user-select: none;\">\n" +
            "         <div class=\"goog-control goog-control-disabled\" aria-disabled=\"true\" style=\"-webkit-user-select: none;\" id=\":1a\"></div>\n" +
            "         <div class=\"goog-control goog-control-disabled\" aria-disabled=\"true\" id=\":1b\" style=\"-webkit-user-select: none;\"></div>\n" +
            "         <div class=\"goog-control goog-control-disabled\" aria-disabled=\"true\" id=\":1c\" style=\"-webkit-user-select: none;\"></div>\n" +
            "         <div class=\"fyre-mention-item fyre-mention-item-twitter\" aria-hidden=\"true\" id=\":1d\" style=\"-webkit-user-select: none; display: none;\">\n" +
            "          <img src=\"http://avatars.fyre.co/a/anon/50.jpg\" class=\"fyre-mention-item-avatar\">\n" +
            "          <h4 class=\"fyre-mention-item-display-name\"></h4>\n" +
            "          <span class=\"fyre-mention-item-twitter\"></span>\n" +
            "         </div>\n" +
            "         <div class=\"fyre-mention-item fyre-mention-item-livefyre\" aria-hidden=\"true\" id=\":1e\" style=\"-webkit-user-select: none; display: none;\">\n" +
            "          <img src=\"http://avatars.fyre.co/a/anon/50.jpg\" class=\"fyre-mention-item-avatar\">\n" +
            "          <h4 class=\"fyre-mention-item-display-name\"></h4>\n" +
            "          <span class=\"fyre-mention-item-livefyre\"></span>\n" +
            "         </div>\n" +
            "        </div>\n" +
            "       </div>\n" +
            "       <div class=\"goog-toolbar goog-toolbar-horizontal\" role=\"toolbar\" style=\"-webkit-user-select: none;\">\n" +
            "        <div class=\"goog-toolbar-separator goog-inline-block\" role=\"separator\" id=\":17\" style=\"-webkit-user-select: none;\">\n" +
            "         &nbsp;\n" +
            "        </div>\n" +
            "        <div class=\"goog-inline-block fyre-button-left fyre-format-button\" role=\"button\" id=\":19\" style=\"-webkit-user-select: none;\">\n" +
            "         <div class=\"goog-inline-block fyre-button-left-outer-box\">\n" +
            "          <div class=\"goog-inline-block fyre-button-left-inner-box\"></div>\n" +
            "         </div>\n" +
            "        </div>\n" +
            "        <div class=\"goog-inline-block fyre-button-left fyre-mention-button\" role=\"button\" id=\":1g\" style=\"-webkit-user-select: none;\">\n" +
            "         <div class=\"goog-inline-block fyre-button-left-outer-box\">\n" +
            "          <div class=\"goog-inline-block fyre-button-left-inner-box\"></div>\n" +
            "         </div>\n" +
            "        </div>\n" +
            "        <div class=\"goog-inline-block fyre-button-left fyre-embed-button\" role=\"button\" id=\":1j\" aria-hidden=\"true\" style=\"-webkit-user-select: none; display: none;\">\n" +
            "         <div class=\"goog-inline-block fyre-button-left-outer-box\">\n" +
            "          <div class=\"goog-inline-block fyre-button-left-inner-box\"></div>\n" +
            "         </div>\n" +
            "        </div>\n" +
            "        <div class=\"goog-inline-block fyre-button-left fyre-raw-button\" role=\"button\" id=\":1l\" style=\"-webkit-user-select: none; display: none;\">\n" +
            "         <div class=\"goog-inline-block fyre-button-left-outer-box\">\n" +
            "          <div class=\"goog-inline-block fyre-button-left-inner-box\"></div>\n" +
            "         </div>\n" +
            "        </div>\n" +
            "        <div class=\"goog-inline-block fyre-button-left fyre-follow-button\" role=\"button\" id=\":1n\" style=\"-webkit-user-select: none;\">\n" +
            "         <div class=\"goog-inline-block fyre-button-left-outer-box\">\n" +
            "          <div class=\"goog-inline-block fyre-button-left-inner-box\">\n" +
            "           + Follow\n" +
            "          </div>\n" +
            "         </div>\n" +
            "        </div>\n" +
            "        <div class=\"goog-inline-block fyre-button-right fyre-share-button\" role=\"button\" id=\":1p\" style=\"-webkit-user-select: none;\">\n" +
            "         <div class=\"goog-inline-block fyre-button-right-outer-box\">\n" +
            "          <div class=\"goog-inline-block fyre-button-right-inner-box\">\n" +
            "           Share\n" +
            "           <label class=\"fyre-share-counter fyre-share-empty\"></label>\n" +
            "          </div>\n" +
            "         </div>\n" +
            "        </div>\n" +
            "        <div class=\"goog-inline-block fyre-button-right fyre-post-button fyre-post-button-new\" role=\"button\" id=\":1r\" style=\"-webkit-user-select: none;\">\n" +
            "         <div class=\"goog-inline-block fyre-button-right-outer-box\">\n" +
            "          <div class=\"goog-inline-block fyre-button-right-inner-box\">\n" +
            "           Post comment as...\n" +
            "          </div>\n" +
            "         </div>\n" +
            "        </div>\n" +
            "       </div>\n" +
            "      </div>\n" +
            "     </div>\n" +
            "     <div class=\"fyre-comment-preview\" style=\"display: none;\">\n" +
            "      <h5>Custom HTML Preview<span class=\"fyre-icon-remove\"></span></h5>\n" +
            "      <div class=\"fyre-roundedpanel-content fyre-comment-preview-content\">\n" +
            "       <article class=\"fyre-comment-article\">\n" +
            "        <div class=\"fyre-comment\">\n" +
            "         <div class=\"fyre-comment-raw-html\"></div>\n" +
            "        </div>\n" +
            "       </article>\n" +
            "      </div>\n" +
            "     </div>\n" +
            "    </div>\n" +
            "    <div class=\"fyre-input-box-collapsed\"></div>\n" +
            "    <div class=\"fyre-stream-header\">\n" +
            "     <div class=\"fyre-content-loading\" style=\"display: none;\">\n" +
            "      Loading\n" +
            "     </div>\n" +
            "     <div class=\"fyre-stream-sort\">\n" +
            "      <div class=\"fyre-stream-sort-options\">\n" +
            "       <a class=\"fyre-stream-sort-newest fyre-stream-sort-selected\">Newest</a>\n" +
            "       <span class=\"fyre-stream-sort-bar\"> | </span>\n" +
            "       <a class=\"fyre-stream-sort-oldest\">Oldest</a>\n" +
            "       <span class=\"fyre-stream-sort-bar\"> | </span>\n" +
            "       <a class=\"fyre-stream-sort-top-comments\">Top Comments</a>\n" +
            "      </div>\n" +
            "      <a class=\"fyre-stream-sort-back\"></a>\n" +
            "     </div>\n" +
            "    </div>\n" +
            "    <div class=\"fyre-comment-stream\">\n" +
            "     <div class=\"fyre-stream-content\">\n" +
            "      <article class=\"fyre-comment-article fyre-comment-source-5\" data-message-id=\"226573152\" data-author-id=\"543cd03935346475c3000a97@tuaw.fyre.co\" itemtype=\"http://schema.org/Comment\" itemscope style=\"\">\n" +
            "       <div class=\"fyre-comment-wrapper\">\n" +
            "        <div class=\"fyre-comment-user\" data-from=\"Nataliamolinaao\" data-author-id=\"543cd03935346475c3000a97@tuaw.fyre.co\">\n" +
            "         <a href=\"\" class=\"fyre-comment-author\"><img src=\"http://avatars.fyre.co/a/141/1db2e7ad10cf37ae02306cb3a16ef15d/50.jpg?v=1413271617\" class=\"fyre-user-avatar\" alt=\"Nataliamolinaao\" onerror=\"javascript:this.src='http://avatars.fyre.co/a/anon/50.jpg';\"></a>\n" +
            "         <div class=\"fyre-comment-socialsync fyre-comment-from-undefined\"></div>\n" +
            "         <div class=\"fyre-comment-highlight\"></div>\n" +
            "        </div>\n" +
            "        <header class=\"fyre-comment-head\">\n" +
            "         <a class=\"fyre-comment-username\"><span itemprop=\"author\">Nataliamolinaao</span></a>\n" +
            "         <span class=\"fyre-comment-user-rating\" style=\"display: none;\">5pts</span>\n" +
            "         <span class=\"fyre-comment-tag fyre-featured\" style=\"display: none;\"><span class=\"fyre-featured-icon\"></span><span class=\"fyre-featured-text\">Featured</span><br></span>\n" +
            "         <meta itemprop=\"dateCreated\" content=\"2014-10-20T08:19:33.000Z\">\n" +
            "         <time class=\"fyre-comment-date\">1 day ago</time>\n" +
            "         <span class=\"fyre-comment-archive\"></span>\n" +
            "         <div class=\"fyre-comment-premod\"></div>\n" +
            "         <div class=\"fyre-flag-list\"></div>\n" +
            "        </header>\n" +
            "        <section class=\"fyre-comment-body\">\n" +
            "         <div class=\"fyre-comment\" itemprop=\"text\">\n" +
            "          <p></p>\n" +
            "          <p>I have to say that, if they sell ipads air 2 is just because of the marketing strategy they have done (NOT the quality of the product):&nbsp;</p>\n" +
            "          <p><br></p>\n" +
            "          <p>Because&nbsp;</p>\n" +
            "          <p>As u can see, &nbsp;the differences with the previous model are ... hardly any ?&nbsp;<a href=\"http://versus.com/en/apple-ipad-air-2-vs-apple-ipad-air\" target=\"_blank\" rel=\"nofollow\">http://versus.com/en/apple-ipad-air-2-vs-apple-ipad-air</a></p>\n" +
            "          <p><br></p>\n" +
            "          <p>1st - The thinnest ipad in the world. Well, maybe it is very cool to say \"ey you! i owe the THINNEST IPAD in the world!\" but i d rather choose a device for other things, such as processor, battery power etc</p>\n" +
            "          <p><br></p>\n" +
            "          <p>2nd- Apple play with users and they are always happy. I can't get it. Why now there's only the option of 16GB and 68GB ?? what about 32GB?? now people think \"well, 16GB is not enough\" and they buy the most expensive options like lambs... :( i feel insulted !</p>\n" +
            "          <p></p>\n" +
            "         </div>\n" +
            "        </section>\n" +
            "        <footer class=\"fyre-comment-footer\">\n" +
            "         <div class=\"fyre-comment-actions\">\n" +
            "          <a class=\"fyre-flag-link\" title=\"Flag\"><span></span>Flag</a>\n" +
            "          <a class=\"fyre-share-link\" title=\"Share\"><span></span>Share</a>\n" +
            "         </div>\n" +
            "         <span class=\"fyre-comment-like\"><span class=\"fyre-comment-like-count\"></span><span class=\"fyre-comment-like-imgs\"></span><a class=\"fyre-comment-like-btn fyre-comment-action-button\">Like</a></span>\n" +
            "         <a class=\"fyre-comment-reply fyre-comment-action-button\">Reply</a>\n" +
            "         <div class=\"fyre-editor\" style=\"display: none;\"></div>\n" +
            "        </footer>\n" +
            "       </div>\n" +
            "       <div class=\"fyre-comment-divider\">\n" +
            "        <a title=\"Toggle Collapse\" class=\"fyre-comment-collapse\" style=\"display: none;\"></a>\n" +
            "        <div class=\"fyre-comment-divider\"></div>\n" +
            "       </div>\n" +
            "       <div class=\"fyre-outer-comment-container\">\n" +
            "        <div class=\"fyre-comment-container fyre-comment-replies fyre-comment-replies-indent\"></div>\n" +
            "       </div>\n" +
            "      </article>\n" +
            "      <article class=\"fyre-comment-article fyre-comment-source-0\" data-message-id=\"225989917\" data-author-id=\"5423c9fdb841b32b9d0020e3@tuaw.fyre.co\" itemtype=\"http://schema.org/Comment\" itemscope style=\"\">\n" +
            "       <div class=\"fyre-comment-wrapper\">\n" +
            "        <div class=\"fyre-comment-user\" data-from=\"EdwardJames\" data-author-id=\"5423c9fdb841b32b9d0020e3@tuaw.fyre.co\">\n" +
            "         <a href=\"\" class=\"fyre-comment-author\"><img src=\"http://avatars.fyre.co/a/141/3dece7881105bdc453c7e32af662847d/50.jpg?v=1411631619\" class=\"fyre-user-avatar\" alt=\"EdwardJames\" onerror=\"javascript:this.src='http://avatars.fyre.co/a/anon/50.jpg';\"></a>\n" +
            "         <div class=\"fyre-comment-socialsync fyre-comment-from-undefined\"></div>\n" +
            "         <div class=\"fyre-comment-highlight\"></div>\n" +
            "        </div>\n" +
            "        <header class=\"fyre-comment-head\">\n" +
            "         <a class=\"fyre-comment-username\"><span itemprop=\"author\">EdwardJames</span></a>\n" +
            "         <span class=\"fyre-comment-user-rating\" style=\"display: none;\">5pts</span>\n" +
            "         <span class=\"fyre-comment-tag fyre-featured\" style=\"display: none;\"><span class=\"fyre-featured-icon\"></span><span class=\"fyre-featured-text\">Featured</span><br></span>\n" +
            "         <meta itemprop=\"dateCreated\" content=\"2014-10-18T03:28:16.000Z\">\n" +
            "         <time class=\"fyre-comment-date\">3 days ago</time>\n" +
            "         <span class=\"fyre-comment-archive\"></span>\n" +
            "         <div class=\"fyre-comment-premod\"></div>\n" +
            "         <div class=\"fyre-flag-list\"></div>\n" +
            "        </header>\n" +
            "        <section class=\"fyre-comment-body\">\n" +
            "         <div class=\"fyre-comment\" itemprop=\"text\">\n" +
            "          <p>&nbsp;<br>Does&nbsp;it&nbsp;bend<span>？</span><span>Just&nbsp;kiding..&nbsp;Apple&nbsp;fans,why&nbsp;don't&nbsp;you&nbsp;go&nbsp;to&nbsp;check&nbsp;this&nbsp;web&nbsp;over?&nbsp;it&nbsp;is&nbsp;set&nbsp;for&nbsp;designing&nbsp;special&nbsp;cases&nbsp;for&nbsp;iphone.Even&nbsp;can&nbsp;get&nbsp;a&nbsp;custom&nbsp;made&nbsp;cases,Here&nbsp;you&nbsp;are <a href=\"http://www.casecoco.com/personalized-cases-for-iphone/iphone-6-cases-194\" target=\"_blank\" rel=\"nofollow\">http://www.casecoco.com/personalized-cases-for-iphone/iphone-6-cases-194</a></span></p>\n" +
            "         </div>\n" +
            "        </section>\n" +
            "        <footer class=\"fyre-comment-footer\">\n" +
            "         <div class=\"fyre-comment-actions\">\n" +
            "          <a class=\"fyre-flag-link\" title=\"Flag\"><span></span>Flag</a>\n" +
            "          <a class=\"fyre-share-link\" title=\"Share\"><span></span>Share</a>\n" +
            "         </div>\n" +
            "         <span class=\"fyre-comment-like\"><span class=\"fyre-comment-like-count\"></span><span class=\"fyre-comment-like-imgs\"></span><a class=\"fyre-comment-like-btn fyre-comment-action-button\">Like</a></span>\n" +
            "         <a class=\"fyre-comment-reply fyre-comment-action-button\">Reply</a>\n" +
            "         <div class=\"fyre-editor\" style=\"display: none;\"></div>\n" +
            "        </footer>\n" +
            "       </div>\n" +
            "       <div class=\"fyre-comment-divider\">\n" +
            "        <a title=\"Toggle Collapse\" class=\"fyre-comment-collapse\" style=\"display: none;\"></a>\n" +
            "        <div class=\"fyre-comment-divider\"></div>\n" +
            "       </div>\n" +
            "       <div class=\"fyre-outer-comment-container\">\n" +
            "        <div class=\"fyre-comment-container fyre-comment-replies fyre-comment-replies-indent\"></div>\n" +
            "       </div>\n" +
            "      </article>\n" +
            "      <article class=\"fyre-comment-article fyre-comment-source-5\" data-message-id=\"225439167\" data-author-id=\"5050f12e77eb993293000635@tuaw.fyre.co\" itemtype=\"http://schema.org/Comment\" itemscope style=\"\">\n" +
            "       <div class=\"fyre-comment-wrapper\">\n" +
            "        <div class=\"fyre-comment-user\" data-from=\"SteveAustin\" data-author-id=\"5050f12e77eb993293000635@tuaw.fyre.co\">\n" +
            "         <a href=\"\" class=\"fyre-comment-author\"><img src=\"http://avatars.fyre.co/a/141/58a4cbcf16dfc87b1326e813bc861968/50.jpg\" class=\"fyre-user-avatar\" alt=\"SteveAustin\" onerror=\"javascript:this.src='http://avatars.fyre.co/a/anon/50.jpg';\"></a>\n" +
            "         <div class=\"fyre-comment-socialsync fyre-comment-from-undefined\"></div>\n" +
            "         <div class=\"fyre-comment-highlight\"></div>\n" +
            "        </div>\n" +
            "        <header class=\"fyre-comment-head\">\n" +
            "         <a class=\"fyre-comment-username\"><span itemprop=\"author\">SteveAustin</span></a>\n" +
            "         <span class=\"fyre-comment-user-rating\" style=\"display: none;\">5pts</span>\n" +
            "         <span class=\"fyre-comment-tag fyre-featured\" style=\"display: none;\"><span class=\"fyre-featured-icon\"></span><span class=\"fyre-featured-text\">Featured</span><br></span>\n" +
            "         <meta itemprop=\"dateCreated\" content=\"2014-10-16T18:56:29.000Z\">\n" +
            "         <time class=\"fyre-comment-date\">5 days ago</time>\n" +
            "         <span class=\"fyre-comment-archive\"></span>\n" +
            "         <div class=\"fyre-comment-premod\"></div>\n" +
            "         <div class=\"fyre-flag-list\"></div>\n" +
            "        </header>\n" +
            "        <section class=\"fyre-comment-body\">\n" +
            "         <div class=\"fyre-comment\" itemprop=\"text\">\n" +
            "          <p>They didn't say when?<br></p>\n" +
            "         </div>\n" +
            "        </section>\n" +
            "        <footer class=\"fyre-comment-footer\">\n" +
            "         <div class=\"fyre-comment-actions\">\n" +
            "          <a class=\"fyre-flag-link\" title=\"Flag\"><span></span>Flag</a>\n" +
            "          <a class=\"fyre-share-link\" title=\"Share\"><span></span>Share</a>\n" +
            "         </div>\n" +
            "         <span class=\"fyre-comment-like\"><span class=\"fyre-comment-like-count\"></span><span class=\"fyre-comment-like-imgs\"></span><a class=\"fyre-comment-like-btn fyre-comment-action-button\">Like</a></span>\n" +
            "         <a class=\"fyre-comment-reply fyre-comment-action-button\">Reply</a>\n" +
            "         <div class=\"fyre-editor\" style=\"display: none;\"></div>\n" +
            "        </footer>\n" +
            "       </div>\n" +
            "       <div class=\"fyre-comment-divider fyre-divider-active\">\n" +
            "        <a title=\"Toggle Collapse\" class=\"fyre-comment-collapse\"></a>\n" +
            "        <div class=\"fyre-comment-divider\"></div>\n" +
            "       </div>\n" +
            "       <div class=\"fyre-outer-comment-container\">\n" +
            "        <div class=\"fyre-comment-container fyre-comment-replies fyre-comment-replies-indent\">\n" +
            "         <article class=\"fyre-comment-article fyre-comment-source-5\" data-message-id=\"225471295\" data-author-id=\"5050f12e77eb993293000635@tuaw.fyre.co\" itemtype=\"http://schema.org/Comment\" itemscope style=\"\">\n" +
            "          <div class=\"fyre-comment-wrapper\">\n" +
            "           <div class=\"fyre-comment-user\" data-from=\"SteveAustin\" data-author-id=\"5050f12e77eb993293000635@tuaw.fyre.co\">\n" +
            "            <a href=\"\" class=\"fyre-comment-author\"><img src=\"http://avatars.fyre.co/a/141/58a4cbcf16dfc87b1326e813bc861968/50.jpg\" class=\"fyre-user-avatar\" alt=\"SteveAustin\" onerror=\"javascript:this.src='http://avatars.fyre.co/a/anon/50.jpg';\"></a>\n" +
            "            <div class=\"fyre-comment-socialsync fyre-comment-from-undefined\"></div>\n" +
            "            <div class=\"fyre-comment-highlight\"></div>\n" +
            "           </div>\n" +
            "           <header class=\"fyre-comment-head\">\n" +
            "            <a class=\"fyre-comment-username\"><span itemprop=\"author\">SteveAustin</span></a>\n" +
            "            <span class=\"fyre-comment-user-rating\" style=\"display: none;\">5pts</span>\n" +
            "            <span class=\"fyre-comment-tag fyre-featured\" style=\"display: none;\"><span class=\"fyre-featured-icon\"></span><span class=\"fyre-featured-text\">Featured</span><br></span>\n" +
            "            <meta itemprop=\"dateCreated\" content=\"2014-10-16T20:18:54.000Z\">\n" +
            "            <time class=\"fyre-comment-date\">5 days ago</time>\n" +
            "            <span class=\"fyre-comment-archive\"></span>\n" +
            "            <div class=\"fyre-comment-premod\"></div>\n" +
            "            <div class=\"fyre-flag-list\"></div>\n" +
            "           </header>\n" +
            "           <section class=\"fyre-comment-body\">\n" +
            "            <div class=\"fyre-comment\" itemprop=\"text\">\n" +
            "             <p>Never mind - the store is now showing pre-orders starting tomorrow (Oct 17), though still no indication of availability date that I could see.<br></p>\n" +
            "            </div>\n" +
            "           </section>\n" +
            "           <footer class=\"fyre-comment-footer\">\n" +
            "            <div class=\"fyre-comment-actions\">\n" +
            "             <a class=\"fyre-flag-link\" title=\"Flag\"><span></span>Flag</a>\n" +
            "             <a class=\"fyre-share-link\" title=\"Share\"><span></span>Share</a>\n" +
            "            </div>\n" +
            "            <span class=\"fyre-comment-like\"><span class=\"fyre-comment-like-count\"></span><span class=\"fyre-comment-like-imgs\"></span><a class=\"fyre-comment-like-btn fyre-comment-action-button\">Like</a></span>\n" +
            "            <a class=\"fyre-comment-reply fyre-comment-action-button\">Reply</a>\n" +
            "            <div class=\"fyre-editor\" style=\"display: none;\"></div>\n" +
            "           </footer>\n" +
            "          </div>\n" +
            "          <div class=\"fyre-comment-divider\">\n" +
            "           <a title=\"Toggle Collapse\" class=\"fyre-comment-collapse\" style=\"display: none;\"></a>\n" +
            "           <div class=\"fyre-comment-divider\"></div>\n" +
            "          </div>\n" +
            "          <div class=\"fyre-outer-comment-container\">\n" +
            "           <div class=\"fyre-comment-container fyre-comment-replies fyre-comment-replies-indent\"></div>\n" +
            "          </div>\n" +
            "         </article>\n" +
            "        </div>\n" +
            "       </div>\n" +
            "      </article>\n" +
            "     </div>\n" +
            "     <div class=\"fyre-stream-content-not-found\"></div>\n" +
            "     <div class=\"fyre-stream-more\" style=\"display: none;\">\n" +
            "      <div class=\"fyre-stream-more-container\">\n" +
            "       <div class=\"fyre-text\">\n" +
            "        Show More Comments\n" +
            "       </div>\n" +
            "       <div class=\"fyre-spinner\">\n" +
            "        <div class=\"fyre-ball\">\n" +
            "         <!--[if IE]><img src=\"http://zor.fyre.co/wjs/v3.0.1413574849/images/fyre-spinner-ball.gif\" alt=\"\" /><![endif]-->\n" +
            "         <!--[if !IE]><!-->\n" +
            "         <img src=\"http://zor.fyre.co/wjs/v3.0.1413574849/images/fyre-spinner-ball.png\" alt=\"\">\n" +
            "         <!--<![endif]-->\n" +
            "        </div>\n" +
            "       </div>\n" +
            "      </div>\n" +
            "     </div>\n" +
            "    </div>\n" +
            "    <div class=\"fyre-top-content-stream\"></div>\n" +
            "    <div class=\"fyre-thread-breakout-stream\"></div>\n" +
            "    <div class=\"fyre-stream-menu-container\"></div>\n" +
            "   </div>\n" +
            "  </div>\n" +
            " </body>\n" +
            "</html>";

    public static void main(String[] args) {
        Document discussion = Jsoup.parse(html);
        System.out.println(discussion.html());

        for( Element comment : discussion.getElementsByTag("article")){
            if (!comment.hasAttr("data-author-id") || !comment.hasAttr("data-message-id"))
                comment.remove();
        }
        Elements comments =  discussion.getElementsByTag("article");

    }

    private static List<Comment> extractComments(Elements comments){
        List<Comment> list = new ArrayList<Comment>();
        for ( Element comment : comments) {
            System.out.println("++++++++++");
            String userName = comment.getElementsByAttributeValue("itemprop","author").first().text();
            System.out.println(userName);
            String date = comment.select("meta[itemprop=dateCreated]").first().attr("content");
            System.out.println(date);
            String text = comment.select("div[itemprop=text]").first().text();
            System.out.println(text);
            String likesCount = comment.select("span[class=fyre-comment-like-count]").first().text();
            System.out.println(likesCount);

        }
        return list;
    }
}

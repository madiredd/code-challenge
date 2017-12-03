package com.cc.service;

/**
 * Created by madiredd on 12/3/17.
 */

import com.cc.functions.EmailValidatorFn;
import com.cc.functions.WebLinkValidatorFn;
import com.google.common.collect.Sets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Pattern;

public class CrawlerService {

    private EmailValidatorFn emailValidatorFn = new EmailValidatorFn();
    private WebLinkValidatorFn webLinkValidatorFn = new WebLinkValidatorFn();
    private static final Logger LOG = LoggerFactory.getLogger(CrawlerService.class);


    public HashSet<String> getPageLinks(String URL) {
        HashSet<String> links = Sets.newHashSet();

        if (!URL.startsWith("http://")) {
            URL = "http://"+URL;
        }
        final String baseURL = URL;
        if (!links.contains(baseURL)) {
            try {
                links.add(baseURL);
                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");
                LOG.debug("Found " + linksOnPage.size() + " links");
                linksOnPage.forEach( url -> {
                    if(url.attr("abs:href").startsWith(baseURL)) {
                        //if (webLinkValidatorFn.apply(url.attr("abs:href")))
                        links.add(url.attr("abs:href"));
                    }
                });
              } catch (IOException e) {
                LOG.error("Invalid url '" + baseURL + "': " + e.getMessage());
                links.remove(baseURL);
            }
        }
        return links;
    }


    public HashSet<String> getEmails( HashSet<String> links) {
        HashSet<String> emails = Sets.newHashSet();
        links.forEach(url -> {
            Document document;
            try {
                document = Jsoup.connect(url).get();
                Elements articleLinks = document.getElementsMatchingText(
                        Pattern.compile("[a-z0-9\\.\\-+_]+@[a-z0-9\\.\\-+_]+\\.[a-z]+"));
               LOG.debug("Processing URL: " + document.baseUri());
                for (Element article : articleLinks) {
                    if (emailValidatorFn.apply(article.text())) {
                        emails.add(article.text());
                    }
                }

            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
        });
        return emails;
    }
}

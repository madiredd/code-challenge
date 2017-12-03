package com.cc;

import com.cc.service.CrawlerService;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

/**
 * Created by madiredd on 12/3/17.
 */
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        if(args.length > 0) {
            CrawlerService service = new CrawlerService();
            HashSet<String> links = service.getPageLinks(args[0]);
            HashSet<String> emails = service.getEmails(links);
            System.out.println("Found " +emails.size() +" emails");
            emails.forEach(s -> System.out.println(s));

        } else {
            System.out.println("Please provide a valid URL to crawl");
        }

    }
}


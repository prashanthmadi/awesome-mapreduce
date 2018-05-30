package com.awesome;

import com.awesome.crawler.CrawlerJob;

public class AppStart {

    public static void main(String[] args) {
        CrawlerJob crawler = new CrawlerJob();
        crawler.start(args[0]);
    }
}

package org.ict4htw.atomfeed.client.repository;

import com.sun.syndication.feed.WireFeed;
import com.sun.syndication.feed.atom.Feed;
import com.sun.syndication.io.WireFeedInput;
import org.apache.log4j.Logger;
import org.ict4htw.atomfeed.client.repository.datasource.WebClient;

import java.io.StringReader;
import java.net.URI;

public class AllFeeds {
    private WebClient webClient;
    private static Logger logger = Logger.getLogger(AllFeeds.class);

    public AllFeeds(WebClient webClient) {
        this.webClient = webClient;
    }

    public Feed getFor(URI uri) {
        String responseString = webClient.fetch(uri);
        logger.debug(responseString);
        responseString.trim().replaceFirst("^([\\W]+)<", "<");

        WireFeedInput wfi = new WireFeedInput();
        WireFeed wireFeed;
        try {
            wireFeed = wfi.build(new StringReader(responseString));
        } catch (Exception e) {
            throw new RuntimeException(responseString, e);
        }

        return (Feed) wireFeed;
    }
}
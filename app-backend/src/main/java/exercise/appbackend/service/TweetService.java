package exercise.appbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

@Service
public class TweetService{

    @Autowired
    private Environment env;

    Twitter twitter;

    public TweetService(){
    }

    public void printPlease(){
        System.out.println("Test 1");
    }

    public void printPlease2(){
        System.out.println(env.getProperty("pog"));
    }


    public void searchTwitter() throws TwitterException {
        System.out.println("starting");
        ConfigurationBuilder cb = new ConfigurationBuilder();
        System.out.println("1");
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(env.getProperty("twitter4j.oauth.consumerKey"))
                .setOAuthConsumerSecret(env.getProperty("twitter4j.oauth.consumerSecret"))
                .setOAuthAccessToken(env.getProperty("twitter4j.oauth.accessToken"))
                .setOAuthAccessTokenSecret(env.getProperty("twitter4j.oauth.accessTokenSecret"));
        System.out.println("2");
        TwitterFactory tf = new TwitterFactory(cb.build());
        System.out.println("3");
        twitter = tf.getInstance();
        System.out.println("4");
        Query query = new Query("$SPY");
        System.out.println("5");
        QueryResult result = twitter.search(query);
        System.out.println("6");
        System.out.println("Total Number of Tweets: " + result.getCount());
        for (Status status : result.getTweets()) {
            System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
        }
        System.out.println("7");
    }

}

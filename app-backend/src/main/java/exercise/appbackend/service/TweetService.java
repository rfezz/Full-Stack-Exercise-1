package exercise.appbackend.service;

import exercise.appbackend.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class TweetService{

    @Autowired
    private Environment env;

    Twitter twitter;

    public TweetService(){
    }

    public void printPlease(){
    }

    public void printPlease2(){
        System.out.println(env.getProperty("pog"));
    }


    public List<Tweet> searchTwitter() throws TwitterException {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(env.getProperty("twitter4j.oauth.consumerKey"))
                .setOAuthConsumerSecret(env.getProperty("twitter4j.oauth.consumerSecret"))
                .setOAuthAccessToken(env.getProperty("twitter4j.oauth.accessToken"))
                .setOAuthAccessTokenSecret(env.getProperty("twitter4j.oauth.accessTokenSecret"));

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        Query query = new Query("$SPY");
        QueryResult result = twitter.search(query);

        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:MM:ss");
        ArrayList<Tweet> tweetList = new ArrayList<>();

        for (Status status : result.getTweets()) {
            Tweet newTweet = new Tweet();
            newTweet.setName(status.getUser().getName());
            newTweet.setScreenName(status.getUser().getScreenName());
            newTweet.setDate(format.format(status.getCreatedAt()));
            newTweet.setContent(status.getText());
            tweetList.add(newTweet);
        }

        return tweetList;
    }

}

package exercise.appbackend.service;

import exercise.appbackend.model.Tweet;
import exercise.appbackend.repositories.SQLiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TweetService{

    @Autowired
    private Environment env;

    @Autowired
    private SQLiteRepository sqLiteRepository;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

    Twitter twitter;

    public TweetService(){
    }

    //is there a better way I can do this?
    //can I make this a singleton somehow

    public void updateDatabase() throws TwitterException {

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
        storeTweets(result);

        query = new Query("$SPY resistance");
        result = twitter.search(query);
        storeTweets(result);

        query = new Query("$SPY support");
        result = twitter.search(query);

        storeTweets(result);

        sanitizeData();
    }

    public List<Tweet> searchDatabase(String query){

        List<Tweet> tweets;

        if (query.equals("All")) {
            tweets = sqLiteRepository.findAll();
        }
        else {
            tweets = sqLiteRepository.findAll().stream()
                    .filter(tweet -> tweet.getContent().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return sortByDate(tweets);
    }

    //utility functions

    public void sanitizeData(){
        List<Tweet> tweets = sqLiteRepository.findAll();
        HashSet<Object> seen = new HashSet<>();
        tweets.forEach(e -> {
            if (!seen.add(e.getContent())) {
                sqLiteRepository.deleteById(e.getId());
            }
        });
    }

    private void storeTweets(QueryResult result) {
        for (Status status : result.getTweets()) {
            Tweet newTweet = new Tweet();
            newTweet.setName(status.getUser().getName());
            newTweet.setScreen_name(status.getUser().getScreenName());
            newTweet.setDate(format.format(status.getCreatedAt()));
            newTweet.setContent(status.getText());
            sqLiteRepository.save(newTweet);
        }
    }

    private List<Tweet> sortByDate(List<Tweet> tweets){
        tweets.sort(Comparator.comparing(tweet -> {
            try {
                return format.parse(tweet.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }));
        Collections.reverse(tweets);
        return tweets;
    }

}

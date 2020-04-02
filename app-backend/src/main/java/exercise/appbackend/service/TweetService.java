package exercise.appbackend.service;

import exercise.appbackend.model.Tweet;
import exercise.appbackend.repositories.SQLiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TweetService{

    @Autowired
    private Environment env;

    @Autowired
    private SQLiteRepository sqLiteRepository;

    Twitter twitter;

    SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:MM:ss");

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
    }

    public List<Tweet> searchDatabase(String query){

        if (query.equals("All")){
            return sqLiteRepository.findAll();
        }

        return sqLiteRepository.findAll().stream()
                .filter(tweet -> tweet.getContent().contains(query.toLowerCase()) ||
                                 tweet.getContent().contains(query))
                .collect(Collectors.toList());
    }

    private void storeTweets(QueryResult result){
        for (Status status : result.getTweets()) {
            Tweet newTweet = new Tweet();
            newTweet.setName(status.getUser().getName());
            newTweet.setScreen_name(status.getUser().getScreenName());
            newTweet.setDate(format.format(status.getCreatedAt()));
            newTweet.setContent(status.getText());
            sqLiteRepository.save(newTweet);
        }
    }

}

package exercise.appbackend.controller;

import exercise.appbackend.model.Tweet;
import exercise.appbackend.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.TwitterException;

import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    TweetService ts;

    @GetMapping
    public String welcome(){
        return "Hello!";
    }

    @GetMapping("/update")
    public void updateDatabase() throws TwitterException {
        ts.updateDatabase();
    }

    @GetMapping("/search/{query}")
    public List<Tweet> searchDatabase(@PathVariable("query") String query) throws TwitterException {
        return ts.searchDatabase(query);
    }
}

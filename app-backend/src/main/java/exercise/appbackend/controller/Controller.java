package exercise.appbackend.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exercise.appbackend.model.Tweet;
import exercise.appbackend.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
        ts.printPlease();
        return "Hello!";
    }

    @GetMapping("/test")
    public String welcome2(){
        ts.printPlease2();
        return "This is the test!";
    }

    @GetMapping("/search")
    public List<Tweet> search() throws TwitterException {
        return ts.searchTwitter();
    }

}

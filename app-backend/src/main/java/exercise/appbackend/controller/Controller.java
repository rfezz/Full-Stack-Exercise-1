package exercise.appbackend.controller;

import exercise.appbackend.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.TwitterException;

@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    TweetService ts;

    @GetMapping
    public void welcome(){
        ts.printPlease();
    }

    @GetMapping("/test")
    public void welcome2(){
        ts.printPlease2();
    }

    @GetMapping("/search")
    public void search() throws TwitterException {
        ts.searchTwitter();
    }
}

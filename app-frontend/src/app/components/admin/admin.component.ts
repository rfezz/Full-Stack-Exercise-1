import { Component, OnInit } from '@angular/core';
import { TweetService } from '../../services/tweet.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  public tweets;

  constructor(private tweetService: TweetService) { }

  ngOnInit(): void {
    this.getTweets();
  }

  getTweets() {
    this.tweetService.getFilteredTweets("All").subscribe(
      data => { this.tweets = data},
      err => console.error(err),
      () => console.log('tweets loaded')
    );
  }

}

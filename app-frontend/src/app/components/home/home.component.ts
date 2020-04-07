import { Component, OnInit } from '@angular/core';
import { TweetService } from '../../services/tweet.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public tweets;
  public lastSelection = "All";
  public lastUpdate;

  searchOptions: string[] = [
    'All',
    'Support',
    'Resistance'
  ]
  confirmationMessage: string = "";

  constructor(private tweetService: TweetService) {
    this.tweets = [];
   }

  ngOnInit(): void {
    this.getTweets();
  }

  submitChoice(optionValue) {
    this.tweetService.getFilteredTweets(optionValue).subscribe(
      data => {
        this.tweets = data;
        return true;
      },
      error => {
        return Observable.throw(error);
      }
    );
  }

  getTweets() {
    this.tweetService.getFilteredTweets("All").subscribe(
      data => { this.tweets = data},
      err => console.error(err),
      () => {
        this.lastUpdate = this.tweets[0].date;
      }
    );

  }

  updatePage(){
    
    this.tweetService.updateTweets().subscribe(
      data => data,
      err => console.error(err),
      () => {
        this.lastUpdate = this.tweets[0].date;
        this.getTweets();
      }
    );

  }

}

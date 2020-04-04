import { Component, OnInit } from '@angular/core';
import { TweetService } from '../../services/tweet.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
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
  optionform: FormGroup;
  confirmationMessage: string = "";

  constructor(private tweetService: TweetService) { }

  ngOnInit(): void {
    this.optionform = new FormGroup({
      searchOption: new FormControl('', Validators.required)
    });

    this.getTweets();

  }

  submitChoice() {
    this.confirmationMessage = "You have selected " + this.optionform.get('searchOption').value;
    this.tweetService.getFilteredTweets(this.optionform.get('searchOption').value).subscribe(
      data => {
        this.tweets = data;
        this.lastSelection = this.optionform.get('searchOption').value;
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
        this.confirmationMessage = "Now showing:  " + this.lastSelection 
        + ". Last updated: " + this.lastUpdate;
      }
    );

  }

  updatePage(){
    
    this.tweetService.updateTweets().subscribe();

    this.getTweets();

  }

}

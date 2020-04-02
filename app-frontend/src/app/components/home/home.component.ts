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
  }

  submitChoice() {
    this.confirmationMessage = "You have selected " + this.optionform.get('searchOption').value;
    this.tweetService.getFilteredTweets(this.optionform.get('searchOption').value).subscribe(
      data => {
        this.tweets = data;
        return true;
      },
      error => {
        return Observable.throw(error);
      }
    )
  }

}

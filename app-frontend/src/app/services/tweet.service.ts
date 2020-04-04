import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class TweetService {

  constructor(private http:HttpClient) { }

  getFilteredTweets(selection : string){
    return this.http.get('/server/search/' + selection);
  }

  updateTweets(){
    return this.http.get('/server/update');
  }
}

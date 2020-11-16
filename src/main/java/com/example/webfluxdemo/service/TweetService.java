package com.example.webfluxdemo.service;

import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.repository.TweetRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    public Flux<Tweet> getAll(){
        return tweetRepository.findAll();
    }

    public Mono<Boolean> existById(String tweetId){
        return tweetRepository.existsById(tweetId);
    }

    public Mono<Tweet> save(Tweet tweet){
        return tweetRepository.save(tweet);
    }

    public Mono<Tweet> findById(String tweetId){
        return tweetRepository.findById(tweetId);
    }

    public Mono<Tweet> updateTweet(String tweetId, Tweet tweet){
        return findById(tweetId)
                .flatMap(existingTweet -> {
                    existingTweet.setText(tweet.getText());
                    return save(existingTweet);
                });
    }

    public Mono<Void> deleteTweet(String tweetId){
        return findById(tweetId)
                .flatMap(existingTweet -> tweetRepository.delete(existingTweet));
    }

    public Flux<Tweet> streamAllTweet(){
       return tweetRepository.findAll();
    }
}

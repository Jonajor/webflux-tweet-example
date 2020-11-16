package com.example.webfluxdemo;

import com.example.webfluxdemo.model.Tweet;
import com.example.webfluxdemo.repository.TweetRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebfluxDemoApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	TweetRepository tweetRepository;

	@Test
	public void testCreateTweet() {
		Tweet tweet = Tweet.builder().text("This is a Test Tweet").build();

		webTestClient.post().uri("/tweets")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(tweet), Tweet.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.text").isEqualTo("This is a Test Tweet");
	}

	@Test
	public void testGetAllTweets() {
		webTestClient.get().uri("/tweets")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBodyList(Tweet.class);
	}

	@Test
	public void testGetSingleTweet() {
		Tweet tweet = Tweet.builder().text("Hello, World!").build();
		tweetRepository.save(tweet);
		webTestClient.get()
				.uri("/tweets/{id}", Collections.singletonMap("id", tweet.getId()))
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.consumeWith(response ->
						Assertions.assertThat(response.getResponseBody()).isNotNull());
	}

	@Test
	public void testUpdateTweet() {

		Tweet tweet = tweetRepository.save(Tweet.builder().text("Initial Tweet").build()).block();
		Tweet newTweetData = Tweet.builder().text("Updated Tweet").build();

		webTestClient.put()
				.uri("/tweets/{id}", Collections.singletonMap("id", tweet.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(newTweetData), Tweet.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.text").isEqualTo("Updated Tweet");
	}

	@Test
	public void testDeleteTweet() {
		Tweet tweet = tweetRepository.save(Tweet.builder().id("teste").text("To be deleted").build()).block();
		webTestClient.delete()
				.uri("/tweets/{id}", Collections.singletonMap("id",  tweet.getId()))
				.exchange()
				.expectStatus().isOk();
	}
}

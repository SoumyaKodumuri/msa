package com.p2.microservice.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.p2.microservice.dto.ReviewRequest;
import com.p2.microservice.dto.ReviewResponse;
import com.p2.microservice.service.ReviewService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
	
	private final ReviewService reviewService;
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	@CircuitBreaker(name = "review-service", fallbackMethod = "createReviewFallback")
	@Retry(name = "review-service")
//	@TimeLimiter(name = "review-service")
	public void createReview(@RequestBody ReviewRequest reviewRequest) {
		reviewService.createReview(reviewRequest);
	}
	
	public void createReviewFallback(ReviewRequest reviewRequest, Throwable t) {
        log.error("Fallback: Unable to create review. Error: {}", t.getMessage());
    }
	
	
	@GetMapping("/all_reviews")
	@ResponseStatus(HttpStatus.OK)
	@CircuitBreaker(name = "review-service", fallbackMethod = "getAllReviewsFallback")
	@Retry(name = "review-service")
	public List<ReviewResponse> getAllReviews() {
		return reviewService.getAllReviews();
	}
	
	public List<ReviewResponse> getAllReviewsFallback(Throwable t) {
        log.error("Fallback: Unable to get all reviews. Error: {}", t.getMessage());
        return Collections.emptyList();
    }
	
	
	@GetMapping("/buyer/{userId}")
	@ResponseStatus(HttpStatus.OK)
	@CircuitBreaker(name = "review-service", fallbackMethod = "getReviewsByBuyerIdFallback")
	@Retry(name = "review-service")
	public List<ReviewResponse> getAllReviewsByBuyerId(@PathVariable String userId) {
		return reviewService.getAllReviewsByBuyerId(userId);
	}
	
	public List<ReviewResponse> getReviewsByBuyerIdFallback(String userId, Throwable t) {
        log.error("Fallback: Unable to get reviews for buyer {}. Error: {}", userId, t.getMessage());
        return Collections.emptyList();
    }
	
	
	@GetMapping("/product/{productId}")
	@ResponseStatus(HttpStatus.OK)
	@CircuitBreaker(name = "review-service", fallbackMethod = "getReviewsByProductIdFallback")
	@Retry(name = "review-service")
	public List<ReviewResponse> getAllReviewsByProductId(@PathVariable long productId) {
		return reviewService.getAllReviewsByProductId(productId);
	}
	
	public List<ReviewResponse> getReviewsByProductIdFallback(String productId, Throwable t) {
		log.error("Fallback: Unable to get reviews for product {}. Error: {}", productId, t.getMessage());
		return Collections.emptyList();
	}
	
	
	@PutMapping("/{reviewId}")
	@ResponseStatus(HttpStatus.OK)
	@CircuitBreaker(name = "review-service", fallbackMethod = "updateReviewFallback")
	@Retry(name = "review-service")
	public ReviewResponse updateReview(@PathVariable long reviewId, @RequestBody ReviewRequest reviewRequest) {
		return reviewService.updateReview(reviewId, reviewRequest);
	}
	
	public ReviewResponse updateReviewFallback(String reviewId, ReviewRequest reviewRequest, Throwable t) {
        log.error("Fallback: Unable to update review {}. Error: {}", reviewId, t.getMessage());
        return null;
    }
	
	
	@DeleteMapping("/{reviewId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CircuitBreaker(name = "review-service", fallbackMethod = "deleteReviewFallback")
	@Retry(name = "review-service")
	public void deleteReview(@PathVariable long reviewId) {
		reviewService.deleteReview(reviewId);
	}
	
	public boolean deleteReviewFallback(String reviewId, Throwable t) {
        log.error("Fallback: Unable to delete review {}. Error: {}", reviewId, t.getMessage());
        return false;
    }
	
	
//	public String fallbackResponse(ReviewRequest reviewRequest, RuntimeException runtimeException) {
//		return "Something went wrong! Please try again later.";
//	}

}

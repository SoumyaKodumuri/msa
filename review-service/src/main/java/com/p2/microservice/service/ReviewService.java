package com.p2.microservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.p2.microservice.dto.ReviewRequest;
import com.p2.microservice.dto.ReviewResponse;
import com.p2.microservice.model.Review;
import com.p2.microservice.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
	
	private final ReviewRepository reviewRepository;
	
	public void createReview(ReviewRequest reviewRequest) {
		Review review = Review.builder()
				.rating(reviewRequest.getRating())
				.comment(reviewRequest.getComment())
				.reviewtimestamp(reviewRequest.getReviewtimestamp())
				.userId(reviewRequest.getUserId())
				.productId(reviewRequest.getProductId())
				.build();
		
		reviewRepository.save(review);
		log.info("Review {} is saved", review.getReviewId());
	}
	
	public List<ReviewResponse> getAllReviews() {
		List<Review> reviews = reviewRepository.findAll();
		
		return reviews.stream().map(review -> mapToReviewResponse(review)).toList();
	}
	
	private ReviewResponse mapToReviewResponse(Review review) {
		return ReviewResponse.builder()
				.reviewId(review.getReviewId())
				.userId(review.getUserId())
				.productId(review.getProductId())
				.rating(review.getRating())
				.comment(review.getComment())
				.reviewtimestamp(review.getReviewtimestamp())
				.build();
	}
	
	public List<ReviewResponse> getAllReviewsByBuyerId(String userId) {
		List<Review> reviews = reviewRepository.findByUserId(userId);
		
		return reviews.stream().map(review -> mapToReviewResponse(review)).toList();
	}
	
	public List<ReviewResponse> getAllReviewsByProductId(long productId) {
		List<Review> reviews = reviewRepository.findByProductId(productId);
		
		return reviews.stream().map(review -> mapToReviewResponse(review)).toList();
	}
	
	public ReviewResponse updateReview(long reviewId, ReviewRequest reviewRequest) {
		Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
		
		if(reviewOptional.isPresent()) {
			Review review = reviewOptional.get();
			review.setRating(reviewRequest.getRating());
			review.setComment(reviewRequest.getComment());
			review.setReviewtimestamp(reviewRequest.getReviewtimestamp());
			
			Review updatedReview = reviewRepository.save(review);
			
			log.info("Review {} is updated", updatedReview.getReviewId());
			
			return mapToReviewResponse(updatedReview);
		}
		else {
			log.error("Review with id {} not found", reviewId);
			
			return null;
		}
	}
	
	public void deleteReview(long reviewId) {
		if(reviewRepository.existsById(reviewId)) {
			reviewRepository.deleteById(reviewId);
			log.info("Review {} is deleted", reviewId);
		}
		else {
			log.warn("Review with id {} not found", reviewId);
		}
	}

}

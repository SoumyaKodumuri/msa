//package com.p2.microservice;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.p2.microservice.dto.ReviewRequest;
//import com.p2.microservice.dto.ReviewResponse;
//import com.p2.microservice.model.Review;
//import com.p2.microservice.repository.ReviewRepository;
//import com.p2.microservice.service.ReviewService;
//
//class ReviewServiceTest {
//
//    @Mock
//    private ReviewRepository reviewRepository;
//
//    @InjectMocks
//    private ReviewService reviewService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testCreateReview() {
//        ReviewRequest request = new ReviewRequest(5, "Great product", LocalDateTime.now(), 1, 1);
//        Review savedReview = new Review(1, 5, "Great product", LocalDateTime.now(), 1, 1);
//
//        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);
//
//        reviewService.createReview(request);
//
//        verify(reviewRepository, times(1)).save(any(Review.class));
//    }
//
//    @Test
//    void testGetAllReviews() {
//        List<Review> reviews = Arrays.asList(
//            new Review(1, 5, "Great", LocalDateTime.now(), 1, 1),
//            new Review(2, 4, "Good", LocalDateTime.now(), 2, 2)
//        );
//
//        when(reviewRepository.findAll()).thenReturn(reviews);
//
//        List<ReviewResponse> result = reviewService.getAllReviews();
//
//        assertEquals(2, result.size());
//        assertEquals("1", result.get(0).getReviewid());
//        assertEquals("2", result.get(1).getReviewid());
//    }
//
//    @Test
//    void testGetAllReviewsByBuyerId() {
//        List<Review> reviews = Arrays.asList(
//            new Review(1, 5, "Great", LocalDateTime.now(), 1, 1),
//            new Review(2, 4, "Good", LocalDateTime.now(), 1, 2)
//        );
//
//        when(reviewRepository.findByBuyerid(1)).thenReturn(reviews);
//
//        List<ReviewResponse> result = reviewService.getAllReviewsByBuyerId(1);
//
//        assertEquals(2, result.size());
//        assertEquals("buyer1", result.get(0).getBuyerid());
//        assertEquals("buyer1", result.get(1).getBuyerid());
//    }
//
//    @Test
//    void testGetAllReviewsByProductId() {
//        List<Review> reviews = Arrays.asList(
//            new Review(1, 5, "Great", LocalDateTime.now(), 1, 1),
//            new Review(2, 4, "Good", LocalDateTime.now(), 2, 1)
//        );
//
//        when(reviewRepository.findByProductid(1)).thenReturn(reviews);
//
//        List<ReviewResponse> result = reviewService.getAllReviewsByProductId(1);
//
//        assertEquals(2, result.size());
//        assertEquals("product1", result.get(0).getProductid());
//        assertEquals("product1", result.get(1).getProductid());
//    }
//
//    @Test
//    void testUpdateReview() {
//        Review existingReview = new Review(1, 4, "Good", LocalDateTime.now(), 1, 1);
//        ReviewRequest updateRequest = new ReviewRequest(5, "Great product", LocalDateTime.now(), 1, 1);
//
//        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existingReview));
//        when(reviewRepository.save(any(Review.class))).thenReturn(existingReview);
//
//        ReviewResponse result = reviewService.updateReview(1, updateRequest);
//
//        assertNotNull(result);
//        assertEquals(5, result.getRating());
//        assertEquals("Great product", result.getComment());
//        assertEquals("2023-01-02", result.getReviewtimestamp());
//    }
//
//    @Test
//    void testDeleteReview() {
//        when(reviewRepository.existsById(1L)).thenReturn(true);
//
//        reviewService.deleteReview(1L);
//
//        verify(reviewRepository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    void testDeleteReviewNotFound() {
//        when(reviewRepository.existsById(1L)).thenReturn(false);
//
//        assertThrows(IllegalArgumentException.class, () -> reviewService.deleteReview(1L));
//
//        verify(reviewRepository, never()).deleteById(1L);
//    }
//}
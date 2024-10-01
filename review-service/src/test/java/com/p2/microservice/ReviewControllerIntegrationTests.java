//package com.p2.microservice;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.mockito.Mockito.*;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.p2.microservice.dto.ReviewRequest;
//import com.p2.microservice.dto.ReviewResponse;
//import com.p2.microservice.repository.ReviewRepository;
//import com.p2.microservice.service.ReviewService;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class ReviewControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ReviewService reviewService;
//
//    @MockBean
//    private ReviewRepository reviewRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void testCreateReview() throws Exception {
//        ReviewRequest request = new ReviewRequest(5, "Great product", LocalDateTime.now(), 1, 1);
//
//        mockMvc.perform(post("/api/review")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isCreated());
//
//        verify(reviewService, times(1)).createReview(any(ReviewRequest.class));
//    }
//
//    @Test
//    void testGetAllReviews() throws Exception {
//        ReviewResponse review1 = new ReviewResponse(1, 5, "Great", LocalDateTime.now(), 1, 1);
//        ReviewResponse review2 = new ReviewResponse(2, 4, "Good", LocalDateTime.now(), 2, 2);
//
//        when(reviewService.getAllReviews()).thenReturn(Arrays.asList(review1, review2));
//
//        mockMvc.perform(get("/api/review"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].reviewid").value("1"))
//                .andExpect(jsonPath("$[1].reviewid").value("2"));
//    }
//
//    @Test
//    void testGetAllReviewsByBuyerId() throws Exception {
//        ReviewResponse review1 = new ReviewResponse(1, 5, "Great", LocalDateTime.now(), 1, 1);
//        ReviewResponse review2 = new ReviewResponse(2, 4, "Good", LocalDateTime.now(), 1, 2);
//
//        when(reviewService.getAllReviewsByBuyerId(1)).thenReturn(Arrays.asList(review1, review2));
//
//        mockMvc.perform(get("/api/review/buyer/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].buyerid").value(1))
//                .andExpect(jsonPath("$[1].buyerid").value(1));
//    }
//
//    @Test
//    void testGetAllReviewsByProductId() throws Exception {
//        ReviewResponse review1 = new ReviewResponse(1, 5, "Great", LocalDateTime.now(), 1, 1);
//        ReviewResponse review2 = new ReviewResponse(2, 4, "Good", LocalDateTime.now(), 2, 1);
//
//        when(reviewService.getAllReviewsByProductId(1)).thenReturn(Arrays.asList(review1, review2));
//
//        mockMvc.perform(get("/api/review/product/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].productid").value(1))
//                .andExpect(jsonPath("$[1].productid").value(1));
//    }
//
//    @Test
//    void testUpdateReview() throws Exception {
//        ReviewRequest updateRequest = new ReviewRequest(5, "Updated comment", LocalDateTime.now(), 1, 1);
//        ReviewResponse updatedReview = new ReviewResponse(1, 5, "Updated comment", LocalDateTime.now(), 1, 1);
//
//        when(reviewService.updateReview(eq(1), any(ReviewRequest.class))).thenReturn(updatedReview);
//
//        mockMvc.perform(put("/api/review/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(updateRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.reviewid").value(1))
//                .andExpect(jsonPath("$.comment").value("Updated comment"));
//    }
//
//    @Test
//    void testDeleteReview() throws Exception {
//    	when(reviewRepository.existsById(1L)).thenReturn(true);
//
//        mockMvc.perform(delete("/reviews/{id}", 1L))
//        		.andExpect(status().isNoContent());
//
//        verify(reviewRepository).deleteById(1L);
//
//
//    }
//
//    @Test
//    void testDeleteReviewNotFound() throws Exception {
//    	when(reviewRepository.existsById(1L)).thenReturn(false);
//
//    	mockMvc.perform(delete("/reviews/{id}", 1L))
//        		.andExpect(status().isNotFound());
//
//        verify(reviewRepository, never()).deleteById(anyLong());
//    }
//}
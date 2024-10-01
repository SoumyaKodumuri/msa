package com.p2.microservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p2.microservice.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

	List<Review> findByUserId(String userId);

	List<Review> findByProductId(long productId);

}

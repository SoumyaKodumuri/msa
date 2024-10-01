package com.p2.microservice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse {
	private long reviewId;
	private int rating;
	private String comment;
	private LocalDateTime reviewtimestamp;
	private long productId;
	private String userId;
}

package com.example.reviewservice.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Review {
    private Long id;
    private Long listingId;
    private Long bookingId;
    private Long reviewerId;
    private Double rating;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

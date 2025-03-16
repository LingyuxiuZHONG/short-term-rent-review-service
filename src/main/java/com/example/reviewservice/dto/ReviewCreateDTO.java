package com.example.reviewservice.dto;

import lombok.Data;

@Data
public class ReviewCreateDTO {
    private Long listingId;
    private Long bookingId;
    private Long reviewerId;
    private Double rating;
    private String content;
}

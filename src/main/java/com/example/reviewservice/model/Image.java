package com.example.reviewservice.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Image {
    private Long id;
    private Long reviewId;
    private String imageUrl;
    private LocalDateTime createdAt;

    public Image(Long reviewId, String imageUrl) {
        this.reviewId = reviewId;
        this.imageUrl = imageUrl;
    }
}

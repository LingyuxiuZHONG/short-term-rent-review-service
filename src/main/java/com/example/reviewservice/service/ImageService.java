package com.example.reviewservice.service;

import java.util.List;

public interface ImageService {
    void uploadImages(Long reviewId, List<String> fileUrls);
}

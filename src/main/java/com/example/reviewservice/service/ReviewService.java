package com.example.reviewservice.service;

import com.example.feignapi.vo.ReviewVO;
import com.example.reviewservice.dto.ReviewCreateDTO;

import java.util.List;

public interface ReviewService {
    void createReview(ReviewCreateDTO reviewCreateDTO);

    List<ReviewVO> getReviewsByListingId(Long listingId);

    void deleteReview(Long id);

}

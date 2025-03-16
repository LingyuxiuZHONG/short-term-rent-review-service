package com.example.reviewservice.mapper;


import com.example.reviewservice.model.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    void insert(Review review);

    List<Review> getReviewsByListingId(Long listingId);

    int delete(Long id);

    List<Review> getReviewsByReviewerId(Long reviewerId);

    Integer ifHaveReviewed(Long reviewerId, Long listingId);

    Double getListingRating(Long listingId);
}

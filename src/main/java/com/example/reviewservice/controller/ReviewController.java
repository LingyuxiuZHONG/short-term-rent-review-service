package com.example.reviewservice.controller;


import com.example.common.ApiResponse;
import com.example.feignapi.vo.ReviewVO;
import com.example.reviewservice.dto.ReviewCreateDTO;
import com.example.reviewservice.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping
    public ResponseEntity<ApiResponse> createReview(@RequestBody ReviewCreateDTO reviewCreateDTO) {
        reviewService.createReview(reviewCreateDTO);
        return ResponseEntity.ok(ApiResponse.success("评论创建成功"));
    }

    @GetMapping("/listings/{listingId}")
    public ResponseEntity<ApiResponse<List<ReviewVO>>> getReviewsByListingId(@PathVariable Long listingId) {
        List<ReviewVO> reviews = reviewService.getReviewsByListingId(listingId);
        return ResponseEntity.ok(ApiResponse.success("查询成功", reviews));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok(ApiResponse.success("删除成功"));

    }




}

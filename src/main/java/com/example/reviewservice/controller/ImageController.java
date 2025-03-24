package com.example.reviewservice.controller;


import com.example.common.ApiResponse;
import com.example.reviewservice.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/{reviewId}/images")
    public ResponseEntity<ApiResponse<String>> uploadImages(@PathVariable Long reviewId, @RequestParam("fileUrls") List<String> fileUrls){
        imageService.uploadImages(reviewId,fileUrls);
        return ResponseEntity.ok(ApiResponse.success("更新成功"));
    }
}

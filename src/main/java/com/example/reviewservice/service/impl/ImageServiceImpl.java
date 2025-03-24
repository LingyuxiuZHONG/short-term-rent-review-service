package com.example.reviewservice.service.impl;


import com.example.common.exception.BusinessException;
import com.example.reviewservice.mapper.ImageMapper;
import com.example.reviewservice.mapper.ReviewMapper;
import com.example.reviewservice.model.Image;
import com.example.reviewservice.model.Review;
import com.example.reviewservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageMapper imageMapper;

    private final ReviewMapper reviewMapper;

    @Override
    public void uploadImages(Long reviewId, List<String> fileUrls) {
        Review review = reviewMapper.getReviewByReviewId(reviewId);
        if(review == null){
            throw new BusinessException("评论不存在");
        }

        List<Image> list = new ArrayList<>();
        for (String url : fileUrls){
            list.add(new Image(reviewId,url));
        }

        imageMapper.insertImages(list);
    }
}

package com.example.reviewservice.service.impl;


import com.example.common.exception.BusinessException;
import com.example.feignapi.clients.BookingClient;
import com.example.feignapi.clients.ListingClient;
import com.example.feignapi.clients.UserClient;
import com.example.feignapi.vo.BookingVO;
import com.example.feignapi.vo.ListingCard;
import com.example.feignapi.vo.ReviewVO;
import com.example.reviewservice.dto.ReviewCreateDTO;
import com.example.reviewservice.mapper.ReviewMapper;
import com.example.reviewservice.model.Review;
import com.example.reviewservice.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final ListingClient listingClient;
    private final BookingClient bookingClient;
    private final UserClient userClient;


    @Override
    public void createReview(ReviewCreateDTO reviewCreateDTO) {
        log.info("开始创建评价，参数: {}", reviewCreateDTO);

        ListingCard listingCard = listingClient.checkIfListingExists(reviewCreateDTO.getListingId()).getBody().getData();
        if (listingCard == null) {
            log.warn("房源不存在，listingId: {}", reviewCreateDTO.getListingId());
            throw new BusinessException("房源不存在");
        }

        BookingVO bookingVO = bookingClient.checkIfBookingExists(reviewCreateDTO.getBookingId()).getBody().getData();
        if (bookingVO == null) {
            log.warn("订单不存在，bookingId: {}", reviewCreateDTO.getBookingId());
            throw new BusinessException("订单不存在");
        }

        Integer count = reviewMapper.ifHaveReviewed(reviewCreateDTO.getReviewerId(), reviewCreateDTO.getListingId());
        if (count != null && count > 0) {
            log.warn("租客重复评价，reviewerId: {}, listingId: {}", reviewCreateDTO.getReviewerId(), reviewCreateDTO.getListingId());
            throw new BusinessException("您已评价过该房源，不能重复评价");
        }

        Review review = new Review();
        BeanUtils.copyProperties(reviewCreateDTO, review);
        reviewMapper.insert(review);

        Double rating = reviewMapper.getListingRating(reviewCreateDTO.getListingId());
        listingClient.updateListingRating(review.getListingId(),rating);

        log.info("评价创建成功，reviewId: {}", review.getId());
    }

    @Override
    public List<ReviewVO> getReviewsByListingId(Long listingId) {
        log.info("查询房源评价，listingId: {}", listingId);

        ListingCard listingCard = listingClient.checkIfListingExists(listingId).getBody().getData();
        if (listingCard == null) {
            log.warn("房源不存在，listingId: {}", listingId);
            throw new BusinessException("房源不存在");
        }

        List<Review> reviews = reviewMapper.getReviewsByListingId(listingId);
        log.info("查询到 {} 条评价", reviews.size());

        return reviews.stream()
                .map(this::convertToReviewVO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long id) {
        log.info("删除评价，reviewId: {}", id);
        try {
            int deletedRows = reviewMapper.delete(id);
            if (deletedRows == 0) {
                log.warn("删除失败，评价不存在，reviewId: {}", id);
                throw new BusinessException("评价不存在");
            }
            log.info("评价删除成功，reviewId: {}", id);
        } catch (Exception e) {
            log.error("删除评价失败，reviewId: {}，错误信息: {}", id, e.getMessage(), e);
            throw new BusinessException("删除失败");
        }
    }

    private Double getListingRating(Long listingId) {
        return reviewMapper.getListingRating(listingId);
    }

    private ReviewVO convertToReviewVO(Review review) {
        ReviewVO reviewVO = new ReviewVO();
        BeanUtils.copyProperties(review, reviewVO);

        reviewVO.setReviewer(userClient.getUserById(review.getReviewerId()).getBody().getData());

        return reviewVO;
    }
}

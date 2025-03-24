package com.example.reviewservice.mapper;


import com.example.reviewservice.model.Image;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImageMapper {

    void insertImages(List<Image> images);
}

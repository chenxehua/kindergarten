package com.kgms.video.mapper;

import com.kgms.video.entity.VideoTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoTemplateMapper {

    VideoTemplate selectById(@Param("id") Long id);

    VideoTemplate selectByTemplateId(@Param("templateId") String templateId);

    List<VideoTemplate> selectByType(@Param("templateType") String templateType);

    List<VideoTemplate> selectAll();

    VideoTemplate selectDefault();

    int insert(VideoTemplate template);

    int updateByTemplateId(VideoTemplate template);

    int deleteByTemplateId(@Param("templateId") String templateId);
}

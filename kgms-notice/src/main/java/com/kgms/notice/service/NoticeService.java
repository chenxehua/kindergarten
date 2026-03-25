package com.kgms.notice.service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kgms.common.exception.BusinessException;
import com.kgms.common.util.IdGenerator;
import com.kgms.notice.dto.NoticeDTO;
import com.kgms.notice.dto.NoticeVO;
import com.kgms.notice.entity.Notice;
import com.kgms.notice.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeMapper noticeMapper;
    @Transactional
    public String publishNotice(NoticeDTO dto, String userId) {
        Notice notice = new Notice();
        notice.setNoticeId(IdGenerator.generateStrId());
        notice.setKgId(dto.getKgId());
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());
        notice.setNoticeType(dto.getNoticeType());
        notice.setTargetType(dto.getTargetType());
        notice.setTargetIds(dto.getTargetIds());
        notice.setPublishBy(userId);
        notice.setPublishTime(LocalDateTime.now());
        notice.setStatus(1);
        noticeMapper.insert(notice);
        // TODO: 推送消息给家长
        log.info("发布通知: {}", notice.getNoticeId());
        return notice.getNoticeId();
    }
    public List<NoticeVO> getNoticeList(String kgId, String noticeType) {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notice::getKgId, kgId);
        if (noticeType != null) wrapper.eq(Notice::getNoticeType, noticeType);
        wrapper.eq(Notice::getStatus, 1);
        wrapper.orderByDesc(Notice::getPublishTime);
        return noticeMapper.selectList(wrapper).stream().map(this::convertToVO).collect(Collectors.toList());
    }
    public NoticeVO getNoticeDetail(String noticeId) {
        Notice notice = noticeMapper.selectOne(
                new LambdaQueryWrapper<Notice>().eq(Notice::getNoticeId, noticeId)
        );
        if (notice == null) throw new BusinessException(404, "通知不存在");
        return convertToVO(notice);
    }
    @Transactional
    public void withdrawNotice(String noticeId) {
        Notice notice = noticeMapper.selectOne(
                new LambdaQueryWrapper<Notice>().eq(Notice::getNoticeId, noticeId)
        );
        if (notice == null) throw new BusinessException(404, "通知不存在");
        notice.setStatus(0);
        noticeMapper.updateById(notice);
    }
    private NoticeVO convertToVO(Notice notice) {
        NoticeVO vo = new NoticeVO();
        vo.setNoticeId(notice.getNoticeId());
        vo.setKgId(notice.getKgId());
        vo.setTitle(notice.getTitle());
        vo.setContent(notice.getContent());
        vo.setNoticeType(notice.getNoticeType());
        vo.setPublishBy(notice.getPublishBy());
        vo.setPublishTime(notice.getPublishTime());
        return vo;
    }
}

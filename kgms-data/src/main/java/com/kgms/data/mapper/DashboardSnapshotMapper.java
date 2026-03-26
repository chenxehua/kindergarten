package com.kgms.data.mapper;

import com.kgms.data.entity.DashboardSnapshot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface DashboardSnapshotMapper {

    DashboardSnapshot selectById(@Param("id") Long id);

    DashboardSnapshot selectBySnapshotId(@Param("snapshotId") String snapshotId);

    List<DashboardSnapshot> selectByKgIdAndDateRange(
            @Param("kgId") String kgId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("snapshotType") String snapshotType
    );

    int insert(DashboardSnapshot snapshot);

    int updateBySnapshotId(DashboardSnapshot snapshot);

    int deleteBySnapshotId(@Param("snapshotId") String snapshotId);
}

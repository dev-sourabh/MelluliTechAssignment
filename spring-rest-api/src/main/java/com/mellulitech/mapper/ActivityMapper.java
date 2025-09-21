package com.mellulitech.mapper;

import com.mellulitech.dto.ActivityDTO;
import com.mellulitech.model.Activity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ActivityMapper extends EntityMapper<ActivityDTO,Activity> {
}

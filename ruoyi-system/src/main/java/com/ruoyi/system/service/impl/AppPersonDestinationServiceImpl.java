package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.dto.AppPersonDestinationSaveDTO;
import com.ruoyi.system.mapper.AppPersonDestinationMapper;
import com.ruoyi.system.entity.AppPersonDestination;
import com.ruoyi.system.service.IAppPersonDestinationService;
import com.ruoyi.system.vo.AppPersonDestinationVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author mac
 */
@Service
public class AppPersonDestinationServiceImpl extends ServiceImpl<AppPersonDestinationMapper, AppPersonDestination> implements IAppPersonDestinationService {

    @Autowired
    private AppPersonDestinationMapper appPersonDestinationMapper;

    /**
     * 添加值班记录
     * @param saveDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AppPersonDestinationVO saveInfo(AppPersonDestinationSaveDTO saveDTO) {
        return Optional.ofNullable(saveDTO).map(dto -> {
            Long udId = IdWorker.getId();
            AppPersonDestination info = new AppPersonDestination();
            BeanUtils.copyProperties(saveDTO, info);
            info.setUdId(udId).setUdDate(new Date());
            appPersonDestinationMapper.insert(info);
            AppPersonDestinationVO resultDto = new AppPersonDestinationVO();
            BeanUtils.copyProperties(info, resultDto);
            return resultDto;
        }).orElse(new AppPersonDestinationVO());
    }

    @Override
    public List<AppPersonDestinationVO> listPersonDestinationVO(Long personId) {
        LocalDate now = LocalDate.now();
        QueryWrapper<AppPersonDestination> personDestinationQueryWrapper = new QueryWrapper<>();
        personDestinationQueryWrapper.eq("person_id",personId);
        personDestinationQueryWrapper.ge("ud_date",now);
        personDestinationQueryWrapper.lt("ud_date",now.plusDays(1));
        personDestinationQueryWrapper.orderByDesc("ud_date");
        List<AppPersonDestinationVO> personDestinationVOS = appPersonDestinationMapper.selectListByWrapper(personDestinationQueryWrapper);
        return personDestinationVOS;
    }
}

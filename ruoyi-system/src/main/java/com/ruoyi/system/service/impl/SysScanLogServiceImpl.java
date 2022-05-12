package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.dto.SysScanLogDTO;
import com.ruoyi.system.entity.*;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ISysScanLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 角色 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysScanLogServiceImpl extends ServiceImpl<SysScanLogMapper, SysScanLog>  implements ISysScanLogService
{
    @Autowired
    private SysScanLogMapper sysScanLogMapper;


    @Override
    public boolean saveInfo(SysScanLogDTO saveDTO) {
        SysScanLog info = new SysScanLog();
        BeanUtils.copyProperties(saveDTO, info);
        info.setCreateTime(new Date());
        return this.save(info);
    }

}

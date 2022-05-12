package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.CheckUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.dto.AppPersonQueryDTO;
import com.ruoyi.system.dto.AppPersonSaveDTO;
import com.ruoyi.system.entity.AppPerson;
import com.ruoyi.system.mapper.AppPersonMapper;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.service.AppPersonService;
import com.ruoyi.system.vo.AppPersonVO;
import com.ruoyi.system.vo.AppPersonWxVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppPersonServiceImpl extends ServiceImpl<AppPersonMapper, AppPerson> implements AppPersonService {

    private static final Logger log = LoggerFactory.getLogger(AppPersonServiceImpl.class);

    @Resource
    private AppPersonMapper appPersonMapper;

    @Autowired
    private SysDeptMapper deptMapper;
    /**
     * 添加人员地库信息
     * @param saveDTO
     * @return
     */
    @Override
    public AppPersonVO saveAppPerson(AppPersonSaveDTO saveDTO) {
        return Optional.ofNullable(saveDTO).map(dto -> {
            AppPersonVO resultDto = new AppPersonVO();
            Long personId = IdWorker.getId();
            AppPerson info = new AppPerson();
            BeanUtils.copyProperties(saveDTO, info);
            info.setCreateTime(new Date()).setPersonId(personId);
            log.info(saveDTO.getPersonName()+"===appPersonMapper.insert开始==="+DateUtils.getTime());
            appPersonMapper.insert(info);
            log.info(saveDTO.getPersonName()+"===appPersonMapper.insert结束==="+DateUtils.getTime());
            BeanUtils.copyProperties(info, resultDto);
            return resultDto;
        }).orElse(new AppPersonVO());
    }

    @Override
    public AppPerson getAppPerson(Long personId) {
        return appPersonMapper.selectById(personId);
    }

    /**
     * 通过身份证查询人员信息
     * @param idNum
     * @return
     */
    @Override
    public AppPersonVO getAppPersonByIdNum(String idNum, String mobile) {
        String startNum = null;
        String endNum = null;
        if(!CheckUtil.NullOrEmpty(idNum)){
            startNum = idNum.substring(0,4);
            endNum = idNum.substring(idNum.length()-4);
        }
        log.info(idNum+"===getAppPersonByIdNum开始==="+DateUtils.getTime());
        AppPersonVO info = appPersonMapper.getAppPersonByIdNum(startNum, endNum, mobile);
        log.info(idNum+"===getAppPersonByIdNum结束==="+DateUtils.getTime());
        if(info!=null && info.getPersonId()!=null && !CheckUtil.NullOrEmpty(idNum) && !idNum.contains("*")){
            if(!CheckUtil.NullOrEmpty(info.getIdNum()) && info.getIdNum().contains("*")){
                AppPerson po = new AppPerson();
                po.setPersonId(info.getPersonId()).setIdNum(idNum);
                log.info(idNum+"===updateById开始==="+DateUtils.getTime());
                appPersonMapper.updateById(po);
                log.info(idNum+"===updateById结束==="+DateUtils.getTime());
                info.setIdNum(idNum);
            }
        }
        return info;
    }

    /**
     * 通过手机号查询信息
     * @param queryDTO
     * @return
     */
    @Override
    public AppPersonVO getAppPerson(AppPersonQueryDTO queryDTO) {
        AppPersonVO info = new AppPersonVO();
        QueryWrapper<AppPerson> queryWrapper = new QueryWrapper<>();
        if(!CheckUtil.NullOrEmpty(queryDTO.getPersonName())){
            queryWrapper.eq("person_name",queryDTO.getPersonName());
        }
        if(!CheckUtil.NullOrEmpty(queryDTO.getMobile())){
            queryWrapper.eq("mobile",queryDTO.getMobile());
        }
        List<AppPerson> items = appPersonMapper.selectList(queryWrapper);
        if(!CheckUtil.NullOrEmpty(items)){
            BeanUtils.copyProperties(items.get(0), info);
        }
        return info;
    }

    /**
     * 查询添加人员信息
     * @param idNum
     * @param name
     * @param mobile
     * @return
     */
    @Override
    public Long getPersonInfo(Long parentPersonId,String idNum, String name, String mobile, String qrCode, String level){
        log.info(name+"===getAppPersonByIdNum开始==="+DateUtils.getTime());
        AppPersonVO info = getAppPersonByIdNum(idNum, mobile);
        log.info(name+"===getAppPersonByIdNum结束==="+DateUtils.getTime());
        if(info==null){
            System.out.println("---需要添加人员信息");
            AppPersonSaveDTO dto = new AppPersonSaveDTO();
            log.info(name+"===saveAppPerson开始==="+DateUtils.getTime());
            dto.setParentPersonId(parentPersonId).setIdNum(idNum).setMobile(mobile).setPersonName(name).setQrcode(qrCode).setQrcodeColor(level);
            AppPersonVO po = saveAppPerson(dto);
            log.info(name+"===saveAppPerson开始==="+DateUtils.getTime());
            return po.getPersonId();
        }else{
            if(!CheckUtil.NullOrEmpty(qrCode) && !CheckUtil.NullOrEmpty(level)){
                AppPerson po = new AppPerson();
                po.setPersonId(info.getPersonId()).setUpdateTime(new Date()).setQrcode(qrCode).setQrcodeColor(level).setPersonName(name);
                log.info(name+"===updateById开始==="+DateUtils.getTime());
                appPersonMapper.updateById(po);
                log.info(name+"===updateById结束==="+DateUtils.getTime());
            }else{
                AppPerson po = new AppPerson();
                po.setPersonId(info.getPersonId()).setUpdateTime(new Date()).setParentPersonId(parentPersonId).setPersonName(name);
                log.info(name+"===updateById开始==="+DateUtils.getTime());
                appPersonMapper.updateById(po);
                log.info(name+"===updateById结束==="+DateUtils.getTime());
            }
            return info.getPersonId();
        }
    }


    /**
     * 查询添加人员信息
     * @param idNum
     * @param name
     * @param mobile
     * @return
     */
    @Override
    public Long getPersonInfo(String idNum, String name, String mobile, Long deptId){
        String qrCode = "";
        String level = "";
        AppPersonVO info = getAppPersonByIdNum(idNum, mobile);
        SysDept sysDept;
        String deptName = "";
        if (deptId != null){
            sysDept = deptMapper.selectDeptById(deptId);
            if (sysDept != null){
                deptName = sysDept.getDeptName();
            }
        }
        if(info==null){
            System.out.println("---需要添加人员信息");
            AppPersonSaveDTO dto = new AppPersonSaveDTO();
            dto.setIdNum(idNum).setMobile(mobile).setPersonName(name).setQrcode(qrCode).setQrcodeColor(level).setCompany(deptName);
            AppPersonVO po = saveAppPerson(dto);
            return po.getPersonId();
        }else{
            if(!CheckUtil.NullOrEmpty(qrCode) && !CheckUtil.NullOrEmpty(level)){
                AppPerson po = new AppPerson();
                po.setPersonId(info.getPersonId()).setQrcode(qrCode).setQrcodeColor(level);
                appPersonMapper.updateById(po);
            }
            return info.getPersonId();
        }
    }

    /**
     * 查询添加人员与openId关系
     * @param personId
     * @param openId
     * @return
     */
    @Override
    public Integer addPersonOpenId(Long personId, String openId, String symptoms) {
        return appPersonMapper.insertPersonOpenId(personId, openId, symptoms);
    }

    @Override
    public PageInfo<AppPersonWxVO> getList(AppPersonQueryDTO queryDTO){
        PageHelper.startPage(queryDTO.getPageNum(),queryDTO.getPageSize(),queryDTO.getOrderBy());
        List<AppPersonWxVO> list = appPersonMapper.getList(queryDTO);
        return new PageInfo<>(list);
    }


    /**
     * 查询添加人员信息 根据姓名身份证号
     * @param idNum
     * @param name
     * @return
     */
    @Override
    public Long getPersonInfoForPublicApi(String idNum, String name){
        AppPersonVO info = getAppPersonByIdNumAndName(idNum, name);
        if(info==null){
            throw new CustomException("根据身份证号和项目未查询到预检分诊信息");
        }else{
            return info.getPersonId();
        }
    }

    /**
     * 通过身份证 姓名查询人员信息
     * @param idNum
     * @return
     */
    public AppPersonVO getAppPersonByIdNumAndName(String idNum, String name) {
        String startNum = null;
        String endNum = null;
        if(!CheckUtil.NullOrEmpty(idNum)){
            startNum = idNum.substring(0,4);
            endNum = idNum.substring(idNum.length()-4);
        }
        AppPersonVO info = appPersonMapper.getAppPersonByIdNumAndName(startNum, endNum, name);
        if(info!=null && info.getPersonId()!=null && !CheckUtil.NullOrEmpty(idNum) && !idNum.contains("*")){
            if(!CheckUtil.NullOrEmpty(info.getIdNum()) && info.getIdNum().contains("*")){
                AppPerson po = new AppPerson();
                po.setPersonId(info.getPersonId()).setIdNum(idNum);
                appPersonMapper.updateById(po);
                info.setIdNum(idNum);
            }
        }
        return info;
    }
}

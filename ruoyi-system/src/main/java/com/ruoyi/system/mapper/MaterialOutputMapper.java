package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.MaterialOutput;
import com.ruoyi.system.entity.vo.MaterialOutputVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface MaterialOutputMapper extends BaseMapper<MaterialOutput> {

    int delete(Long id);

    int insert(MaterialOutput materialOutput);

    int insertDynamic(MaterialOutput materialOutput);

    int updateDynamic(MaterialOutput materialOutput);

    int update(MaterialOutput materialOutput);

    MaterialOutput selectById(Long id);

    /**
     * 全局计算阈值 AVG
     * @param evaluateDays
     * @return
     */
    List<MaterialOutput> getSumNumber(@Param("evaluateDays") Long evaluateDays);

    Long getMaxNumber(@Param("evaluateDays") Long evaluateDays,@Param("materialName") String materialName,@Param("specifications") String specifications);


    List<MaterialOutputVo> getOutputNumber(@Param("evaluateDays") Long evaluateDays ,@Param("materialName")  String materialName,@Param("specifications") String specifications);


    MaterialOutput getOutputDate(@Param("evaluateDays") Long evaluateDays ,@Param("materialName")  String materialName,@Param("specifications") String specifications,@Param("outputDate") String outputDate);

    MaterialOutput getOneOutputAvgNumer(@Param("evaluateDays") Long evaluateDays ,@Param("materialName")  String materialName,@Param("specifications") String specifications);

    MaterialOutput getOutputSumNumber(@Param("evaluateDays") Long evaluateDays ,@Param("materialName")  String materialName,@Param("specifications") String specifications);
}

package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.dto.MaterialSuppliesStockDTO;
import com.ruoyi.system.entity.MaterialInput;
import com.ruoyi.system.entity.MaterialOutput;
import com.ruoyi.system.entity.MaterialSupplies;
import com.ruoyi.system.entity.vo.MaterialSuppliesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface MaterialSuppliesMapper extends BaseMapper<MaterialSupplies> {

    /**
     * 物资列表
     * @param kindName
     * @return
     */
    List<MaterialSupplies> getList (@Param("kindName") String kindName,@Param("materialName") String materialName);
    List<MaterialSupplies> getListOr (@Param("kindName") String kindName,@Param("materialName") String materialName);
    /**
     * 物资表添加
     * @param materialSupplies
     * @return
     */
    int insertSupplies(MaterialSupplies materialSupplies);

    /**
     * 修改
     * @param materialSupplies
     * @return
     */
    int updateSupplies(MaterialSupplies materialSupplies);

    /**
     * 修改是否发送短信
     * @param materialSupplies
     * @return
     */
    int updateSendStatus(Long id);

    /**
     * 删除
     * @param id
     * @return
     */
    int delSupplies(Long id);

    /**
     * 查询入库表数据，物资表数据来源
     * @return
     */
    List<MaterialInput> getMaterialInput();

    /**
     * 根据物资查询出库记录获取出库量
     * @param materialName
     * @param specifications
     * @return
     */
    MaterialOutput getMaterialOutput(@Param("materialName") String materialName, @Param("specifications") String specifications);

    /**
     * 根据条件查询一条
     * @param materialName
     * @param specifications
     * @return
     */
    MaterialSupplies getOne(@Param("materialName") String materialName, @Param("specifications") String specifications);


    MaterialSupplies getOneById(Long id);


    List<MaterialSuppliesVo> evaluate(@Param("materialName") String materialName);

    List<String> getWarnMaterial();

    /**
     * 大屏-物资储备/出库详情
     * @return
     */
    List<MaterialOutput> getMaterialReserveOutput(@Param("evaluateDays") Long evaluateDays);

    /**
     * 大屏-物资库存
     * @return
     */
    List<MaterialSuppliesStockDTO> getMaterialStock();

}

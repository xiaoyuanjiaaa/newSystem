package com.ruoyi.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.dto.AppHealthReportCountDTO;
import com.ruoyi.system.entity.vo.GroupCompleteNumber;
import com.ruoyi.system.entity.vo.HReport;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.common.core.domain.entity.SysDept;
import org.apache.ibatis.annotations.Select;

/**
 * 部门管理 数据层
 *
 * @author ruoyi
 */
public interface SysDeptMapper extends BaseMapper<SysDept>
{
    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @param deptCheckStrictly 部门树选择项是否关联显示
     * @return 选中部门列表
     */
    public List<Integer> selectDeptListByRoleId(@Param("roleId") Long roleId, @Param("deptCheckStrictly") boolean deptCheckStrictly);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * 根据ID查询所有子部门
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    public List<SysDept> selectChildrenDeptById(Long deptId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    public int selectNormalChildrenDeptById(Long deptId);

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int hasChildByDeptId(Long deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int checkDeptExistUser(Long deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    public SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 新增部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(SysDept dept);

    /**
     * 修改部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(SysDept dept);

    /**
     * 修改所在部门正常状态
     *
     * @param deptIds 部门ID组
     */
    public void updateDeptStatusNormal(Long[] deptIds);

    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    public int updateDeptChildren(@Param("depts") List<SysDept> depts);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);


//    @Select("select sys_dept.dept_id as deptId,sys_dept.dept_name as deptName,count(distinct sys_user.user_name) as countUser , count(distinct app_health_report.report_name) as userComplete from sys_dept\n" +
//            "    left join sys_user\n" +
//            "    on sys_dept.dept_id = sys_user.dept_id\n" +
//            "    and sys_user.del_flag = 0 and sys_user.status = 0\n" +
//            "    and sys_dept.del_flag = 0 and sys_dept.status = 0\n" +
//            "    left join app_health_report\n" +
//            "    on report_name = sys_user.user_name\n" +
//            "    and app_health_report.create_time > #{countDTO.currentDay} and app_health_report.create_time < #{countDTO.nextDay}\n" +
//            "\n" +
//            "where level = (select max(level) from sys_dept)\n" +
////            "and sys_user.del_flag = 0 and sys_user.status = 0\n" +
////            "and sys_dept.del_flag = 0 and sys_dept.status = 0\n" +
////            "and app_health_report.create_time > #{countDTO.currentDay} and app_health_report.create_time < #{countDTO.nextDay}\n" +
//            "group by sys_dept.dept_id\n" +
//            "\n")
    List<GroupCompleteNumber> groupDeptComplete(@Param("countDTO") AppHealthReportCountDTO countDTO);


    /*
    * 获取最后一层所有部门
    * */
    List<SysDept> listDepts(@Param("ew") QueryWrapper<SysDept> queryWrapper);

    List<HReport> getYesData(AppHealthReportCountDTO countDTO);

    List<SysDept> selectNoLevel();

    //通过leader_id(person_id) 获取 sys_dept
    SysDept selectDeptByLeaderId(Long leaderId);
}

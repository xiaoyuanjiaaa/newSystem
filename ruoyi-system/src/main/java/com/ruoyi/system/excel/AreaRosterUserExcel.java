package com.ruoyi.system.excel;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

@Data
public class AreaRosterUserExcel {

    /** 人员名称 */
    @Excel(name = "人员名称")
    private String nickName;

    @Excel(name = "手机号")
    private String phone;

    /** 部门名称 */
    @Excel(name = "部门名称")
    private String deptName;

    /** 部门名称 */
    @Excel(name = "工作区域")
    private String workPlaceName;

    /** 排班名称 */
    @Excel(name = "排班名称")
    private String rosterName;

    /** 排班时间 */
    @Excel(name = "排班时间")
    private String rosterTime;
}

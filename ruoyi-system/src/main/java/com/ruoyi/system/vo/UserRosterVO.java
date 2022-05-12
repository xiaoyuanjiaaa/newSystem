package com.ruoyi.system.vo;

import com.ruoyi.system.dto.RosterDTO;
import com.ruoyi.system.entity.AppRosterUser;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value = "防疫人员排班表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRosterVO extends AppRosterUser {

    //状态名字
    private String statusName;

    //班次名称
    private String rosterName;

    //职责
    private String dutyName;

    private Long deptId;

    private String deptName;

}

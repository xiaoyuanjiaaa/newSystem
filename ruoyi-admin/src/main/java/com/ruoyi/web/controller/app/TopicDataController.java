package com.ruoyi.web.controller.app;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 18:42
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.system.entity.AppHealthReport;
import com.ruoyi.system.entity.TopicData;
import com.ruoyi.system.service.IAppHealthReportService;
import com.ruoyi.system.service.ITopicDataService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/appJson")
public class TopicDataController {

    @Autowired
    private ITopicDataService iTopicDataService;

    @Autowired
    private IAppHealthReportService appHealthReportService;

    @ApiOperation("json转换")
    @PostMapping("/jsonTransform")
    public Boolean jsonTransform(@RequestBody String str) {
//        String str="[{\"detailId\":\"16\",\"templateId\":\"4\",\"columnName\":null,\"chineseName\":\"昨日核酸检测情况\",\"isCached\":null,\"columnType\":\"radio\",\"createBy\":\"\",\"createTime\":\"2021-09-07 10:00:07\",\"updateBy\":\"\",\"updateTime\":\"2021-09-07 19:24:25\",\"remark\":null,\"selectOptions\":\"[{\\\"label\\\":\\\"阴性\\\",\\\"value\\\":0},{\\\"label\\\":\\\"未采\\\",\\\"value\\\":1},{\\\"label\\\":\\\"阳性\\\",\\\"value\\\":2}]\",\"sort\":\"1\",\"isEnabled\":\"0\"},{\"detailId\":\"8\",\"templateId\":\"4\",\"columnName\":null,\"chineseName\":\"近28天内，家庭成员或同行人员是否有流行病学史或密接史？\",\"isCached\":null,\"columnType\":\"radio\",\"createBy\":\"\",\"createTime\":\"2021-09-06 08:54:58\",\"updateBy\":\"\",\"updateTime\":\"2021-09-07 19:24:25\",\"remark\":null,\"selectOptions\":\"[{\\\"label\\\":\\\"A.有\\\",\\\"value\\\":0},{\\\"label\\\":\\\"B.无\\\",\\\"value\\\":1}]\",\"sort\":\"2\",\"isEnabled\":\"0\"},{\"detailId\":\"7\",\"templateId\":\"4\",\"columnName\":null,\"chineseName\":\"近28天内，本人是否有流行病学史或密接史？\",\"isCached\":null,\"columnType\":\"radio\",\"createBy\":\"\",\"createTime\":\"2021-09-06 08:54:28\",\"updateBy\":\"\",\"updateTime\":\"2021-09-07 19:24:49\",\"remark\":null,\"selectOptions\":\"[{\\\"label\\\":\\\"A.有\\\",\\\"value\\\":0},{\\\"label\\\":\\\"B.无\\\",\\\"value\\\":1}]\",\"sort\":\"3\",\"isEnabled\":\"0\"},{\"detailId\":\"6\",\"templateId\":\"4\",\"columnName\":null,\"chineseName\":\"有无其他如：干咳、鼻塞、流涕、咽痛等呼吸道症状、腹泻等消化道症状、乏力、肌肉痛、结膜炎、嗅觉味觉减退等症状\",\"isCached\":null,\"columnType\":\"radio\",\"createBy\":\"\",\"createTime\":\"2021-09-06 08:54:01\",\"updateBy\":\"\",\"updateTime\":\"2021-09-07 19:25:01\",\"remark\":null,\"selectOptions\":\"[{\\\"label\\\":\\\"A.有\\\",\\\"value\\\":0},{\\\"label\\\":\\\"B.无\\\",\\\"value\\\":1}]\",\"sort\":\"4\",\"isEnabled\":\"0\"},{\"detailId\":\"21\",\"templateId\":\"4\",\"columnName\":null,\"chineseName\":\"在院状态\",\"isCached\":null,\"columnType\":\"radio\",\"createBy\":\"\",\"createTime\":\"2021-09-07 20:33:43\",\"updateBy\":\"\",\"updateTime\":\"2021-09-07 20:33:47\",\"remark\":null,\"selectOptions\":\"[{\\\"label\\\":\\\"今日在岗\\\",\\\"value\\\":0},{\\\"label\\\":\\\"今日休息\\\",\\\"value\\\":1},{\\\"label\\\":\\\"隔离场所\\\",\\\"value\\\":2},{\\\"label\\\":\\\"长期离院\\\",\\\"value\\\":3}]\",\"sort\":\"6\",\"isEnabled\":\"0\"}]";

//        Boolean resolve = iTopicDataService.resolve(map);
//        String str = "";
        Boolean b=false;
    /*    Boolean b = false;
        if(jsonObject.size()>1){
            for (JSONObject object : jsonObject) {
                String selectOptions = object.getString("selectOptions");
                JSONArray array = JSONArray.parseArray(selectOptions);
                b = iTopicDataService.resolveReport(array);
            }
        }

        return b;
    }*/

        return iTopicDataService.resolveReport(str);
    }

    @ApiOperation("json转换")
    @PostMapping("/jsonTransToDB")
//    @Scheduled(cron = "0 10 11 ? * *")
    public void jsonTransToDB(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格式，为年-月-日
        String date = dateFormat.format(new Date());
        iTopicDataService.remove(new QueryWrapper<TopicData>().ge("report_time",date));
        List<AppHealthReport> list = appHealthReportService.list(new QueryWrapper<AppHealthReport>().ge("report_time",date));
        for(AppHealthReport report:list){
            try{
                iTopicDataService.jsonTransToDB(report);
                continue;
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("======="+report);
            }

        }
    }
}

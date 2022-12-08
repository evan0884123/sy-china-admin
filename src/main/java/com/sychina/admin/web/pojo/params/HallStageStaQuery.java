package com.sychina.admin.web.pojo.params;

import com.sychina.admin.utils.TimeUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.Assert;
import sun.text.resources.FormatData;

import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
public class HallStageStaQuery {

    @ApiModelProperty(value = "需要查询的阶段参数, 0-周，1-月")
    @NotNull
    private Integer stage;

    @ApiModelProperty(value = "距离这周/月的数，比如：0-这个周/月，-1-上周/月")
    @NotNull
    private Integer count;

    public Long startTime() {
        Long startTime = TimeUtil.getFirstDayOfWeek();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli());
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        Assert.isTrue(this.getStage() == 1 || this.getStage() == 0, "阶段查询参数错误");
        switch (this.getStage()) {
            case 0:
                if (calendar.get(Calendar.DAY_OF_WEEK) == 1){
                    calendar.add(Calendar.DAY_OF_WEEK, -1);
                }
                calendar.set(Calendar.DAY_OF_WEEK, 2);
                calendar.add(Calendar.WEEK_OF_YEAR, this.getCount());
                startTime = calendar.getTimeInMillis();
                break;
            case 1:
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.MONTH, this.getCount());
                startTime = calendar.getTimeInMillis();
                break;
            default:
                break;
        }

        return startTime;
    }

    public Long endTime() {

        Long endTime = TimeUtil.getLastDayOfWeek();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli());
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        Assert.isTrue(this.getStage() == 1 || this.getStage() == 0, "阶段查询参数错误");
        switch (this.getStage()) {
            case 0:
                if (calendar.get(Calendar.DAY_OF_WEEK) == 1){
                    calendar.add(Calendar.DAY_OF_WEEK, -1);
                }
                calendar.set(Calendar.DAY_OF_WEEK, 2);
                calendar.add(Calendar.WEEK_OF_YEAR, this.getCount());
                calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 6);
                endTime = calendar.getTimeInMillis();
                break;
            case 1:
                calendar.set(Calendar.DAY_OF_MONTH, 0);
                calendar.add(Calendar.MONTH, this.getCount() + 1);
                endTime = calendar.getTimeInMillis();
                break;
            default:
                break;
        }

        return endTime;
    }

    public static void main(String[] args) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        HallStageStaQuery query = new HallStageStaQuery();
        query.setStage(0);
        query.setCount(0);
        System.out.println("startTime:" + format.format(query.startTime()));
        System.out.println("endTime:" + format.format(query.endTime()));
    }
}

package com.sychina.admin.web.pojo.params;

import com.sychina.admin.utils.TimeUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

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
        calendar.setTime(new Date());
        Assert.isTrue(this.getCount() == 1 || this.getCount() == 0, "阶段查询参数错误");
        switch (this.getStage()) {
            case 0:
                calendar.set(Calendar.DAY_OF_WEEK, 2);
                calendar.add(Calendar.WEEK_OF_YEAR, this.getCount());
                startTime = calendar.getTimeInMillis();
                break;
            case 1:
                calendar.set(Calendar.MONTH, 0);
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
        calendar.setTime(new Date());
        Assert.isTrue(this.getCount() == 1 || this.getCount() == 0, "阶段查询参数错误");
        switch (this.getStage()) {
            case 0:
                calendar.set(Calendar.DAY_OF_WEEK, 2);
                calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 6);
                calendar.add(Calendar.WEEK_OF_YEAR, this.getCount());
                endTime = calendar.getTimeInMillis();
                break;
            case 1:
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 0);
                calendar.add(Calendar.MONTH, this.getCount());
                endTime = calendar.getTimeInMillis();
                break;
            default:
                break;
        }

        return endTime;
    }
}

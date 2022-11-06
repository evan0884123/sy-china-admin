package com.sychina.admin.web.pojo.params.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sychina.admin.utils.LocalDateTimeHelper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Administrator
 */
@Data
public class PageQuery {

    @ApiModelProperty(value = "开始时间")
    private Long startTime;

    @ApiModelProperty(value = "结束时间")
    private Long endTime;

    @ApiModelProperty(value = "当前第几页")
    private Integer current;

    @ApiModelProperty(value = "每页条数")
    private Integer size;

    @ApiModelProperty(value = "获取分页参数", hidden = true)
    public IPage page() {
        return new Page(this.getCurrent(), this.getSize());
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime == null || startTime == 0? null : startTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime == null  || endTime == 0? null : endTime;
    }

    public void setCurrent(Integer current) {
        this.current = current == null ? 1 : current;
    }

    public void setSize(Integer size) {
        this.size = size == null ? 10 : size;
    }
}

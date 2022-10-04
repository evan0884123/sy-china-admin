package com.sychina.admin.web.pojo.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageModel<T, D> {

    /**
     * 每页数量
     */
    private Integer rows;

    /**
     * 当前页码,  从 1 开始
     */
    private Integer page;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 总条数
     */
    private Integer totalCount;

    /**
     * 辅助数据
     */
    private D data;


    /**
     * 当前页数据
     */
    private List<T> list;

    /**
     * 生成分页数据
     *
     * @param pageSize   每页多少条
     * @param pageNum    当前页码(从1开始)
     * @param list       注意: 当前页的 List 数据
     * @param totalCount 总条数
     */
    public PageModel(Integer pageSize, Integer pageNum, List<T> list, Integer totalCount) {
        this.totalCount = totalCount;
        if (pageSize == null || pageSize < 1 || pageNum == null || pageNum < 1) {
            // 不分页
            this.list = list;
        } else {
            if (this.totalCount < list.size()) {
                this.totalCount = list.size();
            }
            this.rows = pageSize;
            if (this.totalCount / this.rows >= pageNum) {
                this.page = pageNum;
            } else {
                this.page = this.totalCount / this.rows + (this.totalCount % this.rows > 0 ? 1 : 0);
            }
            this.totalPage = this.totalCount / this.rows + (this.totalCount % this.rows > 0 ? 1 : 0);
            this.list = list;
        }
    }

    public PageModel(Integer pageSize, Integer pageNum, List<T> list, Integer totalCount, D data) {
        this.totalCount = totalCount;
        this.data = data;
        if (pageSize == null || pageSize < 1 || pageNum == null || pageNum < 1) {
            // 不分页
            this.list = list;
        } else {
            if (this.totalCount < list.size()) {
                this.totalCount = list.size();
            }
            this.rows = pageSize;
            if (this.totalCount / this.rows >= pageNum) {
                this.page = pageNum;
            } else {
                this.page = this.totalCount / this.rows + (this.totalCount % this.rows > 0 ? 1 : 0);
            }
            this.totalPage = this.totalCount / this.rows + (this.totalCount % this.rows > 0 ? 1 : 0);
            this.list = list;
        }
    }

}

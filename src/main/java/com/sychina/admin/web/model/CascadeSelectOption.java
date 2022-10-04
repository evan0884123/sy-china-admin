package com.sychina.admin.web.model;

import lombok.Data;

import java.util.List;

/**
 * Description: 对应element-ui Cascader 级联选择器数据
 */
@Data
public class CascadeSelectOption {

    private String value;

    private String label;

    private List<CascadeSelectOption> children;

}

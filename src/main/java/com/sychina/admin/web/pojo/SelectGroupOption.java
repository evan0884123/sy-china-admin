package com.sychina.admin.web.pojo;

import lombok.Data;

import java.util.List;

/**
 * Description: 配合element-ui 分组
 */
@Data
public class SelectGroupOption {

    private String label;

    private String value;

    private String extension;

    private List<SelectGroupOption> children;

}

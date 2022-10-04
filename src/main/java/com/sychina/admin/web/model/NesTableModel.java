package com.sychina.admin.web.model;

import lombok.Data;

import java.util.List;

/**
 * Description: nestable结构数据
 */
@Data
public class NesTableModel {

    private String leftOption;

    private String rightOption;

    private boolean showPlus;

    private boolean showEdit;

    private boolean showTrash;

    private String value;

    private String level;

    private List<NesTableModel> children;

}

package com.sychina.admin.web.model;

import com.sychina.admin.infra.domain.Menu;
import lombok.Data;

import java.util.List;

/**
 * Description: 角色列表表格元素
 */
@Data
public class RoleTableModel {

    private Integer id;

    private String name;

    private List<Menu> menus;

    private List<SelectGroupOption> roleRegions;

}

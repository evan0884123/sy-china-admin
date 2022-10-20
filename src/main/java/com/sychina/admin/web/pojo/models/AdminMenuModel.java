package com.sychina.admin.web.pojo.models;

import com.sychina.admin.infra.domain.AdminMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class AdminMenuModel {

    @ApiModelProperty(value = "id", hidden = true)
    private Integer id;

    @ApiModelProperty(value = "菜单code")
    private String code;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单list")
    private List<Menu> adminMenus;

    public List<AdminMenuModel> convert(List<AdminMenu> adminMenuList) {

        ArrayList<AdminMenu> collect = adminMenuList.stream()
                .collect(Collectors.collectingAndThen(Collectors
                        .toCollection(() ->
                                new TreeSet<>(Comparator.comparing(AdminMenu::getParentCode))), ArrayList::new));
        Map<String, List<AdminMenu>> listMap = adminMenuList.stream().collect(Collectors.groupingBy(AdminMenu::getParentCode));

        List<AdminMenuModel> adminMenuModels = new ArrayList<>();
        collect.forEach(menuModel -> {

            List<AdminMenu> menuList = listMap.get(menuModel.getParentCode());
            List<Menu> menus = new ArrayList<>();
            menuList.forEach(menu -> {
                if (StringUtils.isBlank(menu.getCode())) {
                    return;
                }
                Menu menu1 = new Menu()
                        .setCode(menu.getCode())
                        .setName(menu.getName());

                menus.add(menu1);
            });

            AdminMenuModel adminMenuModel = new AdminMenuModel().setId(menuModel.getId())
                    .setCode(menuModel.getParentCode())
                    .setName(menuModel.getParentName()).setAdminMenus(menus);

            adminMenuModels.add(adminMenuModel);
        });

        List<AdminMenuModel> modelList = adminMenuModels.stream().sorted(Comparator.comparing(AdminMenuModel::getId)).collect(Collectors.toList());
        return modelList;
    }

    @Data
    @Accessors(chain = true)
    class Menu {

        @ApiModelProperty(value = "菜单code")
        private String code;

        @ApiModelProperty(value = "菜单名称")
        private String name;
    }
}

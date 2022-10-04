package com.sychina.admin.web;

import com.sychina.admin.service.impl.ElementUiServiceImpl;
import com.sychina.admin.web.model.SelectOption;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/ui")
@Api(tags = {"UI信息"})
public class UiController {

    private ElementUiServiceImpl elementUiServiceImpl;

    @GetMapping("/loadMenuList")
    @ApiOperation("获取菜单信息")
    public List<SelectOption> loadMenuList() {
        return elementUiServiceImpl.loadMenuList();
    }

    @GetMapping("/fetchRoleOption")
    @ApiOperation("获取角色")
    public List<SelectOption> fetchRoleOption() {
        return elementUiServiceImpl.fetchRoleOption();
    }

    @GetMapping("/fetchUserOptions")
    @ApiOperation("获取用户")
    public List<SelectOption> fetchUserOptions() {
        return elementUiServiceImpl.fetchUserOptions();
    }

    @Autowired
    public void setElementUiService(ElementUiServiceImpl elementUiServiceImpl) {
        this.elementUiServiceImpl = elementUiServiceImpl;
    }
}

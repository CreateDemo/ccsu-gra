package com.ccsu.feng.test.controller;

import com.ccsu.feng.test.domain.node.xinode.WeaponNode;
import com.ccsu.feng.test.domain.vo.PersonVO;
import com.ccsu.feng.test.domain.vo.WeaponVO;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.service.IWeaponNodeService;
import com.ccsu.feng.test.utils.PageResult;
import com.ccsu.feng.test.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author admin
 * @create 2020-02-11-21:26
 */
@RestController
@RequestMapping("/weaponNode")
public class WeaponNodeController {
    @Autowired
    private IWeaponNodeService iWeaponNodeService;


    @PostMapping("/addWeaponNode")
    public Result<WeaponVO> addWeaponNode(@RequestBody WeaponNode weapon) {
        WeaponVO node = iWeaponNodeService.addWeapon(weapon);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getMsg());
        }
    }


    @GetMapping("/getWeaponNodeByName")
    public Result<WeaponVO> getWeaponNodeByName(@Param("name") String name) {
        WeaponVO node = iWeaponNodeService.getWeaponNodeByName(name);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/findWeaponNodeById")
    public Result<Optional<WeaponNode>> findWeaponNodeById(@Param("id") Long id) {
        Optional<WeaponNode> node = iWeaponNodeService.findWeaponNodeById(id);
        if (node.isPresent()) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getMsg());
        }
    }


    @DeleteMapping("/deleteWeaponNodeById")
    public Result<Boolean> deleteWeaponNodeById(@Param("id") Long id) {
        return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),
                iWeaponNodeService.deleteWeaponById(id));
    }


    @GetMapping("/getWeaponNodeByPage")
    public Result<PageResult> getWeaponNodeByPage(@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize) {
        PageResult<WeaponVO> listWeaponNodeByPage = iWeaponNodeService.getListWeaponNodeByPage(pageIndex, pageSize);
        if (listWeaponNodeByPage != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), listWeaponNodeByPage);
        } else {
            return Result.error(ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/getListWeaponNodeByPageAndName")
    public Result<PageResult> getListWeaponNodeByPageAndName(@Param("name") String name, @Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize) {
        PageResult<WeaponVO> listWeaponNodeByPageAndName = iWeaponNodeService.getListWeaponNodeByPageAndName(name, pageIndex, pageSize);
        if (listWeaponNodeByPageAndName != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), listWeaponNodeByPageAndName);
        } else {
            return Result.error(ResultEnum.ERROR.getMsg());
        }
    }


}

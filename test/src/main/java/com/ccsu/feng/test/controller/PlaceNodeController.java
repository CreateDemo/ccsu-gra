package com.ccsu.feng.test.controller;

import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.node.xinode.PlaceNode;
import com.ccsu.feng.test.domain.vo.DeedsRelationVO;
import com.ccsu.feng.test.domain.vo.PersonVO;
import com.ccsu.feng.test.domain.vo.PlaceVO;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.service.IPlaceNodeService;
import com.ccsu.feng.test.utils.PageResult;
import com.ccsu.feng.test.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author admin
 * @create 2020-02-12-18:27
 */
@RestController
@RequestMapping("/place")
public class PlaceNodeController {
    @Autowired
    IPlaceNodeService iPlaceNodeService;


    @PostMapping("/addPlaceNode")
    public Result<PlaceNode> addPlaceNode(@RequestBody PlaceNode placeNode) {
        PlaceNode node = iPlaceNodeService.addPlaceNode(placeNode);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getMsg());
        }
    }


    @GetMapping("/getPlaceNodeByName")
    public Result<PlaceNode> getPlaceNodeByName(@Param("name") String name) {
        PlaceNode node = iPlaceNodeService.getPlaceNodeByName(name);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/findPlaceNodeById")
    public Result<Optional<PlaceNode>> findPlaceNodeById(@Param("id") Long id) {
        Optional<PlaceNode> node = iPlaceNodeService.findPlaceNodeById(id);
        if (node.isPresent()) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getMsg());
        }
    }


    @DeleteMapping("/deletePlaceNodeById")
    public Result<Boolean> deletePlaceNodeById(@Param("id") Long id) {
        return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),
                iPlaceNodeService.deletePlaceNodeById(id));
    }

    @PostMapping("/addPlaceNodeDeedsNodeRelationship")
    public Result<List<BaseRelationship>> addPlaceNodeDeedsNodeRelationship(@RequestBody DeedsRelationVO deedsRelationVO) {
        List<BaseRelationship> list = iPlaceNodeService
                .addPlaceNodeDeedsNodeRelationship(deedsRelationVO.getStartName(), deedsRelationVO.getNames());
        if (!list.isEmpty()) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), list);
        } else {
            return Result.error(ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/getPlaceNodeByPage")
    public Result<PageResult> getPlaceNodeByPage(@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize) {
        PageResult<PlaceVO> listPlaceNodeByPage = iPlaceNodeService.getListPlaceNodeByPage(pageIndex, pageSize);
        if (listPlaceNodeByPage != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), listPlaceNodeByPage);
        } else {
            return Result.error(ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/getListPlaceNodeByPageAndName")
    public Result<PageResult> getListPlaceNodeByPageAndName(@Param("name") String name, @Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize) {
        PageResult<PlaceVO> listPersonNodeByPage = iPlaceNodeService.getListPlaceNodeByPageAndName(name, pageIndex, pageSize);
        if (listPersonNodeByPage != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), listPersonNodeByPage);
        } else {
            return Result.error(ResultEnum.ERROR.getMsg());
        }
    }


}

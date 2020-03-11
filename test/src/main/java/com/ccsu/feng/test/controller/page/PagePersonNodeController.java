package com.ccsu.feng.test.controller.page;

import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.node.PersonNode;
import com.ccsu.feng.test.domain.vo.PersonNodeRelationsListVO;
import com.ccsu.feng.test.domain.vo.PersonVO;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.service.node.IBaseRelationshipService;
import com.ccsu.feng.test.service.node.IPersonNodeService;
import com.ccsu.feng.test.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author admin
 * @create 2020-03-07-18:07
 */
@RestController
@RequestMapping("/page/personNode")
public class PagePersonNodeController {
    @Autowired
    IPersonNodeService iPersonNodeService;

    @Autowired
    IBaseRelationshipService iBaseRelationshipService;

    @GetMapping("/getPersonNodeByType")
    public Result<List<PersonVO>> getPersonNodeByType(String type) {
        List<PersonVO> node = iPersonNodeService.getPersonNodeByType(type);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }
    @GetMapping("/getPersonNodRelationByType")
    public Result<List<PersonNodeRelationsListVO>> getPersonNodRelationByType(String type) {
        List<PersonNodeRelationsListVO>node = iBaseRelationshipService.getPersonNodRelationByType(type);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }


    @GetMapping("/getPersonNodRelationByName")
    public Result<List<PersonNodeRelationsListVO>> getPersonNodRelationByName(String name) {
        List<PersonNodeRelationsListVO>node = iBaseRelationshipService.getPersonNodRelationByName (name);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/getPersonNodeByName")
    public Result<PersonVO> getPersonNodeByName(String name) {
        PersonVO node = iPersonNodeService.getPersonRelationByName(name);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
        }
    }

    @GetMapping("/getPersonNodeLikeByName")
    public Result<List<PersonNodeRelationsListVO> > getPersonNodeLikeByName(String name,String relationType) {
        List<PersonNodeRelationsListVO> node = iPersonNodeService.getPersonNodeLikeByName(name,relationType);
        if (node != null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), node);
        } else {
            return Result.error(ResultEnum.NOT_DATA.getCode(),ResultEnum.NOT_DATA.getMsg());
        }
    }


}

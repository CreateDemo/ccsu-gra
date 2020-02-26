package com.ccsu.feng.test.service.node.impl;

import com.ccsu.feng.test.domain.base.BaseNode;
import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.vo.BaseRelationVO;
import com.ccsu.feng.test.domain.vo.ListRelationVO;
import com.ccsu.feng.test.domain.vo.RelationVO;
import com.ccsu.feng.test.repository.BaseNodeRepository;
import com.ccsu.feng.test.service.node.IBaseNodeService;
import com.ccsu.feng.test.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author admin
 * @create 2020-02-23-13:34
 */
@Service
@Slf4j
public class BaseNodeServiceImpl implements IBaseNodeService {
    @Autowired
    BaseNodeRepository baseNodeRepository;


    @Override
    public List<Map<String, String>> findBaseNodeName(String type) {
        List<Map<String, String>> list = new ArrayList<>();
        List<BaseNode> baseNodeName = baseNodeRepository.getBaseNodeName(type);
        for (BaseNode baseNode : baseNodeName) {
            Map<String, String> map = new HashMap<>();
            map.put("value", baseNode.getName());
            list.add(map);
        }
        return list;
    }

    @Override
    public BaseNode getBaseNodeByName(String name) {
        return baseNodeRepository.getBaseNodeByName(name);
    }


    @Override
    public BaseRelationVO findBaseRelationshipByName(String name) {
        BaseNode node = baseNodeRepository.getBaseNodeByName(name);
        BaseRelationVO baseRelationVO = new BaseRelationVO(node);
        List<RelationVO> relationVOs = new ArrayList<>();
        Set<BaseRelationship> relationships = node.getRelationships();
        if (relationships.size() > 0) {
            for (BaseRelationship baseRelationship : relationships) {
                RelationVO relationVO = new RelationVO();
                relationVO.setName(baseRelationship.getName());
                relationVO.setId(baseRelationship.getEnd().getId());
                relationVOs.add(relationVO);
            }
        }
        baseRelationVO.setRelationVOs(relationVOs);
        return baseRelationVO;
    }

    @Override
    public PageResult<ListRelationVO> findBaseRelationshipByNameByPage(String name, int pageIndex, int pageSize) {
        BaseNode node = baseNodeRepository.getBaseNodeByName(name);
        if (node==null){
            return null;
        }
        List<ListRelationVO> list = new ArrayList<>();
        Set<BaseRelationship> relationships = node.getRelationships();
        if (relationships.size() > 0) {
            for (BaseRelationship baseRelationship : relationships) {
                ListRelationVO listRelationVO = new ListRelationVO(baseRelationship);
                list.add(listRelationVO);
            }
        }
        log.info("pageIndex->{},pageSize->{}", pageIndex, pageSize);
        return new PageResult<>(pageIndex, pageSize, (long) list.size(), list);
    }

}

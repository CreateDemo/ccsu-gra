package com.ccsu.feng.test.service.impl;

import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.node.xinode.DeedsNode;
import com.ccsu.feng.test.domain.node.xinode.PersonNode;
import com.ccsu.feng.test.domain.node.xinode.PlaceNode;
import com.ccsu.feng.test.domain.vo.PersonVO;
import com.ccsu.feng.test.domain.vo.PlaceVO;
import com.ccsu.feng.test.enums.RelationsType;
import com.ccsu.feng.test.repository.PlaceNodeRepository;
import com.ccsu.feng.test.service.IBaseRelationshipService;
import com.ccsu.feng.test.service.IDeedsNodeService;
import com.ccsu.feng.test.service.IPlaceNodeService;
import com.ccsu.feng.test.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author admin
 * @create 2020-02-12-17:45
 */
@Service
@Slf4j
public class PlaceNodeServiceImpl implements IPlaceNodeService {

    @Autowired
    PlaceNodeRepository placeNodeRepository;

    @Autowired
    IDeedsNodeService iDeedsNodeService;

    @Autowired
    IBaseRelationshipService relationshipService;


    @Override
    public PlaceNode addPlaceNode(PlaceNode placeNode) {
        log.info("placeNode 对象为->{}", placeNode);
        if (placeNode == null) {
            return null;
        }
        log.info("保存成功 -{}", placeNode);
        return placeNodeRepository.save(placeNode);
    }

    @Override
    public PlaceNode addPlaceNodeByName(String name) {
        log.info("需要添加的地点名称为 ->{}", name);
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        PlaceNode placeNode = new PlaceNode();
        placeNode.setName(name);
        PlaceNode node = placeNodeRepository.save(placeNode);
        log.info("保存成功 -{}", node);
        return node;
    }


    @Override
    public PlaceNode getPlaceNodeByName(String name) {
        log.info("需要查找的名称为 ->{}", name);
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        PlaceNode node = placeNodeRepository.getPlaceNodeByName(name);
        log.info("查找结果为->{}", node);
        return node;
    }


    @Override
    public boolean deletePlaceNodeById(Long id) {
        if (id == null) {
            return false;
        }
        placeNodeRepository.deleteById(id);
        log.info("id->{} 删除成功", id);
        return true;
    }

    @Override
    public Optional<PlaceNode> findPlaceNodeById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        Optional<PlaceNode> node = placeNodeRepository.findById(id);
        if (!node.isPresent()) {
            return Optional.empty();

        }
        log.info("id->{},查找结果为->{}", id, node.get());
        return node;

    }


    @Override
    public List<BaseRelationship> addPlaceNodeDeedsNodeRelationship(String startName, Set<String> names) {
        List<BaseRelationship> list = new ArrayList<>();
        log.info("地点名称 ->startName为->{}", startName);
        PlaceNode startPlaceNode = getPlaceNodeByName(startName);
        log.info("地点查找结果为->{}", startPlaceNode);
        if (startPlaceNode == null) {
            startPlaceNode = addPlaceNodeByName(startName);
        }

        for (String name : names) {
            list.add(addPlaceNodeDeedsNodeRelationship(startPlaceNode, name));
        }
        return list;
    }


    @Override
    public PageResult<PlaceVO> getListPlaceNodeByPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Page<PlaceNode> page = placeNodeRepository.findAll(pageable);
        List<PlaceVO> list = new ArrayList<>();
        for (PlaceNode placeNode : page.getContent()) {
            list.add(new PlaceVO(placeNode));
        }
        log.info("pageIndex->{},pageSize->{}", pageIndex, pageSize);
        return new PageResult<>(pageIndex, pageSize, placeNodeRepository.getPlaceNodeCount(), list);
    }

    @Override
    public Long getPersonNodeCount() {
        return placeNodeRepository.getPlaceNodeCount();
    }

    @Override
    public PageResult<PlaceVO> getListPlaceNodeByPageAndName(String name, int pageIndex, int pageSize) {
        List<PlaceNode> page = placeNodeRepository.getListPlaceNodeeByPageAndName(name, pageIndex - 1, pageSize);
        List<PlaceVO> list = new ArrayList<>();
        for (PlaceNode placeNode : page) {
            list.add(new PlaceVO(placeNode));
        }
        log.info("pageIndex->{},pageSize->{}", pageIndex, pageSize);
        return new PageResult<>(pageIndex, pageSize, (long) page.size(), list);
    }

    private BaseRelationship addPlaceNodeDeedsNodeRelationship(PlaceNode startPlaceNode, String endName) {
        DeedsNode endDeedsNode = iDeedsNodeService.getDeedsNodeByName(endName);
        log.info("事迹名称->{}", endName);
        if (endDeedsNode != null) { // 1.人物节点存在
            BaseRelationship relationship = relationshipService
                    .findRelationshipByStarNameAndEndName(RelationsType.PLACE_REF.getRelation(), endDeedsNode.getName(), endName);
            if (relationship != null) {
                return relationship;
            }
        } else {
            endDeedsNode = iDeedsNodeService.addDeedsNodeByName(endName);
        }
        BaseRelationship relationship = relationshipService
                .addRelationship(RelationsType.DEEDS_REF_PERSON.getRelation(), endDeedsNode, endDeedsNode);
        Set<BaseRelationship> relationships = startPlaceNode.getRelationships();
        if (relationships == null) {
            relationships = new HashSet<>();
        }
        relationships.add(relationship);
        startPlaceNode.setRelationships(relationships);
        placeNodeRepository.save(startPlaceNode);
        return relationship;
    }
}

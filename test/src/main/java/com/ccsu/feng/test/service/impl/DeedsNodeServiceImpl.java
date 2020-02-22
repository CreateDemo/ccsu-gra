package com.ccsu.feng.test.service.impl;

import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.node.xinode.DeedsNode;
import com.ccsu.feng.test.domain.node.xinode.PersonNode;
import com.ccsu.feng.test.domain.vo.DeedsVO;
import com.ccsu.feng.test.domain.vo.PersonVO;
import com.ccsu.feng.test.enums.RelationsType;
import com.ccsu.feng.test.repository.DeedsNodeRepository;
import com.ccsu.feng.test.service.IBaseRelationshipService;
import com.ccsu.feng.test.service.IDeedsNodeService;
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
 * @create 2020-02-12-16:53
 */
@Service
@Slf4j
public class DeedsNodeServiceImpl implements IDeedsNodeService {

    @Autowired
    DeedsNodeRepository deedsNodeRepository;

    @Autowired
    PersonNodeServiceImpl personNodeService;

    @Autowired
    IBaseRelationshipService relationshipService;


    @Override
    public DeedsNode addDeedsNode(DeedsNode deedsNode) {
        if (deedsNode == null) {
            return null;
        }

        return deedsNodeRepository.save(deedsNode);
    }


    @Override
    public DeedsNode getDeedsNodeByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }

        return deedsNodeRepository.getDeedsNodeByName(name);
    }


    @Override
    public Optional<DeedsNode> findDeedsNodeById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return deedsNodeRepository.findById(id);
    }

    @Override
    public DeedsNode addDeedsNodeByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        DeedsNode deedsNode = new DeedsNode();
        deedsNode.setName(name);
        return deedsNodeRepository.save(deedsNode);

    }

    @Override
    public boolean deleteDeedsNode(Long id) {
        deedsNodeRepository.deleteById(id);
        return true;
    }


    @Override
    public List<BaseRelationship> addDeedsPersonRelationship(String startName, Set<String> names) {
        List<BaseRelationship> list = new ArrayList<>();
        DeedsNode startDeedsNode = getDeedsNodeByName(startName);
        if (startDeedsNode == null) {
            startDeedsNode = addDeedsNodeByName(startName);
        }

        for (String name : names) {
            list.add(addaddDeedsPersonRelationship(startDeedsNode, name));
        }
        return list;
    }

    @Override
    public PageResult<DeedsVO> getListDeedsNodeByPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Page<DeedsNode> page = deedsNodeRepository.findAll(pageable);
        List<DeedsVO> list = new ArrayList<>();
        for (DeedsNode deedsNode : page.getContent()) {
            list.add(new DeedsVO(deedsNode));
        }
        log.info("pageIndex->{},pageSize->{}", pageIndex, pageSize);
        return new PageResult<>(pageIndex, pageSize, deedsNodeRepository.getDeedsNodeCount(), list);
    }

    @Override
    public Long getDeedsNodeCount() {
        return deedsNodeRepository.getDeedsNodeCount();
    }

    @Override
    public PageResult<DeedsVO> getListDeedsNodeByPageAndName(String name, int pageIndex, int pageSize) {
        List<DeedsNode> page = deedsNodeRepository.getListDeedsNodeByPageAndName(name, pageIndex - 1, pageSize);
        List<DeedsVO> list = new ArrayList<>();
        for (DeedsNode deedsNode : page) {
            list.add(new DeedsVO(deedsNode));
        }
        log.info("pageIndex->{},pageSize->{}", pageIndex, pageSize);
        return new PageResult<>(pageIndex, pageSize, (long) page.size(), list);
    }

    private BaseRelationship addaddDeedsPersonRelationship(DeedsNode startDeedsNode, String endName) {

        PersonNode endPersonNode = personNodeService.getPersonNodeByName(endName);


        if (endPersonNode != null) { // 1.人物节点存在
            BaseRelationship relationship = relationshipService
                    .findRelationshipByStarNameAndEndName(RelationsType.DEEDS_REF_PERSON.getRelation(), startDeedsNode.getName(), endName);
            if (relationship != null) {
                return relationship;
            }
        } else {
            endPersonNode = personNodeService.addPersonNodeByName(endName);
        }
        BaseRelationship relationship = relationshipService
                .addRelationship(RelationsType.DEEDS_REF_PERSON.getRelation(), startDeedsNode, endPersonNode);
        Set<BaseRelationship> relationships = startDeedsNode.getRelationships();
        if (relationships == null) {
            relationships = new HashSet<>();
        }
        relationships.add(relationship);
        startDeedsNode.setRelationships(relationships);
        deedsNodeRepository.save(startDeedsNode);
        deedsNodeRepository.save(startDeedsNode);
        return relationship;
    }
}

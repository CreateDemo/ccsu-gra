package com.ccsu.feng.test.service.node.impl;

import com.ccsu.feng.test.domain.base.BaseNode;
import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.node.DeedsNode;
import com.ccsu.feng.test.domain.node.PersonNode;
import com.ccsu.feng.test.domain.node.PlaceNode;
import com.ccsu.feng.test.domain.node.WeaponNode;
import com.ccsu.feng.test.domain.vo.ListRelationVO;
import com.ccsu.feng.test.repository.*;
import com.ccsu.feng.test.service.node.IBaseRelationshipService;
import com.ccsu.feng.test.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author admin
 * @create 2020-02-10-15:32
 */
@Service
@Slf4j
public class BaseRelationshipServiceImpl implements IBaseRelationshipService {

    @Autowired
    BaseRelationshipRepository relationshipRepository;
    @Autowired
    PersonNodeRepository personNodeRepository;

    @Autowired
    WeaponNodeRepository weaponNodeRepository;

    @Autowired
    DeedsNodeRepository deedsNodeRepository;

    @Autowired
    PlaceNodeRepository placeNodeRepository;

    @Autowired
    BaseNodeRepository baseNodeRepository;


    @Override
    public BaseRelationship addRelationship(BaseRelationship relationship) {
        if (relationship == null) {
            return null;
        }
        BaseRelationship baseRelationship = relationshipRepository.save(relationship);
        log.info("{} - >保存成功 ", baseRelationship);
        return baseRelationship;
    }

    @Override
    public Boolean updateRelationById(Long id,String name) {
         relationshipRepository.updateRelationById(id, name);
         return true;
    }
    @Override
    public BaseRelationship addRelationship(String name, BaseNode startNode, BaseNode endNode) {

        log.info("startNode->{},endNode->{} ", startNode, endNode);
        BaseRelationship relationship = new BaseRelationship(name, startNode, endNode);
        BaseRelationship baseRelationship = relationshipRepository.save(relationship);
        log.info("{} - >保存成功 ", baseRelationship);
        return baseRelationship;
    }

    @Override
    public boolean deleteRelationshipById(Long id) {
        if (id == null) {
            return false;
        }
        relationshipRepository.deleteById(id);
        log.info("id->{} 删除成功", id);
        return true;
    }


    @Override
    public BaseRelationship findRelationshipByStarNameAndEndName(String name, String startName, String endName) {
        log.info("startName->{},endName->{}", startName, endName);
        List<BaseRelationship> ships = relationshipRepository.findRelationshipByStarNameAndEndName(name, startName, endName);
        log.info("查找关系为{}", ships);
        if (ships != null && ships.size() > 0) {
            return ships.get(0);
        }
        return null;
    }


    @Override
    public Optional<BaseRelationship> findRelationshipById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        Optional<BaseRelationship> baseRelationship = relationshipRepository.findById(id);
        if (!baseRelationship.isPresent()) {
            return Optional.empty();

        }
        log.info("id->{},查找结果为->{}", id, baseRelationship.get());
        return baseRelationship;
    }



    @Override
    public List<Map<String, String>> findNodeName(String nodeType, String type) {
        List<Map<String, String>> list = new ArrayList<>();
        if ("personNode".equals(nodeType)) {
            List<PersonNode> nodeName = personNodeRepository.getListPersonNodeByType(type);
            for (PersonNode personNode : nodeName) {
                Map<String, String> map = new HashMap<>();
                map.put("value", personNode.getName());
                list.add(map);
            }
        }
        if ("weaponNode".equals(nodeType)) {
            List<WeaponNode> nodeName = weaponNodeRepository.getListWeaponNodeByType(type);
            for (WeaponNode weaponNode : nodeName) {
                Map<String, String> map = new HashMap<>();
                map.put("value", weaponNode.getName());
                list.add(map);
            }
        }
        if ("deedsNode".equals(nodeType)){
            List<DeedsNode> nodeName = deedsNodeRepository.getListDeedsNodeByType(type);
            for (DeedsNode deedsNode : nodeName) {
                Map<String, String> map = new HashMap<>();
                map.put("value", deedsNode.getName());
                list.add(map);
            }
        }

        if ("placeNode".equals(nodeType)){
            List<PlaceNode> nodeName = placeNodeRepository.getListPlaceNodeByType(type);
            for (PlaceNode placeNode : nodeName) {
                Map<String, String> map = new HashMap<>();
                map.put("value", placeNode.getName());
                list.add(map);
            }
        }
        return list;

    }

    @Override
    public PageResult<ListRelationVO> getListRelationByPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Page<BaseRelationship> page = relationshipRepository.findAll(pageable);
        List<ListRelationVO> list = new ArrayList<>();
        for (BaseRelationship relationship : page.getContent()) {
            ListRelationVO listRelationVO = new ListRelationVO(relationship);
            list.add(listRelationVO);
        }
        log.info("pageIndex->{},pageSize->{}", pageIndex, pageSize);
        return new PageResult<>(pageIndex, pageSize, relationshipRepository.getBaseRelationshipCount(), list);

    }
}

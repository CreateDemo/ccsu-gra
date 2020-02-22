package com.ccsu.feng.test.service.impl;

import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.domain.node.xinode.PersonNode;
import com.ccsu.feng.test.domain.node.xinode.WeaponNode;
import com.ccsu.feng.test.domain.vo.PersonVO;
import com.ccsu.feng.test.enums.RelationsType;
import com.ccsu.feng.test.repository.PersonNodeRepository;
import com.ccsu.feng.test.repository.WeaponNodeRepository;
import com.ccsu.feng.test.service.IBaseRelationshipService;
import com.ccsu.feng.test.service.IPersonNodeService;
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
 * @create 2020-02-10-16:02
 */
@Service
@Slf4j
public class PersonNodeServiceImpl implements IPersonNodeService {

    @Autowired
    PersonNodeRepository personNodeRepository;

    @Autowired
    WeaponNodeRepository weaponNodeRepository;

    @Autowired
    IBaseRelationshipService relationshipService;


    @Override
    public PersonVO addPersonNode(PersonNode person) {
        PersonNode personNode = personNodeRepository.save(person);
        PersonVO personVo = new PersonVO(personNode);
        return personVo;
    }

    @Override
    public Optional<PersonNode> findPersonNodeById(Long id) {


        if (id == null) {
            return Optional.empty();
        }
        Optional<PersonNode> personNode = personNodeRepository.findById(id);
        if (!personNode.isPresent()) {
            return null;
        }
        log.info("id->{},查找结果为->{}", id, personNode.get());
        return personNode;
    }


    @Override
    public PersonVO addPersonNodeByName(String name) {
        log.info("需要添加的人物名称为 ->{}", name);
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        PersonNode person = new PersonNode();
        person.setName(name);

        PersonNode personNode = personNodeRepository.save(person);
        PersonVO personVo = new PersonVO(personNode);
        return personVo;
    }


    @Override
    public PersonVO getPersonNodeByName(String name) {

        log.info("需要查找的名称为 ->{}", name);
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        PersonNode personNode = personNodeRepository.getPersonNodeByName(name);
        PersonVO personVo = new PersonVO(personNode);
        return personVo;
    }


    @Override
    public boolean deletePersonNode(Long id) {
        if (id == null) {
            return false;
        }
        personNodeRepository.deleteById(id);
        return true;
    }


    @Override
    public BaseRelationship addPersonNodeRelationship(String name, String startName, String endName) {
        //获取人物节点
        PersonNode startPerson = getPersonNodeByName(startName);
        PersonNode endPerson = getPersonNodeByName(endName);

        /**
         * 添加关系有如下几种情况：
         * 1.人物节点全部存在:
         *   1.1关系存在
         *   1.2关系不存在
         * 2.开始人物节点存在，结束人物节点不存在
         * 3.开始人物节点不存在，结束人物节点存在
         * 4.人物节点均不存在
         */
        if (startPerson != null && endPerson != null) { // 1.人物节点全部存在
            BaseRelationship relationship = relationshipService.findRelationshipByStarNameAndEndName(name, startName, endName);
            if (relationship != null) {
                return relationship;
            }
        } else if (startPerson != null && endPerson == null) { //2.开始人物节点存在，结束人物节点不存在
            endPerson = addPersonNodeByName(endName);
        } else if (startPerson == null && endPerson != null) { //3.开始人物节点不存在，结束人物节点存在
            startPerson = addPersonNodeByName(startName);
        } else if (startPerson == null && endPerson == null) { //4.人物节点均不存在
            startPerson = addPersonNodeByName(startName);
            endPerson = addPersonNodeByName(endName);
        }
        BaseRelationship relationship = relationshipService.addRelationship(name, startPerson, endPerson);
        //更新开始节点关系
        Set<BaseRelationship> relationships = startPerson.getRelationships();
        if (relationships == null) {
            relationships = new HashSet<>();
        }
        relationships.add(relationship);
        startPerson.setRelationships(relationships);
        personNodeRepository.save(startPerson);
        return relationship;
    }


    @Override
    public List<BaseRelationship> addTwoPersonNodeRelationship(String preName, String sufName, String startName, String endName) {
        List<BaseRelationship> list = new ArrayList<>();
        //顺向关系
        BaseRelationship baseRelationship = addPersonNodeRelationship(preName, startName, endName);
        //逆向关系
        BaseRelationship baseRelationship1 = addPersonNodeRelationship(sufName, endName, startName);
        list.add(baseRelationship);
        list.add(baseRelationship1);
        return list;
    }


    @Override
    public BaseRelationship getPersonNodeRelationship(String name, String startName, String endName) {
        return relationshipService.findRelationshipByStarNameAndEndName(name, startName, endName);
    }


    @Override
    public List<BaseRelationship> addPersonNodeWeapon(String name, String startName, String endName) {
        List<BaseRelationship> list = new ArrayList<>();
        PersonNode startNode = getPersonNodeByName(startName);
        WeaponNode endNode = weaponNodeRepository.getWeaponByName(endName);

        /**
         * 添加关系有如下几种情况：
         * 1.节点全部存在:
         *   1.1关系存在
         *   1.2关系不存在
         * 2.开始节点存在，结束节点不存在
         * 3.开始节点不存在，结束节点存在
         * 4.节点均不存在
         */
        if (startNode != null && endNode != null) { // 1.节点全部存在
            BaseRelationship relationship = relationshipService.findRelationshipByStarNameAndEndName(name, startName, endName);
            if (relationship != null) {
                list.add(relationship);
                list.add(addWeaponPerson(RelationsType.WEAPON_REF.getRelation(), endName, startName));
                return list;
            }
        } else if (startNode != null && endNode == null) { //2.开始节点存在，结束人物节点不存在
            WeaponNode weapon = new WeaponNode();
            weapon.setName(endName);
            endNode = weaponNodeRepository.save(weapon);
        } else if (startNode == null && endNode != null) { //3.开始人物节点不存在，结束人物节点存在
            startNode = addPersonNodeByName(startName);
        } else if (startNode == null && endNode == null) { //4.人物节点均不存在
            startNode = addPersonNodeByName(startName);
            WeaponNode weapon = new WeaponNode();
            weapon.setName(endName);
            endNode = weaponNodeRepository.save(weapon);
        }

        BaseRelationship relationship = relationshipService.addRelationship(name, startNode, endNode);
        //更新开始节点关系
        Set<BaseRelationship> relationships = startNode.getRelationships();
        if (relationships == null) {
            relationships = new HashSet<>();
        }
        relationships.add(relationship);
        startNode.setRelationships(relationships);
        personNodeRepository.save(startNode);
        list.add(relationship);
        list.add(addWeaponPerson(RelationsType.WEAPON_REF.getRelation(), endName, startName));
        return list;
    }

    @Override
    public PageResult<PersonVO> getListPersonNodeByPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Page<PersonNode> page = personNodeRepository.findAll(pageable);
        List<PersonVO> list = new ArrayList<>();
        for (PersonNode personNode : page.getContent()) {
            list.add(new PersonVO(personNode));
        }
        log.info("pageIndex->{},pageSize->{}", pageIndex, pageSize);
        return new PageResult<>(pageIndex, pageSize, personNodeRepository.getPersonNodeCount(), list);
    }

    @Override
    public Long getPersonNodeCount() {
        return personNodeRepository.getPersonNodeCount();
    }

    @Override
    public PageResult<PersonVO> getListPersonNodeByPageAndName(String name, int pageIndex, int pageSize) {
        List<PersonNode> page = personNodeRepository.getListPersonNodeByPageAndName(name, pageIndex - 1, pageSize);
        List<PersonVO> list = new ArrayList<>();
        for (PersonNode personNode : page) {
            list.add(new PersonVO(personNode));
        }
        log.info("pageIndex->{},pageSize->{}", pageIndex, pageSize);
        return new PageResult<>(pageIndex, pageSize, (long) page.size(), list);
    }


    private BaseRelationship addWeaponPerson(String name, String startName, String endName) {
        WeaponNode startNode = weaponNodeRepository.getWeaponByName(startName);
        PersonNode endNode = getPersonNodeByName(endName);

        if (startNode != null && endNode != null) { // 1.节点全部存在
            BaseRelationship relationship = relationshipService.findRelationshipByStarNameAndEndName(name, startName, endName);
            if (relationship != null) {
                addWeaponPerson(RelationsType.WEAPON_REF.getRelation(), endName, startName);
                return relationship;
            }
        } else if (startNode != null && endNode == null) { //2.开始节点存在，结束人物节点不存在
            endNode = addPersonNodeByName(endName);
        } else if (startNode == null && endNode != null) { //3.开始人物节点不存在，结束人物节点存在
            WeaponNode weapon = new WeaponNode();
            weapon.setName(endName);
            startNode = weaponNodeRepository.save(weapon);
        } else if (startNode == null && endNode == null) { //4.人物节点均不存在
            WeaponNode weapon = new WeaponNode();
            weapon.setName(endName);
            startNode = weaponNodeRepository.save(weapon);
            endNode = addPersonNodeByName(startName);
        }

        BaseRelationship relationship = relationshipService.addRelationship(name, startNode, endNode);
        //更新开始节点关系
        Set<BaseRelationship> relationships = startNode.getRelationships();
        if (relationships == null) {
            relationships = new HashSet<>();
        }
        relationships.add(relationship);
        startNode.setRelationships(relationships);
        weaponNodeRepository.save(startNode);
        return relationship;
    }


}

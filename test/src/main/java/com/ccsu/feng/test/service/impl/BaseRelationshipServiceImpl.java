package com.ccsu.feng.test.service.impl;

import com.ccsu.feng.test.domain.base.BaseNode;
import com.ccsu.feng.test.domain.base.BaseRelationship;
import com.ccsu.feng.test.repository.BaseRelationshipRepository;
import com.ccsu.feng.test.service.IBaseRelationshipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author admin
 * @create 2020-02-10-15:32
 */
@Service
@Slf4j
public class BaseRelationshipServiceImpl implements IBaseRelationshipService {

    @Autowired
    BaseRelationshipRepository relationshipRepository;


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
}

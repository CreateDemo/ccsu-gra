package com.ccsu.feng.test.service.node.impl;

import com.ccsu.feng.test.domain.node.WeaponNode;
import com.ccsu.feng.test.domain.vo.WeaponVO;
import com.ccsu.feng.test.repository.WeaponNodeRepository;
import com.ccsu.feng.test.service.node.IWeaponNodeService;
import com.ccsu.feng.test.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author admin
 * @create 2020-02-10-16:45
 */
@Service
@Slf4j
public class WeaponNodeServiceImpl implements IWeaponNodeService {

    @Autowired
    WeaponNodeRepository weaponRepository;

    @Override
    public WeaponVO addWeapon(WeaponNode weapon) {
        log.info("weaponNode 对象为->{}", weapon);
        if (weapon == null) {
            return null;
        }
        WeaponNode weaponNode = weaponRepository.save(weapon);
        log.info("保存成功 -{}", weaponNode);
        WeaponVO weaponVO = new WeaponVO(weaponNode);
        return weaponVO;
    }

    @Override
    public WeaponNode addWeaponByName(String name,String type) {
        log.info("需要添加的武器名称为 ->{}", name);
        WeaponNode weaponNode = new WeaponNode();
        weaponNode.setName(name);
        weaponNode.setType(type);
        weaponRepository.save(weaponNode);
        log.info("保存成功 -{}", weaponNode);
        return weaponNode;
    }

    @Override
    public WeaponVO getWeaponNodeByName(String name) {
        log.info("需要查找的名称为 ->{}", name);
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        WeaponNode weapon = weaponRepository.getWeaponByName(name);
        log.info("查找结果为->{}", weapon);
        WeaponVO weaponVO = new WeaponVO(weapon);
        return weaponVO;
    }

    @Override
    public boolean deleteWeaponById(Long id) {
        if (id == null) {
            return false;
        }
        weaponRepository.deleteById(id);
        log.info("id->{} 删除成功", id);
        return true;
    }

    @Override
    public Optional<WeaponNode> findWeaponNodeById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        Optional<WeaponNode> weaponNode = weaponRepository.findById(id);
        if (!weaponNode.isPresent()) {
            return Optional.empty();

        }
        log.info("id->{},查找结果为->{}", id, weaponNode.get());
        return weaponNode;
    }


    @Override
    public PageResult<WeaponVO> getListWeaponNodeByPage(int pageIndex, int pageSize, String type) {
        if (pageIndex <= 1) {
            pageIndex = 0;
        } else {
            pageIndex = (pageSize - 1) * pageSize;
        }
        List<WeaponNode> page = weaponRepository.getListWeaponNodeByPage(pageIndex, pageSize, type);
        List<WeaponVO> list = new ArrayList<>();
        for (WeaponNode weaponNode : page) {
            list.add(new WeaponVO(weaponNode));
        }
        log.info("pageIndex->{},pageSize->{}", pageIndex, pageSize);
        return new PageResult<>(pageIndex, pageSize, weaponRepository.getWeaponNodeCount(type), list);
    }

    @Override
    public Long getWeaponNodeCount(String type) {
        return weaponRepository.getWeaponNodeCount(type);
    }

    @Override
    public PageResult<WeaponVO> getListWeaponNodeByPageAndName(String name, int pageIndex, int pageSize, String type) {

        int pageIndexNum = 0;
        if (pageIndex > 1) {
            pageIndexNum = (pageIndex - 1) * pageSize;
        }
        List<WeaponNode> page = weaponRepository.getListWeaponNodeByPageAndName(name, pageIndexNum, pageSize, type);
        List<WeaponVO> list = new ArrayList<>();
        for (WeaponNode weaponNode : page) {
            list.add(new WeaponVO(weaponNode));
        }
        log.info("pageIndex->{},pageSize->{}", pageIndex, pageSize);
        return new PageResult<>(pageIndex, pageSize, weaponRepository.getWeaponNodeCountByName(type, name), list);
    }
}

package com.ccsu.feng.test.repository;

import com.ccsu.feng.test.domain.node.xinode.PersonNode;
import com.ccsu.feng.test.domain.node.xinode.WeaponNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @create 2020-02-10-16:04
 */
@Repository
public interface WeaponNodeRepository extends Neo4jRepository<WeaponNode, Long> {


    @Query("MATCH (n:WeaponNode) RETURN count(n)")
    Long getWeaponNodeNodeCount();

    WeaponNode getWeaponByName(String name);


    @Query("MATCH (n:WeaponNode)  where n.name Contains {name} RETURN n skip {pageIndex} limit {pageSize}")
    List<WeaponNode> getListWeaponNodeByPageAndName(@Param("name") String name,
                                                    @Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize);
}

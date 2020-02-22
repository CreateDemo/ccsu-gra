package com.ccsu.feng.test.repository;

import com.ccsu.feng.test.domain.node.xinode.PersonNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @create 2020-02-10-15:19
 */
@Repository
public interface PersonNodeRepository extends Neo4jRepository<PersonNode, Long> {

    PersonNode getPersonNodeByName(String name);

    @Query("MATCH (n:PersonNode) RETURN count(n)")
    Long getPersonNodeCount();

    @Query("MATCH (n:PersonNode)  where n.name Contains {name} RETURN n skip {pageIndex} limit {pageSize}")
    List<PersonNode> getListPersonNodeByPageAndName(@Param("name") String name,
                                                    @Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize);
}

package com.changyu.dao;

import com.changyu.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    @Query("select b from t_blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from t_blog b where b.title like ?1 or b.description like ?1")
    Page<Blog> findByQuery(Pageable pageable, String query);

    @Transactional
    @Modifying
    @Query("update t_blog b set b.viewCount = b.viewCount+1 where b.id = ?1")
    int updateViewCount(Long id);

    @Query("select function('date_format', b.updateTime, '%Y') as year from t_blog b group by year order by function('date_format', b.updateTime, '%Y')")
    List<String> findGroupYear();

    @Query("select b from t_blog b where function('date_format', b.updateTime, '%Y') = ?1")
    List<Blog> findByYear(String year);
}

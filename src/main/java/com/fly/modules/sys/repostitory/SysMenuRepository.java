package com.fly.modules.sys.repostitory;

import com.fly.modules.sys.entity.SysMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author : liufei on 2018/4/9
 */
@Repository
public interface SysMenuRepository extends JpaRepository<SysMenuEntity,Long>{
    /**
     * 根据父菜单ID查询子菜单,order_num正序展示
     * @param parentId
     * @return
     */
    List<SysMenuEntity> findAllByParentIdOrderByOrderNumAsc(Long parentId);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SysMenuEntity findByMenuId(Long id);

    /**
     * 查询不是按钮的菜单列表
     * @param type
     * @return
     */
    List<SysMenuEntity> findByTypeNot(Integer type);
}

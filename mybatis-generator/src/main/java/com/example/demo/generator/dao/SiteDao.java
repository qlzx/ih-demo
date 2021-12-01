package com.example.demo.generator.dao;

import java.util.List;

import com.example.demo.generator.model.Site;
import org.apache.ibatis.annotations.Select;

/**
 * @author lh0
 * @date 2021/9/27
 * @desc
 */
public interface SiteDao {

    @Select(value="select * from om_site")
    public List<Site> findAll();
}

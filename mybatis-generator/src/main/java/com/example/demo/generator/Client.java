package com.example.demo.generator;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.example.demo.generator.dao.SiteDao;
import com.example.demo.generator.model.Site;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author lh0
 * @date 2021/9/27
 * @desc
 */
public class Client {
    public static void main(String[] args) {

        InputStream input = null;
        SqlSessionFactory sqlSessionFactory = null;
        SqlSession sqlSession = null;
        try {
            //1.定位MyBatis的主配置文件
            input = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            //2.根据配置文件创建SqlSessionFactory
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(input);
            sqlSessionFactory.getConfiguration().addMapper(SiteDao.class);
            //3.使用SqlSessionFactory生产SqlSession
            sqlSession = sqlSessionFactory.openSession();
            SiteDao empMapper = sqlSession.getMapper(SiteDao.class);
            //4、使用SqlSession调用相应的方法完成操作
            List<Site> list = empMapper.findAll();//com.sdbairui.mapper.EmpMapper.findAll
            for (Site emp : list) {
                System.out.println(emp.getSiteCode() + " " + emp.getSiteName());
            }
            //5.提交
            sqlSession.commit();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            //如果出现了异常则回滚
            if (sqlSession != null) {
                sqlSession.rollback();//回滚
            }
        } finally {
            //6、关闭sqlSession
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
}

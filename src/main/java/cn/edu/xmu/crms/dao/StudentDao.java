package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.xml.stream.events.Comment;
import java.math.BigInteger;

/**
 * @author SongLingbing
 * @date 2018/11/29 12:35
 */
@Mapper
@Repository
public interface StudentDao {
    /**
      * 用于通过学生学号获取学生信息
      *
      * @param number 学号
      * @return Student 学生对象
      * @author SongLingbing
      * @date 2018/11/29 22:36
      */
    Student selectStudentByNumber(BigInteger number);
}
package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * @ClassName TeacherMapper
 * @Author Hongqiwu
 * @Description 有关数据库中教师信息的操作
 * @Date 2018/12/18 17:37
 **/
@Mapper
@Repository
public interface TeacherMapper {
    /**
     * 用于通过教师id获取教师信息
     * @param teacherID 教师id号
     * @return Teacher 教师对象
     * @author hongqiwu
     * @date 2018/12/01 15:09
     */
    Teacher getTeacherByTeacherID(BigInteger teacherID);
}

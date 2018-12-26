package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Course;
import cn.edu.xmu.crms.entity.Teacher;
import cn.edu.xmu.crms.entity.Team;
import cn.edu.xmu.crms.mapper.CourseMapper;
import cn.edu.xmu.crms.mapper.KlassMapper;
import cn.edu.xmu.crms.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hongqiwu
 * @date 2018/11/30 19:45
 */
@Repository
public class CourseDao {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    KlassMapper klassMapper;
    @Autowired
    TeacherDao teacherDao;

    public Course getCourseByCourseID(BigInteger courseID) {
        Course course = courseMapper.getCourseByCourseID(courseID);
        if(course == null) {
            return null;
        }
        course.setMaxMember(courseMapper.getCourseMaxMemberByCourseID(courseID));
        course.setMinMember(courseMapper.getCourseMinMemberByCourseID(courseID));
        return course;
    }

    public List<Course> listCoursesByStudentID(BigInteger studentID) {
        return courseMapper.listCoursesByStudentID(studentID);
    }

    public List<Course> listCoursesByTeacherID(BigInteger teacherID) {
        return courseMapper.listCoursesByTeacherID(teacherID);
    }

    public void deleteCourseInfoByCourseID(BigInteger courseID) {
        courseMapper.deleteCourseByCourseID(courseID);
        courseMapper.deleteConflictCourseStrategyByCourseID(courseID);
        courseMapper.deleteCourseInKlassByCourseID(courseID);
        courseMapper.deleteCourseInKlassStudentByCourseID(courseID);
        courseMapper.deleteCourseInRoundByCourseID(courseID);
        courseMapper.deleteCourseInSeminarByCourseID(courseID);
        courseMapper.deleteCourseInTeamByCourseID(courseID);
        courseMapper.deleteCourseInTeamStrategyByCourseID(courseID);
        courseMapper.deleteCourseMemberLimitStrategyByCourseID(courseID);
    }

    public BigInteger insertCourse(Course course) {
        courseMapper.insertCourse(course);
        return courseMapper.getLastInsertID();
    }

    public List<Course> listMainCoursesByCourseID(BigInteger courseID) {
        List<BigInteger> mainCoursesIDList = courseMapper.listMainCoursesIDByCourseID(courseID);
        List<Course> courses = new ArrayList<>();
        if(mainCoursesIDList == null) {
            return null;
        }
        for(int i = 0; i < mainCoursesIDList.size(); i++) {
            BigInteger mainCourseID = mainCoursesIDList.get(i);
            Course course = this.getCourseByCourseID(mainCourseID);
            courses.add(course);
        }
        return courses;
    }

    public List<Course> listSubCoursesByCourseID(BigInteger courseID) {
        List<BigInteger> subCoursesIDList = courseMapper.listSubCoursesIDByCourseID(courseID);
        List<Course> courses = new ArrayList<>();
        if(subCoursesIDList == null) {
            return null;
        }
        for(int i = 0; i < subCoursesIDList.size(); i++) {
            BigInteger subCourseID = subCoursesIDList.get(i);
            Course course = this.getCourseByCourseID(subCourseID);
            courses.add(course);
        }
        return courses;
    }

}

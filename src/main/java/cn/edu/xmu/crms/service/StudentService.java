package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.entity.User;
import cn.edu.xmu.crms.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName StudentService
 * @Description TODO
 * @Author Hongqiwu
 * @Date 2018/12/20 4:05
 **/
@RestController
@Service
public class StudentService {
    @Autowired
    StudentDao studentDao;
    @Autowired
    StudentMapper studentMapper;

    @GetMapping("/student/{studentID}")
    public Student getStudentInfo(@PathVariable("studentID") BigInteger studentID) {
        return studentDao.getStudentByStudentID(studentID);
    }


    @GetMapping("/student")//查询所有学生
    public List<Map<String, Object>> listAllStudentsInfo() {
        List<Map<String, Object>> studentsInfoList = new ArrayList<>();
        List<Student> students = studentDao.listAllStudents();
        for(int i = 0; i < students.size(); i++) {
            Map<String, Object> map = new HashMap<>(4);
            Student student = students.get(i);
            map.put("id",student.getID());
            map.put("account",student.getUsername());
            map.put("name",student.getName());
            map.put("email",student.getEmail());
            studentsInfoList.add(map);
        }
        return studentsInfoList;
    }

    @PutMapping("/student/{studentID}/information")//修改学生信息
    public Student modifyStudentInfo(@PathVariable("studentID") BigInteger studentID,
                                     @RequestBody Student student) {
        student.setID(studentID);
        if(studentDao.updateStudentInfo(student) == 1) {
            return student;
        }
        return null;
    }

    public Map<String, Object> resetStudentPasswordByStudentID(BigInteger studentID) {
        Map<String, Object> map = new HashMap<>(4);
        studentMapper.resetStudentPasswordByStudentID(studentID);
        Student student = studentMapper.getStudentByStudentID(studentID);
        map.put("id",student.getID());
        map.put("account",student.getUsername());
        map.put("name",student.getName());
        map.put("email",student.getEmail());
        return map;
    }

    public void deleteStudentByStudentID(BigInteger studentID) {
        studentMapper.deleteStudentByStudentID(studentID);
    }

    public Map<String, Object> updateStudentActiveByStudentID(Student student) {
        studentMapper.updateStudentActiveByStudentID(student);
        Map<String, Object> map = new HashMap<>(3);
        map.put("id",student.getID());
        map.put("account",student.getID());
        map.put("name",student.getName());
        return map;
    }
}

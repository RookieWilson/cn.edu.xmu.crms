package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.*;
import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.mapper.*;
import cn.edu.xmu.crms.util.email.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TeamService
 * @Author Hongqiwu
 **/
@Service
public class TeamService {
    @Autowired
    TeamDao teamDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    CourseDao courseDao;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    KlassDao klassDao;
    @Autowired
    TeamValidDao teamValidDao;
    @Autowired
    TeamValidMapper teamValidMapper;


    private Map<String, Object> getTeamInfo(Team team) {
        Map<String,Object> teamInfoMap = new HashMap<>(5);
        Map<String, Object> teamLeaderMap = new HashMap<>(3);
        Student teamLeader = team.getLeader();
        teamLeaderMap.put("id",teamLeader.getID());
        teamLeaderMap.put("account",teamLeader.getUsername());
        teamLeaderMap.put("name",teamLeader.getName());
        List<Student> teamMembers = team.getMembers();
        List<Map<String, Object>> teamMembersList = new ArrayList<>();
        for(int i = 0; i < teamMembers.size(); i++) {
            Student teamMember = teamMembers.get(i);
            Map<String, Object> oneMemberMap = new HashMap<>(3);
            oneMemberMap.put("id",teamMember.getID());
            oneMemberMap.put("account",teamMember.getUsername());
            oneMemberMap.put("name",teamMember.getName());
            teamMembersList.add(oneMemberMap);
        }
        teamInfoMap.put("id",team.getID());
        teamInfoMap.put("teamSerial",team.getTeamSerial());
        teamInfoMap.put("klassSerial",team.getKlassSerial());
        teamInfoMap.put("name",team.getTeamName());
        teamInfoMap.put("valid",team.getStatus());
        teamInfoMap.put("leader",teamLeaderMap);
        teamInfoMap.put("members",teamMembersList);
        return teamInfoMap;
    }

    public List<Map<String, Object>> listNoTeamStudentsInfoByCourseID(BigInteger courseID) {
        List<Map<String, Object>> noTeamStudentsMap = new ArrayList<>();
        List<Student> noTeamStudents = studentDao.listNoTeamStudentsByCourseID(courseID);
        for(int i = 0; i < noTeamStudents.size(); i++) {
            Student noTeamStudent = noTeamStudents.get(i);
            Map<String, Object> noTeamStudentMap = new HashMap<>(3);
            noTeamStudentMap.put("id",noTeamStudent.getID());
            noTeamStudentMap.put("account",noTeamStudent.getUsername());
            noTeamStudentMap.put("name",noTeamStudent.getName());
            noTeamStudentsMap.add(noTeamStudentMap);
        }
        return noTeamStudentsMap;
    }

    public Map<String, Object> getTeamInfoByTeamID(BigInteger teamID) {
        Team team = teamDao.getTeamByTeamID(teamID);
        Map<String, Object> teamInfo = new HashMap<>(7);
        Map<String, Object> courseInfo = new HashMap<>(2);
        courseInfo.put("id",team.getCourse().getID());
        courseInfo.put("name",team.getCourse().getCourseName());
        Map<String, Object> klassInfo = new HashMap<>(2);
        klassInfo.put("id",team.getKlass().getID());
        klassInfo.put("name",team.getKlass().getGrade().toString()+team.getKlass().getKlassSerial().toString());
        Map<String, Object> leaderInfo = new HashMap<>(2);
        leaderInfo.put("id",team.getLeader().getID());
        leaderInfo.put("account",team.getLeader().getUsername());
        leaderInfo.put("name",team.getLeader().getName());
        List<Map<String, Object>> membersInfo = new ArrayList<>();
        for(int i = 0; i < team.getMembers().size(); i++) {
            Student student = team.getMembers().get(i);
            Map<String, Object> memberInfo = new HashMap<>(3);
            memberInfo.put("id",student.getID());
            memberInfo.put("account",student.getUsername());
            memberInfo.put("name",student.getName());
            membersInfo.add(memberInfo);
        }
        teamInfo.put("id",team.getID());
        teamInfo.put("name",team.getTeamName());
        teamInfo.put("course",courseInfo);
        teamInfo.put("class",klassInfo);
        teamInfo.put("leader",leaderInfo);
        teamInfo.put("members",membersInfo);
        teamInfo.put("valid",team.getStatus());
        return teamInfo;
    }


    @DeleteMapping("/team/{teamID}")//组长解散小组
    public void deleteTeamByTeamID(@PathVariable("teamID") BigInteger teamID) {
        teamDao.deleteTeamByTeamID(teamID);
    }


    //组员或者组长添加新的成员
    @PutMapping("/team/{teamID}/add")
    public Boolean addTeamMember(@PathVariable("teamID") BigInteger teamID,
                                 @RequestBody Student student) {
        teamDao.insertStudentByTeamAndStudentID(teamID,student.getID());
        return teamValidDao.checkTeam(teamDao.getTeamByTeamID(teamID));
    }


    //移除成员或踢出队伍
    @PutMapping("/team/{teamID}/remove")
    public Boolean removeTeamMember(@PathVariable("teamID") BigInteger teamID,
                                    @RequestBody Student student) {
        teamDao.deleteStudentFromTeam(teamID,student.getID());
        return teamValidDao.checkTeam(teamDao.getTeamByTeamID(teamID));
    }


    //组长发出有效组队申请  如果返回id=0则还有未审核的申请 需等待
    @PostMapping("/team/{teamID}/teamvalidrequest")
    public Map<String, Object> createTeamValidRequest(@PathVariable("teamID") BigInteger teamID,
                                                      @RequestBody TeamValidApplication teamValidApplication) {
        Team team = new Team();
        teamValidApplication.setTeam(team);
        teamValidApplication.getTeam().setID(teamID);
        Map<String, Object> map = new HashMap<>(1);
        if(teamValidDao.getApplicationIDByTeamID(teamValidApplication.getTeam().getID())!= null) {
            map.put("id",0);
            return map;
        }
        BigInteger teamValidApplicationID = teamValidDao.insertApplicationByTeamValid(teamValidApplication);
        map.put("id",teamValidApplicationID);
        return map;
    }


    //教师更新队伍是否合法
    @PutMapping("/request/teamvalid/{teamValidID}")
    public void updateValidApplication(@PathVariable("teamValidID") BigInteger teamValidID,
                                       @RequestBody Map<String,Integer> statusMap) {
        Integer status = statusMap.get("status");
        teamValidDao.updateValidApplicationByID(teamValidID,status);
    }


    //创建队伍 三个不合法条件（还缺一个条件，选某门课程最少最多人数）
    @PostMapping("/course/{courseID}/team")
    public Map<String, Object> createNewTeam(@PathVariable("courseID") BigInteger courseID,
                                             @RequestBody Team team) {
        Course course = courseDao.getCourseByCourseID(courseID);
        if(course == null) {
            return null;
        }
        Student leader = studentDao.getStudentByStudentID(team.getLeader().getID());
        Klass klass = klassDao.getKlassByKlassID(team.getKlass().getID());
        for(int i = 0; i < team.getMembers().size(); i++) {
            Student student = studentMapper.getStudentByStudentID(team.getMembers().get(i).getID());
            team.getMembers().set(i,student);
        }
        team.setCourse(course);
        team.setLeader(leader);
        team.setKlass(klass);
        team.setStatus(1);
        if(!teamValidDao.checkTeam(team)) {
            team.setStatus(0);
        }
        Team newTeam = teamDao.insertTeam(team);
        Map<String, Object> map = new HashMap<>(1);
        map.put("id",newTeam.getID());
        return map;
    }

    @GetMapping("/request/teamvalid")
    public List<Map<String,Object>> listAllTeamValidApplication() {
        List<Map<String,Object>> applicationMapList = new ArrayList<>();
        List<TeamValidApplication> applications = teamValidDao.listAllApplication();
        for(int i = 0; i < applications.size(); i++) {
            Map<String,Object> map = new HashMap<>(10);
            TeamValidApplication application = applications.get(i);
            map.put("courseID",application.getCourse().getID());
            map.put("courseName",application.getCourse().getCourseName());
            map.put("classID",application.getKlass().getID());
            map.put("classGrade",application.getKlass().getGrade());
            map.put("classSerial",application.getKlass().getKlassSerial());
            map.put("teamID",application.getTeam().getID());
            map.put("leaderID",application.getLeader().getID());
            map.put("leaderName",application.getLeader().getName());
            map.put("reason",application.getReason());
            map.put("status",application.getStatus());
            applicationMapList.add(map);
        }

        return applicationMapList;
    }
}

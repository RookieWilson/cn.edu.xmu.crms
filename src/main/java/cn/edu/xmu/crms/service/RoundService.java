package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.RoundDao;
import cn.edu.xmu.crms.dao.TeamDao;
import cn.edu.xmu.crms.entity.Round;
import cn.edu.xmu.crms.entity.RoundScore;
import cn.edu.xmu.crms.entity.Team;
import cn.edu.xmu.crms.mapper.RoundMapper;
import cn.edu.xmu.crms.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RoundService
 * @Description 关于round的处理
 * @Author Hongqiwu
 * @Date 2018/12/20 0:22
 **/

@Service
public class RoundService {
    @Autowired
    RoundDao roundDao;
    @Autowired
    RoundMapper roundMapper;
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    TeamDao teamDao;

    private Map<String, Object> getRoundInfo(Round round) {
        Map<String, Object> roundInfo = new HashMap<>(4);
        roundInfo.put("id",round.getID());
        roundInfo.put("order",round.getRoundSerial());
        roundInfo.put("calculatePreType",round.getPresentationScoreMethod());
        roundInfo.put("calculateQueType",round.getQuestionScoreMethod());
        roundInfo.put("calculateRepType",round.getReportScoreMethod());
        roundInfo.put("courseID",round.getCourse().getID());
        roundInfo.put("courseName",round.getCourse().getCourseName());
        ArrayList<Map<String,Object>> signUpNumber = new ArrayList<>();
        for(int i = 0; i < round.getSignUpNumber().size();i++) {
            Map<String,Object> map = new HashMap<>(4);
            map.put("klassID",round.getSignUpNumber().get(i).get("klassID"));
            map.put("klassGrade",round.getSignUpNumber().get(i).get("klassGrade"));
            map.put("klassSerial",round.getSignUpNumber().get(i).get("klassSerial"));
            map.put("signUpNumber",round.getSignUpNumber().get(i).get("signUpNumber"));
            signUpNumber.add(map);
        }
        roundInfo.put("signUpNumber",signUpNumber);
        return roundInfo;
    }

    private Map<String, Object> getRoundScoreInfo(RoundScore roundScore) {
        Map<String, Object> roundScoreMap = new HashMap<>(5);
        Map<String, Object> teamMap = new HashMap<>(2);
        teamMap.put("id",roundScore.getTeam().getID());
        teamMap.put("name",roundScore.getTeam().getTeamName());
        Map<String, Object> roundMap = new HashMap<>(5);
        roundMap.put("id",roundScore.getRound().getID());
        roundMap.put("order",roundScore.getRound().getRoundSerial());
        roundScoreMap.put("preScore",roundScore.getPresentationScore());
        roundScoreMap.put("reportScore",roundScore.getReportScore());
        roundScoreMap.put("questionScore",roundScore.getQuestionScore());
        roundScoreMap.put("team",teamMap);
        roundScoreMap.put("round",roundMap);
        return roundScoreMap;
    }

    @GetMapping("/course/{courseID}/round")
    public List<Map<String, Object>> listRoundsInfoByCourseID(@PathVariable("courseID") BigInteger courseID) {
        List<Map<String, Object>> roundInfoList = new ArrayList<>();
        List<Round> rounds = roundDao.listRoundsByCourseID(courseID);
        for(int i = 0; i < rounds.size(); i++) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("id",rounds.get(i).getID());
            map.put("order",rounds.get(i).getRoundSerial());
            roundInfoList.add(map);
        }
        return roundInfoList;
    }

    public Map<String, Object> getRoundInfoByRoundID(BigInteger roundID) {
        Map<String, Object> roundInfo = new HashMap<>(4);
        Round round = roundMapper.getRoundByRoundID(roundID);
        roundInfo.put("id",round.getID());
        roundInfo.put("order",round.getRoundSerial());
        roundInfo.put("calculatePreType",round.getPresentationScoreMethod());
        roundInfo.put("calculateQueType",round.getQuestionScoreMethod());
        roundInfo.put("calculateRepType",round.getReportScoreMethod());
        return roundInfo;
    }



    @PutMapping("/round/{roundID}")
    public void modifyCalculateRuleByRoundID(@PathVariable("roundID") BigInteger roundID,
                                             @RequestBody Round round) {
        round.setID(roundID);
        roundDao.updateRuleByRound(round);
    }



    @GetMapping("/round/{roundID}/roundscore")
    public List<Map<String, Object>> listTeamRoundScoreInfoByRoundID(@PathVariable("roundID") BigInteger roundID) {
        List<Map<String, Object>> teamScoreList = new ArrayList<>();
        List<RoundScore> roundScores = roundDao.listRoundScoreByRoundID(roundID);
        for(int i = 0; i < roundScores.size(); i++) {
            Map<String, Object> teamInfo = new HashMap<>(2);
            Map<String, Object> map = new HashMap<>(4);
            RoundScore roundScore = roundScores.get(i);
            Team team = teamDao.getTeamByTeamID(roundScore.getTeamID());
            teamInfo.put("id",team.getID());
            teamInfo.put("name",team.getTeamName());
            map.put("team",teamInfo);
            map.put("preScore",roundScore.getPresentationScore());
            map.put("questionScore",roundScore.getQuestionScore());
            map.put("reportScore",roundScore.getReportScore());
            teamScoreList.add(map);
        }
        return teamScoreList;
    }



    @GetMapping("/round/{roundID}/team/{teamID}/roundscore")
    public Map<String, Object> getRoundScoreInfoByRoundAndTeamID(@PathVariable("roundID") BigInteger roundID,
                                                                 @PathVariable("teamID") BigInteger teamID) {
        RoundScore roundScore = roundDao.getRoundScoreByRoundAndTeamID(roundID, teamID);
        return this.getRoundScoreInfo(roundScore);
    }


}
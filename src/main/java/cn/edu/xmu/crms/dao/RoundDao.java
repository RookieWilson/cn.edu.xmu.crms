package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Round;
import cn.edu.xmu.crms.entity.RoundScore;
import cn.edu.xmu.crms.mapper.KlassMapper;
import cn.edu.xmu.crms.mapper.RoundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RoundDao
 * @Description TODO
 * @Author Hongqiwu
 * @Date 2018/12/20 0:37
 **/
@Repository
public class RoundDao {
    @Autowired
    RoundMapper roundMapper;
    @Autowired
    KlassMapper klassMapper;

    public Round getRoundByRoundID(BigInteger roundID) {
        Round round = roundMapper.getRoundByRoundID(roundID);
        List<BigInteger> klassesID = klassMapper.listKlassIDByCourseID(round.getCourse().getID());
        List<Map<String,Object>> signUpNumber = new ArrayList<>();
        round.setSignUpNumber(signUpNumber);
        for(int i = 0; i < klassesID.size(); i++) {
            round.getSignUpNumber().add(roundMapper.getSignUpNumberByRoundAndKlassID(roundID,klassesID.get(i)));
        }
        return round;
    }

    public List<Round> listRoundsByCourseID(BigInteger courseID) {
        List<Round> rounds = new ArrayList<>();
        List<BigInteger> roundsID = roundMapper.listRoundIDByCourseID(courseID);
        for(int i = 0; i < roundsID.size(); i++) {
            Round round = roundMapper.getRoundByRoundID(roundsID.get(i));
            rounds.add(round);
        }
        return rounds;
    }

    public List<RoundScore> listRoundScoreByRoundID(BigInteger roundID) {
        List<BigInteger> teamsID = roundMapper.listTeamIDByRoundID(roundID);
        List<RoundScore> roundScores = new ArrayList<>();
        for(int i = 0; i < teamsID.size(); i++) {
            RoundScore roundScore = roundMapper.getRoundScoreByRoundAndTeamID(roundID,teamsID.get(i));
            roundScores.add(roundScore);
        }
        return roundScores;
    }

    public void updateRuleByRound(Round round) {
        roundMapper.updateRuleByRound(round);
    }
}

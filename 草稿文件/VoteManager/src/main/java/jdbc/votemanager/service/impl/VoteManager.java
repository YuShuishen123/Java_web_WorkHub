package jdbc.votemanager.service.impl;

import jdbc.votemanager.entity.Student;
import jdbc.votemanager.service.VoteManagerInterface;

import java.util.HashMap;

public class VoteManager implements VoteManagerInterface {
    private HashMap<String,Integer> map=new HashMap<String,Integer>();  // 存储学生和选票数量
    @Override
    public void addStudent(Student s) {  // 添加学生
        map.put(s.getSno(),0);
    }

    @Override
    public void vote(Student voter, Student select) {   // 投票
        if(map.containsKey(select.getSno())){
            Integer v=map.get(select.getSno());
            map.put(select.getSno(),v+1);
        }else{
            map.put(select.getSno(),0);
        }
    }
    @Override
    public int getVoteNum(Student s) {   // 获取学生选票数量
        if(map.containsKey(s.getSno())){
            return map.get(s.getSno());
        }
        return 0;
    }
}
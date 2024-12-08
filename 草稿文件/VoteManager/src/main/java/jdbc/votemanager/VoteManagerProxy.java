package jdbc.votemanager;

import jdbc.votemanager.entity.Student;
import jdbc.votemanager.service.VoteManagerInterface;
import jdbc.votemanager.service.impl.VoteManager;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * VoteManagerProxy类是VoteManager的代理实现，提供了额外的投票逻辑控制
 */
public class VoteManagerProxy implements VoteManagerInterface {

    // voteManager是实际执行投票操作的对象
    private final VoteManager voteManager;
    // votes列表记录了所有的投票行为，每个投票行为由投票人和被投票人组成
    private List<AbstractMap.SimpleEntry<Student, Student>> votes = new ArrayList<>();

    /**
     * 构造方法，传入实际的VoteManager对象
     * @param voteManager 实际的投票管理对象
     */
    public VoteManagerProxy (VoteManager voteManager) {
        this.voteManager = voteManager;
    }

    /**
     * 添加学生方法，直接调用voteManager的同名方法
     * @param s 要添加的学生对象
     */
    @Override
    public void addStudent(Student s) {
        voteManager.addStudent(s);
    }

    /**
     * 获取学生的投票数量，直接调用voteManager的同名方法
     * @param s 学生对象
     * @return 学生的投票数量
     */
    @Override
    public int getVoteNum(Student s) {
        return voteManager.getVoteNum(s);
    }

    /**
     * 投票方法，包括了额外的逻辑控制
     * @param voter 投票人
     * @param select 被投票人
     */
    @Override
    public void vote(Student voter, Student select) {
        // 不能投给自己
        if (voter == select) {
            return;
        }
        // 检查是否已经投票
        for(AbstractMap.SimpleEntry<Student, Student> vote :votes){
            if(voter == vote.getKey() && select == vote.getValue()){
                return;
            }
        }
        // 处理投票
        voteManager.vote(voter, select);

        // 将投票学生加入列表
        votes.add(new AbstractMap.SimpleEntry<>(voter, select));
    }
}

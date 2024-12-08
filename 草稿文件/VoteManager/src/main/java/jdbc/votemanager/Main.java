package jdbc.votemanager;

import jdbc.votemanager.entity.Student;
import jdbc.votemanager.service.VoteManagerInterface;
import jdbc.votemanager.service.impl.VoteManager;


public class Main{
    public static void main(String[] args){
        Student s1=new Student("001","张三");
        Student s2=new Student("002","李四");
        Student s3=new Student("003","王五");
        VoteManagerInterface v=new VoteManagerProxy(new VoteManager());

        v.addStudent(s1);
        v.addStudent(s2);
        v.addStudent(s3);

        v.vote(s1,s1);  // 作废
        v.vote(s1,s2);  // 计数 s2一票
        v.vote(s1,s2); //  重复投票，不计票
        v.vote(s1,s3); //  s3一票
        v.vote(s2,s1);  // 计数，s1一票
        v.vote(s3,s2);  // 计数，s2一票
        v.vote(s3,s1);  // s1一票
        v.vote(s3,s3);  // 自己不能投自己，作废

        System.out.println(v.getVoteNum(s1));  // 2票
        System.out.println(v.getVoteNum(s2));  // 2票
        System.out.println(v.getVoteNum(s3));  // 1票

    }

}
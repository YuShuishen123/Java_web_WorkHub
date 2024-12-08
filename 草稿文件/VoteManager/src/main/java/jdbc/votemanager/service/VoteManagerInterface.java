package jdbc.votemanager.service;
import jdbc.votemanager.entity.Student;

public interface VoteManagerInterface{

    void addStudent(Student s);

    void vote(Student voter,Student select);

    int getVoteNum(Student s);

}

package jdbc.votemanager.entity;
import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

public class Student {
    private String sno;
    private String sname;

    public Student(String sno, String sname) {

        this.sno = sno;
        this.sname = sname;

    }

    public String getSno() {

        return sno;

    }


    public void setSno(String sno) {

        this.sno = sno;

    }
}
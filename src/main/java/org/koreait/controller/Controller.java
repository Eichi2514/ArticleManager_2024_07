package org.koreait.controller;

import org.koreait.dto.Member;

import java.util.List;

public abstract class Controller {
    protected static Member loginCheck = null;

    protected static List<Member> members() {
        return members();
    }

    public abstract void doAction(String cmd, String actionMethodName);

    public static Member isLoginCheck() {
        return loginCheck;
    }

    public void makeTestData() {

    }
}
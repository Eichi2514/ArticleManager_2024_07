package org.koreait.controller;

import org.koreait.dto.Member;
import org.koreait.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController extends Controller {
    private Scanner sc;
    private List<Member> members;
    private String cmd;
    private int loginCheck = 0;
    private int id = 4;

    public MemberController(Scanner sc) {
        this.sc = sc;
        members = new ArrayList<>();
    }

    public void join() {
        System.out.println("== 회원가입 ==");
        String regDate = Util.getNow();
        System.out.print("ID : ");
        String loginId = sc.nextLine();
        while (true) {
            String foundMemberId = getMemberLoginId(loginId);
            if (foundMemberId == null) {
                System.out.println("중복된 ID 입니다 다시 입력해주세요");
                System.out.print("ID : ");
                loginId = sc.nextLine();
            } else break;
        }
        String loginPw = null;
        while (true) {
            System.out.print("PW : ");
            loginPw = sc.nextLine();
            System.out.print("PW 재확인 : ");
            String loginPw2 = sc.nextLine();
            if (!loginPw.equals(loginPw2)) System.out.println("PW를 다시 확인해주세요");
            if (loginPw.equals(loginPw2)) break;
        }
        System.out.print("이름 : ");
        String name = sc.nextLine();

        Member member = new Member(id, regDate, loginId, loginPw, name);
        members.add(member);
        id++;

        System.out.println("회원가입이 완료되었습니다.");
    }

    private String getMemberLoginId(String loginId) {
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return null;
            }
        }
        return loginId;
    }

    public void makeTestId() {
        System.out.println("테스트 ID 생성");
        members.add(new Member(1, Util.getNow(), "asd", "asd", "testName1"));
        members.add(new Member(2, Util.getNow(), "testLoginId2", "testLoginPw2", "testName2"));
        members.add(new Member(3, Util.getNow(), "testLoginId3", "testLoginPw3", "testName3"));
    }

    public int login() {
        if (loginCheck != 0) {
            System.out.println("먼저 로그아웃을 해주세요.");
            return 0;
        }
        System.out.println("== 로그인 ==");
        int loginIdCheck = -1;
        int loginError = 0;
        while (true) {
            System.out.print("ID : ");
            String loginId = sc.nextLine();

            for (int i = 0; i <= members.size() - 1; i++) {
                Member member = members.get(i);
                if (member.getLoginId().equals(loginId)) {
                    loginIdCheck = i;
                }
            }
            if (loginIdCheck == -1) {
                System.out.printf("ID가 존재하지 않습니다. %d\n", (3 - loginError));
                loginError++;
                if (loginError > 2) {
                    System.out.println("== 로그인(ID) 실패 ==");
                    return 0;
                }
            } else if (loginIdCheck != -1) {
                Member member = members.get(loginIdCheck);
                if (member.getLoginId().equals(loginId)) {
                    break;
                }
            }
        }
        loginError = 0;
        while (loginIdCheck >= 0) {
            Member member = members.get(loginIdCheck);
            System.out.print("PW : ");
            String loginPw = sc.nextLine();
            if (member.getLoginPw().equals(loginPw)) {
                break;
            } else if (loginError > 2) {
                System.out.println("== 로그인(PW) 실패 ==");
                return 0;
            } else {
                System.out.printf("PW가 틀렸습니다. %d\n", (3 - loginError));
                loginError++;
            }
        }
        loginCheck = id;
        System.out.println("로그인 되었습니다");

        return loginCheck;
    }

    public void logout() {
        if (loginCheck == 0) {
            System.out.println("로그아웃 상태입니다.");
            return;
        }
        System.out.println("로그아웃 되었습니다.");
        loginCheck = 0;
    }

    @Override
    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;
        this.loginCheck = loginCheck;

        switch (actionMethodName) {
            case "join":
                join();
                break;
            case "login":
                login();
                break;
            case "logout":
                logout();
                break;
            default:
                System.out.println("명령어 확인 (actionMethodName) 오류");
                break;
        }
    }
}



























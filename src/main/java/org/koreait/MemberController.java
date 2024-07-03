package org.koreait;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController {
    private Scanner sc;
    private List<Member> members;
    private boolean loginCheck = false;
    private int memberId = 4;

    public MemberController(Scanner sc) {
        this.sc = sc;
        members = new ArrayList<>();
    }

    public void add() {
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

        Member member = new Member(memberId, regDate, loginId, loginPw, name);
        members.add(member);
        memberId++;

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
        members.add(new Member(1, Util.getNow(), "testLoginId1", "testLoginPw1", "testName1"));
        members.add(new Member(2, Util.getNow(), "testLoginId2", "testLoginPw2", "testName2"));
        members.add(new Member(3, Util.getNow(), "testLoginId3", "testLoginPw3", "testName3"));
    }

    public void login() {
        if (loginCheck) {
            System.out.println("먼저 로그아웃을 해주세요.");
            return;
        }
        System.out.println("== 로그인 ==");
        int loginIdCheck = -1;
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
                System.out.println("ID가 존재하지 않습니다.");
            } else if (loginIdCheck != -1) {
                Member member = members.get(loginIdCheck);
                if (member.getLoginId().equals(loginId)) {
                    break;
                }
            }
        }
        while (true) {
            Member member = members.get(loginIdCheck);
            System.out.print("PW : ");
            String loginPw = sc.nextLine();
            if (member.getLoginPw().equals(loginPw)) {
                break;
            } else {
                System.out.println("PW가 틀렸습니다.");
            }
        }
        System.out.println("로그인 되었습니다");
        loginCheck = true;
    }

    public void logout() {
        if (loginCheck == false) {
            System.out.println("먼저 로그인을 해주세요.");
            return;
        }
        System.out.println("로그아웃 되었습니다.");
        loginCheck = false;
    }
}




























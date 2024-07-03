package org.koreait.articleManager;

import org.koreait.controller.Controller;
import org.koreait.controller.MemberController;
import org.koreait.controller.ArticleController;

import java.util.Scanner;

public class App {
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("==프로그램 시작==");
        int loginCheck = 0;

        MemberController memberController = new MemberController(sc);
        ArticleController articleController = new ArticleController(sc);

        articleController.makeTestData();
        memberController.makeTestId();

        Controller controller = null;


        while (true) {
            System.out.print("명령어) ");
            String cmd = sc.nextLine().trim();

            if (cmd.length() == 0) {
                System.out.println("명령어를 입력해주세요");
                continue;
            }
            if (cmd.equals("help")) {
                System.out.println("  member join   : 회원가입");
                System.out.println("  member login  : 로그인");
                System.out.println("  member logout : 로그아웃");
                System.out.println("article  write  : 글쓰기");
                System.out.println("article  list   : 글목록");
                System.out.println("article  detail : 글 자세히 보기");
                System.out.println("article  modify : 글 수정");
                System.out.println("article  delete : 글 삭제");
                continue;
            }
            if (cmd.equals("exit")) {
                break;
            }
            String[] cmdBits = cmd.split(" ");
            String controllerName = cmdBits[0];
            if (cmdBits.length == 1) {
                System.out.println("명령어를 확인해주세요");
                continue;
            }
            String actionMethodName = cmdBits[1];
            if (controllerName.equals("article")) {
                if (loginCheck !=0) {
                    controller = articleController;
                } else {
                    System.out.println("로그인을 먼저 해주세요");
                    continue;
                }
            } else if (controllerName.equals("member")) {
                controller = memberController;
            } else {
                System.out.println("사용 할 수 없는 명령어 입니다.");
                continue;
            }
            controller.doAction(cmd, actionMethodName);
        }
    }
}

package org.koreait;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public void run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("==프로그램 시작==");

        MemberController memberController = new MemberController(sc);
        ArticleController articleController = new ArticleController(sc);

        articleController.makeTestData();
        memberController.makeTestId();


        while (true) {
            System.out.print("명령어) ");
            String cmd = sc.nextLine();

            if (cmd.startsWith("member")) {
                memberController.add();
            } else if (cmd.startsWith("login")) {
                memberController.login();
            } else if (cmd.startsWith("logout")) {
                memberController.logout();
            } else if (cmd.length() == 0) {
                System.out.println("명령어를 입력하세요");
            } else if (cmd.equals("exit")) {
                articleController.exit();
                break;
            } else if (cmd.equals("article write")) {
                articleController.write();
            } else if (cmd.contains("article list")) {
                articleController.list(cmd);
            } else if (cmd.startsWith("article detail")) {
                articleController.detail(cmd);
            } else if (cmd.startsWith("article delete")) {
                articleController.delete(cmd);
            } else if (cmd.startsWith("article modify")) {
                articleController.modify(cmd);
            } else {
                System.out.println("사용할 수 없는 명령어입니다");
            }

        }
    }
}

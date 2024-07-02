package org.koreait;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static List<Article> articles = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("== 회원가입 ==");
        int id = 1;
        String regDate = Util.getNow();
        System.out.print("ID : ");
        String loginId = sc.nextLine();
        System.out.print("PW : ");
        String loginPw = sc.nextLine();
        System.out.print("NAME : ");
        String name = sc.nextLine();

        Member(id, regDate, loginId, loginPw, name);
        id++;


        System.out.println("==프로그램 시작==");

        makeTestData();

        int lastArticleId = 0;


        while (true) {
            System.out.print("명령어) ");
            String cmd = sc.nextLine();

            if (cmd.length() == 0) {
                System.out.println("명령어를 입력하세요");
                continue;
            }
            if (cmd.equals("exit")) {
                break;
            }

            if (cmd.equals("article write")) {
                System.out.println("==게시글 작성==");
                int number = lastArticleId + 1;
                String regDate2 = Util.getNow();
                String updateDate = regDate2;
                System.out.print("제목 : ");
                String title = sc.nextLine();
                System.out.print("내용 : ");
                String body = sc.nextLine();

                Article article = new Article(number, regDate2, updateDate, title, body);
                articles.add(article);

                System.out.println(number + "번 글이 생성되었습니다");
                lastArticleId++;
            } else if (cmd.contains("list")) {
                String[] cmds = cmd.split(" ");
                if (cmds.length == 2) {
                    System.out.println("==게시글 목록==");
                    if (articles.size() == 0) {
                        System.out.println("아무것도 없어");
                    } else {
                        System.out.println("  번호   /    날짜   /   제목   /   내용   ");
                        for (int i = articles.size() - 1; i >= 0; i--) {
                            Article article = articles.get(i);
                            if (Util.getNow().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                                System.out.printf("  %d   /   %s      /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody());
                            } else {
                                System.out.printf("  %d   /   %s      /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody());
                            }
                        }
                    }
                } else if (cmds.length == 3) {
                    int w = 0;
                    System.out.println("==게시글 목록==");
                    if (articles.size() == 0) {
                        System.out.println("아무것도 없어");
                    } else {
                        System.out.println("  번호   /    날짜   /   제목   /   내용   ");
                        for (int i = articles.size() - 1; i >= 0; i--) {
                            Article article = articles.get(i);
                            if (article.getTitle().contains(cmds[2])) {
                                if (Util.getNow().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                                    System.out.printf("  %d   /   %s      /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody());
                                } else {
                                    System.out.printf("  %d   /   %s      /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody());
                                }
                            } else {w = -1;}
                        } if(w == -1) System.out.println("해당 단어를 포함한 게시글이 없습니다");
                    }
                }
            } else if (cmd.startsWith("article detail")) {
                System.out.println("==게시글 상세보기==");

                int number = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = null;

                for (Article article : articles) {
                    if (article.getId() == number) {
                        foundArticle = article;
                        break;
                    }
                }

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                System.out.println("번호 : " + foundArticle.getId());
                System.out.println("작성날짜 : " + foundArticle.getRegDate());
                System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
                System.out.println("제목 : " + foundArticle.getTitle());
                System.out.println("내용 : " + foundArticle.getBody());

            } else if (cmd.startsWith("article delete")) {
                System.out.println("==게시글 삭제==");

                int number = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = null;

                for (Article article : articles) {
                    if (article.getId() == number) {
                        foundArticle = article;
                        break;
                    }
                }

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                articles.remove(foundArticle);
                System.out.println(id + "번 게시글이 삭제되었습니다");

            } else if (cmd.startsWith("article modify")) {
                System.out.println("==게시글 수정==");

                int number = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = null;

                for (Article article : articles) {
                    if (article.getId() == number) {
                        foundArticle = article;
                        break;
                    }
                }

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                System.out.println("기존 제목 : " + foundArticle.getTitle());
                System.out.println("기존 내용 : " + foundArticle.getBody());
                System.out.print("새 제목 : ");
                String newTitle = sc.nextLine();
                System.out.print("새 내용 : ");
                String newBody = sc.nextLine();

                foundArticle.setTitle(newTitle);
                foundArticle.setBody(newBody);
                foundArticle.setUpdateDate(Util.getNow());

                System.out.println(number + "번 게시글이 수정되었습니다");
            } else {
                System.out.println("사용할 수 없는 명령어입니다");
            }

        }
        System.out.println("==프로그램 종료==");
        sc.close();

    }

    private static void makeTestData() {
        System.out.println("테스트 데이터 생성");
        articles.add(new Article(-1, "2023-12-12 12:12:12", "2023-12-12 12:12:12", "제목1", "내용1"));
        articles.add(new Article(-2, Util.getNow(), Util.getNow(), "제목2", "내용2"));
        articles.add(new Article(-3, Util.getNow(), Util.getNow(), "제목3", "내용3"));
    }

    private static void Member(int id,String regDate,String loginId,String loginPw,String name){
        System.out.println("회원가입이 완료되었습니다.");
    }
}
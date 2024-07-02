package org.koreait;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static List<Article> articles = new ArrayList<>();
    static List<Member> members = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("==프로그램 시작==");

        makeTestData();

        int lastArticleId = 0;
        int memberId = 1;


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
                int id = lastArticleId + 1;
                String regDate2 = Util.getNow();
                String updateDate = regDate2;
                System.out.print("제목 : ");
                String title = sc.nextLine();
                System.out.print("내용 : ");
                String body = sc.nextLine();

                Article article = new Article(id, regDate2, updateDate, title, body);
                articles.add(article);

                System.out.println(id + "번 글이 생성되었습니다");
                lastArticleId++;
            } else if (cmd.contains("article list")) {
                String[] cmds = cmd.split(" ");
                if (cmds.length == 2) {
                    if (articles.size() == 0) {
                        System.out.println("아무것도 없어");
                    } else {
                        System.out.println("==게시글 목록==");
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
                    if (articles.size() == 0) {
                        System.out.println("아무것도 없어");
                    } else {
                        int w = 0;
                        System.out.println("==게시글 목록==");
                        System.out.println("  번호   /    날짜   /   제목   /   내용   ");
                        for (int i = articles.size() - 1; i >= 0; i--) {
                            Article article = articles.get(i);
                            if (article.getTitle().contains(cmds[2])) {
                                if (Util.getNow().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                                    System.out.printf("  %d   /   %s      /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody());
                                } else {
                                    System.out.printf("  %d   /   %s      /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody());
                                }
                            } else {
                                w = -1;
                            }
                        }
                        if (w == -1) System.out.println("해당 단어를 포함한 게시글이 없습니다");
                    }
                }
            } else if (cmd.startsWith("article detail")) {
                System.out.println("==게시글 상세보기==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

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

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                articles.remove(foundArticle);
                System.out.println(id + "번 게시글이 삭제되었습니다");

            } else if (cmd.startsWith("article modify")) {
                System.out.println("==게시글 수정==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

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

                System.out.println(id + "번 게시글이 수정되었습니다");
            } else if (cmd.startsWith("member")) {
                System.out.println("== 회원가입 ==");
                String regDate = Util.getNow();
                System.out.print("ID : ");
                String loginId = sc.nextLine();
                while (true) {
                    String foundMemberId = getMemberLoginId(loginId);
                    if (foundMemberId != null) break;
                    if (foundMemberId == null) {
                        System.out.println("중복된 ID 입니다 다시 입력해주세요");
                        System.out.print("ID : ");
                        loginId = sc.nextLine();
                    }
                }
                System.out.print("PW : ");
                String loginPw = sc.nextLine();
                while (true) {
                    System.out.print("PW 재확인 : ");
                    String loginPw2 = sc.nextLine();
                    if (!loginPw.equals(loginPw2)) System.out.println("PW를 다시 확인해주세요");
                    if (loginPw.equals(loginPw2)) break;
                }
                System.out.print("NAME : ");
                String name = sc.nextLine();

                Member member = new Member(memberId, regDate, loginId, loginPw, name);
                members.add(member);
                memberId++;

                System.out.println("회원가입이 완료되었습니다.");

            } else {
                System.out.println("사용할 수 없는 명령어입니다");
            }

        }
        System.out.println("==프로그램 종료==");
        sc.close();

    }

    private static Article getArticleById(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    private static String getMemberLoginId(String loginId) {
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return null;
            }
        }
        return loginId;
    }

    private static void makeTestData() {
        System.out.println("테스트 데이터 생성");
        articles.add(new Article(-1, "2023-12-12 12:12:12", "2023-12-12 12:12:12", "제목1", "내용1"));
        articles.add(new Article(-2, Util.getNow(), Util.getNow(), "제목2", "내용2"));
        articles.add(new Article(-3, Util.getNow(), Util.getNow(), "제목3", "내용3"));
    }
}
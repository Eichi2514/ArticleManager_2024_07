package org.koreait;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController {
    private Scanner sc;
    private List<Article> articles;
    private int lastArticleId = 3;

    public ArticleController(Scanner sc) {
        this.sc = sc;
        articles = new ArrayList<>();
    }

    public void exit() {
        System.out.println("==프로그램 종료==");
        sc.close();
    }

    public void write() {
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
    }

    public void list(String cmd) {
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
                boolean loginCheck = true;
                System.out.println("==게시글 목록==");
                System.out.println("  번호   /    날짜   /   제목   /   내용   ");
                for (int i = articles.size() - 1; i >= 0; i--) {
                    Article article = articles.get(i);
                    if (article.getTitle().contains(cmds[2])) {
                        loginCheck = false;
                        if (Util.getNow().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                            System.out.printf("  %d   /   %s      /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody());
                        } else {
                            System.out.printf("  %d   /   %s      /   %s   /   %s  \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody());
                        }
                    }
                }
                if (loginCheck) {
                    System.out.println("해당 단어를 포함한 게시글이 없습니다");
                }
            }
        }
    }

    public void detail(String cmd) {
        System.out.println("==게시글 상세보기==");

        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다");
            return;
        }
        System.out.println("번호 : " + foundArticle.getId());
        System.out.println("작성날짜 : " + foundArticle.getRegDate());
        System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
        System.out.println("제목 : " + foundArticle.getTitle());
        System.out.println("내용 : " + foundArticle.getBody());

    }

    public void delete(String cmd) {
        System.out.println("==게시글 삭제==");

        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다");
            return;
        }
        articles.remove(foundArticle);
        System.out.println(id + "번 게시글이 삭제되었습니다");

    }

    public void modify(String cmd) {
        System.out.println("==게시글 수정==");

        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다");
            return;
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


    }

    private Article getArticleById(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    public void makeTestData() {
        System.out.println("테스트 데이터 생성");
        articles.add(new Article(1, Util.getNow(), Util.getNow(), "제목1", "내용1"));
        articles.add(new Article(2, Util.getNow(), Util.getNow(), "제목2", "내용2"));
        articles.add(new Article(3, Util.getNow(), Util.getNow(), "제목3", "내용3"));
    }
}

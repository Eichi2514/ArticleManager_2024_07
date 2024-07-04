package org.koreait.controller;

import org.koreait.articleManager.Container;
import org.koreait.dto.Article;
import org.koreait.dto.Member;
import org.koreait.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller {
    private Scanner sc;
    private List<Article> articles;
    private String cmd;

    List<Member> members;

    private int lastArticleId = 3;

    public ArticleController(Scanner sc) {
        this.sc = sc;
        articles = Container.articleDao.articles;
        members = Container.memberDao.members;
    }

    public void exit() {
        System.out.println("==프로그램 종료==");
        sc.close();
    }

    public void write() {
        System.out.println("==게시글 작성==");
        int id = lastArticleId + 1;
        String regDate = Util.getNow();
        String updateDate = regDate;
        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();
        int memberId = Controller.isLoginCheck().getId();
        Article article = new Article(id, regDate, updateDate, memberId, title, body);
        articles.add(article);

        System.out.println(id + "번 글이 생성되었습니다");
        lastArticleId++;
    }

    public void list() {
        String[] cmds = cmd.split(" ");
        if (cmds.length == 2) {
            if (articles.size() == 0) {
                System.out.println("작성된 글이 없습니다");
            } else {
                System.out.println("==게시글 목록==");
                System.out.println("  번호   /    날짜   /    작성자   /     제목     /   내용   ");
                for (int i = articles.size() - 1; i >= 0; i--) {
                    Article article = articles.get(i);
                    Member loginCheck = members.get(i);
                    if (Util.getNow().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                        System.out.printf("  %d   /  %s  /     %s      /     %s    /   %s      \n",
                                article.getId(), article.getRegDate().split(" ")[1], loginCheck.getName(), article.getTitle(), article.getBody());
                    } else {
                        System.out.printf("  %d   / %s /     %s      /     %s    /   %s      \n",
                                article.getId(), article.getRegDate().split(" ")[0], loginCheck.getName(), article.getTitle(), article.getBody());
                    }
                }
            }
        } else if (cmds.length == 3) {
            if (articles.size() == 0) {
                System.out.println("작성된 글이 없습니다");
            } else {
                boolean listCheck = true;

                for (int i = articles.size() - 1; i >= 0; i--) {
                    System.out.println("==게시글 목록==");
                    System.out.println("  번호   /    날짜   /    작성자   /     제목    /   내용   ");
                    Article article = articles.get(i);
                    Member loginCheck = members.get(i);
                    if (article.getTitle().contains(cmds[2])) {
                        listCheck = false;
                        if (Util.getNow().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                            System.out.printf("  %d   /  %s  /     %s      /     %s    /   %s      \n",
                                    article.getId(), article.getRegDate().split(" ")[1], loginCheck.getName(), article.getTitle(), article.getBody());
                        } else {
                            System.out.printf("  %d   / %s /     %s      /     %s    /   %s      \n",
                                    article.getId(), article.getRegDate().split(" ")[0], loginCheck.getName(), article.getTitle(), article.getBody());
                        }
                    }
                }
                if (listCheck) {
                    System.out.println("해당 단어를 포함한 게시글이 없습니다");
                }
            }
        }
    }

    public void detail() {
        int id = Integer.parseInt(cmd.split(" ")[2]);
        Article foundArticle = getArticleById(id);

        System.out.println("==게시글 상세보기==");


        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다");
            return;
        }
        System.out.println("번호 : " + foundArticle.getId());
        System.out.println("작성날짜 : " + foundArticle.getRegDate());
        System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
        System.out.println("작성자 : " + foundArticle.getMemberId());
        System.out.println("제목 : " + foundArticle.getTitle());
        System.out.println("내용 : " + foundArticle.getBody());

    }

    public void delete() {
        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle.getMemberId() != loginCheck.getId()) {
            System.out.println("타인의 글은 삭제할 수 없습니다.");
            return;
        }

        System.out.println("==게시글 삭제==");

        if (foundArticle == null) {
            System.out.println("해당 게시글은 없습니다");
            return;
        }
        articles.remove(foundArticle);
        System.out.println(id + "번 게시글이 삭제되었습니다");

    }

    public void modify() {
        int id = Integer.parseInt(cmd.split(" ")[2]);

        Article foundArticle = getArticleById(id);

        if (foundArticle.getMemberId() != loginCheck.getId()) {
            System.out.println("타인의 글은 수정할 수 없습니다.");
            return;
        }

        System.out.println("==게시글 수정==");

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

    @Override
    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;

        switch (actionMethodName) {
            case "write":
                write();
                break;
            case "list":
                list();
                break;
            case "detail":
                detail();
                break;
            case "modify":
                modify();
                break;
            case "delete":
                delete();
                break;
            default:
                System.out.println("명령어 확인 (actionMethodName) 오류");
                break;
        }
    }

    public void makeTestData() {
        System.out.println("테스트 데이터 생성");
        articles.add(new Article(1, Util.getNow(), Util.getNow(), 1, "제목1", "내용1"));
        articles.add(new Article(2, Util.getNow(), Util.getNow(), 2, "제목2", "내용2"));
        articles.add(new Article(3, Util.getNow(), Util.getNow(), 3, "제목3", "내용3"));

    }
}

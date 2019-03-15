package com.project.administrator.huaqin;

import android.view.View;

import java.util.ArrayList;

public class Item {

    private String book_title;

    private String book_url;

    private String book_cover_url;

    private String down_url;

    private String content_description;

    private String writer;

    private String download_count;


    private View.OnClickListener requestBtnClickListener;


    public Item(String book_title, String book_url, String book_cover_url, String down_url, String content_description) {
        this.book_title = book_title;
        this.book_url = book_url;
        this.book_cover_url = book_cover_url;
        this.down_url = down_url;
        this.content_description = content_description;
    }


    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDownload_count() {
        return download_count;
    }

    public void setDownload_count(String download_count) {
        this.download_count = download_count;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getBook_url() {
        return book_url;
    }

    public void setBook_url(String book_url) {
        this.book_url = book_url;
    }

    public String getBook_cover_url() {
        return book_cover_url;
    }

    public void setBook_cover_url(String book_cover_url) {
        this.book_cover_url = book_cover_url;
    }

    public String getDown_url() {
        return down_url;
    }

    public void setDown_url(String down_url) {
        this.down_url = down_url;
    }

    public String getContent_description() {
        return content_description;
    }

    public void setContent_description(String content_description) {
        this.content_description = content_description;
    }

    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

//    public static ArrayList<Item> getTestingList() {
//        ArrayList<Item> items = new ArrayList<>();
//        String content_1 = "《海胆》是一本人物特写集，收录了十篇文章，写出了十个人的秘密。\n" +
//                "附赠朴树亲笔信。\n" +
//                "之所以取名“海胆”，是因为这十个人都和海胆一样：有尖利的刺，也有柔软的心。\n" +
//                "除了刷爆朋友圈的《和李安一起午餐》——对李安而言，电影的秘密可以讲，生活的秘密不可说。但他把生活秘密藏在电影里，看懂了，才是真正理解他了——还有：\n" +
//                "《Hello，朴树先生》：用两万字告诉你，一个40多岁的男人要“天真做少年”，需要付出怎样的代价？\n" +
//                "《刘若英：每个女人心里都卧虎藏龙》：是做循规蹈矩的俞秀莲，还是自由肆意的玉娇龙？如果两个都想，就是太贪心吗？\n" +
//                "《侯孝贤：一根老骨头，知道自己的样子》：《刺客聂隐娘》为什么会拍成这个样子？下一部电影会是什么？还有，他为什么从来不拍中老年人？\n" +
//                "……\n" +
//                "十篇文章，写了十个成功者内心的欲望、恐惧和挣扎。当你理解了他们，会更能应对自己在生活中碰到的难题。\n";
//        String url_1 = "https://img3.doubanio.com/view/subject/m/public/s29918393.jpg";
//        String url_2 = "https://img1.doubanio.com/view/subject/m/public/s29925287.jpg";
//        String url_3 = "https://img1.doubanio.com/view/subject/m/public/s29933617.jpg";
//        String url_4 = "https://img3.doubanio.com/view/subject/m/public/s29918393.jpg";
//        String url_5 = "https://img3.doubanio.com/view/subject/m/public/s29918393.jpg";
//
//
//        items.add(new Item("我们都是宇宙中的微尘", "李银河", "2018-11", "8.3", content_1,url_2));
//        items.add(new Item("海胆", "雷晓宇", "2018-11", "8.1", content_1,url_1));
//
//        items.add(new Item("进击的智人", "河森堡", "2018-12", "暂无评分", content_1,url_3));
//        items.add(new Item("我们都是宇宙中的微尘", "李银河", "2018-11", "8.3", content_1,url_2));
//        items.add(new Item("海胆", "雷晓宇", "2018-11", "8.1", content_1,url_1));
//        items.add(new Item("进击的智人", "河森堡", "2018-12", "暂无评分", content_1,url_3));
//        return items;
//
//    }
}

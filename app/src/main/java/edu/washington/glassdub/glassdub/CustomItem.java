package edu.washington.glassdub.glassdub;

/**
 * Created by Diana on 3/3/2017.
 */

public class CustomItem {
    private String title;
    private String subtitle;
    private String main;
    private int starCount;


    public CustomItem() {
        super();
    }

    public CustomItem(String title, String subtitle, String main, int count) {
        this.title = title;
        this.subtitle = subtitle;
        this.main = main;
        this.starCount = count;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getMain() {
        return main;
    }

    public int getStarCount() {return starCount; }
}

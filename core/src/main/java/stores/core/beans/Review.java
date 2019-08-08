package stores.core.beans;

import java.io.Serializable;
import java.util.List;

public class Review implements Serializable {

    private String title;
    private String rating;
    private String rateOne;
    private String rateTwo;
    private String rateThree;
    private String rateFour;
    private String rateFive;
    private String progressOne;
    private String progressTwo;
    private String progressThree;
    private String progressFour;
    private String progressFive;
    private List<Comment> comments = null;
    private final static long serialVersionUID = 6475852108181417597L;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRateOne() {
        return rateOne;
    }

    public void setRateOne(String rateOne) {
        this.rateOne = rateOne;
    }

    public String getRateTwo() {
        return rateTwo;
    }

    public void setRateTwo(String rateTwo) {
        this.rateTwo = rateTwo;
    }

    public String getRateThree() {
        return rateThree;
    }

    public void setRateThree(String rateThree) {
        this.rateThree = rateThree;
    }

    public String getRateFour() {
        return rateFour;
    }

    public void setRateFour(String rateFour) {
        this.rateFour = rateFour;
    }

    public String getRateFive() {
        return rateFive;
    }

    public void setRateFive(String rateFive) {
        this.rateFive = rateFive;
    }

    public String getProgressOne() {
        return progressOne;
    }

    public void setProgressOne(String progressOne) {
        this.progressOne = progressOne;
    }

    public String getProgressTwo() {
        return progressTwo;
    }

    public void setProgressTwo(String progressTwo) {
        this.progressTwo = progressTwo;
    }

    public String getProgressThree() {
        return progressThree;
    }

    public void setProgressThree(String progressThree) {
        this.progressThree = progressThree;
    }

    public String getProgressFour() {
        return progressFour;
    }

    public void setProgressFour(String progressFour) {
        this.progressFour = progressFour;
    }

    public String getProgressFive() {
        return progressFive;
    }

    public void setProgressFive(String progressFive) {
        this.progressFive = progressFive;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}

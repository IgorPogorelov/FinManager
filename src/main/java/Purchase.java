import java.io.Serializable;

public class Purchase implements Serializable {

    private String title;
    private String date;
    private Long sum;

    public Purchase(String title, String date, Long sum) {
        this.title = title;
        this.date = date;
        this.sum = sum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return date;
    }

    public void setData(String data) {
        this.date = data;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "title='" + title + '\'' +
                ", data='" + date + '\'' +
                ", sum=" + sum +
                '}';
    }
}

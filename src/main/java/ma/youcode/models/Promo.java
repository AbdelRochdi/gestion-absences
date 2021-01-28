package ma.youcode.models;

public class Promo {

    private int id;
    private String promo;

    public Promo(int id, String promo) {
        this.id = id;
        this.promo = promo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }
}

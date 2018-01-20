package entity;

public class Tariff {

    private int id;
    private String nameTariff;
    private int price;
    private String comment;

    public Tariff(String nameTariff, int price, String coment) {
        this.nameTariff = nameTariff;
        this.price = price;
        this.comment = coment;
    }

    public Tariff(int id, String nameTariff, int price, String coment) {

        this.id = id;
        this.nameTariff = nameTariff;
        this.price = price;
        this.comment = coment;
    }

    public Tariff() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameTariff() {
        return nameTariff;
    }

    public void setNameTariff(String nameTariff) {
        this.nameTariff = nameTariff;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String coment) {
        this.comment = coment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tariff tariff = (Tariff) o;

        if (id != tariff.id) return false;
        if (price != tariff.price) return false;
        if (nameTariff != null ? !nameTariff.equals(tariff.nameTariff) : tariff.nameTariff != null) return false;
        return comment != null ? comment.equals(tariff.comment) : tariff.comment == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nameTariff != null ? nameTariff.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", nameTariff='" + nameTariff + '\'' +
                ", price=" + price +
                ", comment='" + comment + '\'' +
                '}';
    }
}

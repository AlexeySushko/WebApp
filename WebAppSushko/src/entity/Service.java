package entity;

public class Service {
    private int id;
    private String nameService;
    private int price;
    private String comment;

    public Service(int id, String nameService, int price, String comment) {
        this.id = id;
        this.nameService = nameService;
        this.price = price;
        this.comment = comment;
    }

    public Service() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
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

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        if (id != service.id) return false;
        if (price != service.price) return false;
        if (nameService != null ? !nameService.equals(service.nameService) : service.nameService != null) return false;
        return comment != null ? comment.equals(service.comment) : service.comment == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nameService != null ? nameService.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Service{" +
                "nameService='" + nameService + '\'' +
                ", price=" + price +
                ", comment='" + comment + '\'' +
                '}';
    }
}

package entity;


public class Goods {
    private int goods_id;
    private String name;
    private String imagePath;
    private int price;
    private String description;
    private int del_status;

    public Goods(int goods_id, String name, String imagePath, int price, String descriptions, int del_status) {
        this.goods_id = goods_id;
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;
        this.description = descriptions;
        this.del_status = del_status;
    }

    public Goods(int goods_id) {
        this.goods_id = goods_id;
        this.name ="";
        this.imagePath ="";
        this.price = 0;
        this.description = "";
        this.del_status = 0;
    }

    public Goods(int goods_id, String imagePath) {
        this.goods_id = goods_id;
        this.imagePath = imagePath;
    }


    public Goods(int goods_id, String name, int price, String description) {
        this.goods_id = goods_id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDel_status() {
        return del_status;
    }

    public void setDel_status(int del_status) {
        this.del_status = del_status;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goods_id=" + goods_id +
                ", name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", del_status=" + del_status +
                '}';
    }
}

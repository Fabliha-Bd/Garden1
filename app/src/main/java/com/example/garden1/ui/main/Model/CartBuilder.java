package com.example.garden1.ui.main.Model;

public class CartBuilder {
    private String cartId;
    private String name;
    private float price;
    private String type;
    private int quantity;
    private String imageUrl;
    private String availableStatus;
    private float discount;
    private CartBuilder(){

    }
    public static class Builder{
        private String cartId;
        private String name;
        private float price;
        private String type;
        private int quantity;
        private String imageUrl;
        private String availableStatus;
        private float discount;
        public Builder(){

        }

        public Builder setCartId(String cartId) {
            this.cartId = cartId;
            return  this;
        }

        public Builder setName(String name) {
            this.name = name;
            return  this;
        }

        public Builder setPrice(float price) {
            this.price = price;
            return  this;
        }

        public Builder setType(String type) {
            this.type = type;
            return  this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setAvailableStatus(String availableStatus) {
            this.availableStatus = availableStatus;
            return  this;
        }

        public Builder setDiscount(float discount) {
            this.discount = discount;
            return this;
        }
        public CartBuilder build(){
            CartBuilder cart= new CartBuilder();
            cart.cartId = this.cartId;
            cart.name = this.name;
            cart.price = this.price;
            cart.type = this.type;
            cart.quantity = this.quantity;
            cart.imageUrl = this.imageUrl;
            cart.availableStatus = this.availableStatus;
            cart.discount = this.discount;
            return cart;

        }

    }

    @Override
    public String toString() {
        return "CartBuilder{" +
                "cartId='" + cartId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                ", imageUrl='" + imageUrl + '\'' +
                ", availableStatus='" + availableStatus + '\'' +
                ", discount=" + discount +
                '}';
    }

    public String getCartId() {
        return cartId;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAvailableStatus() {
        return availableStatus;
    }

    public float getDiscount() {
        return discount;
    }
}

package com.example.eclothes;

import java.util.Comparator;
import java.lang.Float;

public class EntityNews {
    String image;
    String productname;
    String productprice;
    String productdescription;
    String productid;

    public EntityNews(String image, String productname, String productprice, String productdescription, String productid) {
        this.image = image;
        this.productname = productname;
        this.productprice = productprice;
        this.productdescription = productdescription;
        this.productid = productid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public static Comparator<EntityNews> PriceUp = new Comparator<EntityNews>() {
        @Override
        public int compare(EntityNews o1, EntityNews o2) {
            float a = Float.parseFloat(o1.getProductprice());
            float b = Float.parseFloat(o2.getProductprice());

            if (Float.compare(a, b) == 0)
                return 0;
            else if (Float.compare(a, b) < 0)
                return -1;
            else
                return 1;
        }
    };
    public static Comparator<EntityNews> PriceDown = new Comparator<EntityNews>() {
        @Override
        public int compare(EntityNews o1, EntityNews o2) {
            float a = Float.parseFloat(o1.getProductprice());
            float b = Float.parseFloat(o2.getProductprice());

            if (Float.compare(b, a) == 0)
                return 0;
            else if (Float.compare(b, a) < 0)
                return -1;
            else
                return 1;
        }
    };

}

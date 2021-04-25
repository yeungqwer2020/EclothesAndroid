package com.example.eclothes.search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eclothes.R;
import com.example.eclothes.APIManager;
import com.example.eclothes.API.AuthorizationInterceptor;
import com.example.eclothes.Models.Favorite;
import com.example.eclothes.Models.Following;
import com.example.eclothes.Models.Product;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder> {
    private List<Product> productList;
    private LinearLayout.LayoutParams removeMargin;
    private String userId;
    private String[] favoriteIdList;
    private List<Favorite> favoriteList;

    ProductRecyclerViewAdapter(List<Product> merchantList, String userId){
        this.productList = merchantList;
        this.userId = userId;
        favoriteIdList = new String[getItemCount()];

        removeMargin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        removeMargin.setMargins(0, 0, 0, 0);

        getFavorites(userId);
    }
    @Override
    public ProductRecyclerViewAdapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_layout,parent,false);
        return new ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ProductRecyclerViewAdapter.ProductViewHolder holder, final int position) {
        final Product product = productList.get(position);
        holder.merchant_shop_name.setText(product.getMerchant().getShopName());
        holder.product_name.setText(product.getName());
        holder.product_description.setText(product.getDesc());
        holder.product_price.setText(product.getPrice() + "");
        Picasso.get().load("http://ec2-54-175-85-90.compute-1.amazonaws.com/photo/merchants/" + product.getMerchant().get_id() + "/" + product.getMerchant().getPhoto()).error(R.mipmap.ic_mobile_shopping_clothes_500_gray).into(holder.merchant_shop_image);
        Picasso.get().load("http://ec2-54-175-85-90.compute-1.amazonaws.com/photo/merchants/" + product.getMerchant().get_id() + "/products/" + product.get_id() + "/" + product.getPhotos().get(0)).error(R.mipmap.ic_mobile_shopping_clothes_500_gray).into(holder.product_image);
        if (holder.product_favourite.isChecked() != (favoriteIdList[position] != null)) {
            holder.product_favourite.setChecked(favoriteIdList[position] != null);
        }

        if (product.getStyle() == null) {
            holder.product_style.setText("");
            holder.product_style.setVisibility(View.INVISIBLE);
            //holder.product_style.setLayoutParams(removeMargin);
        } else {
            holder.product_style.setVisibility(View.VISIBLE);
            holder.product_style.setText(product.getStyle());
        }

        holder.product_favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                 @Override
                 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                     Log.i("onCheckedChanged", "product_favourite: " + isChecked + " - " + product.getName() + ", " + product.get_id());
                     if(!buttonView.isPressed()) {
                         Log.i("onCheckedChanged", "It is not Pressed!");
                         return;
                     }

                     if (isChecked) {
                         createFavorite(product.get_id());
                         getFavorites(userId);
                     } else {
                         removeFavorite(favoriteIdList[position]);
                         favoriteIdList[position] = null;
                     }
                 }
             }
        );
    }

    private void getFavorites(String userId) {
        Call<List<Favorite>> call = APIManager.getInstance().getAPIService().getFavorites(userId);

        call.enqueue(new Callback<List<Favorite>>() {
            @Override
            public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response) {
                if (!response.isSuccessful()) {
                    Log.i("onResponse", "Fail!");
                    return;
                }

                favoriteList = response.body();

                if (favoriteList != null && favoriteList.size() > 0) {
                    for (int i = 0; i < getItemCount(); i++) {
                        for (Favorite favorite : favoriteList) {
                            if (favorite.getProduct().get_id().equals(productList.get(i).get_id())) {
                                favoriteIdList[i] = favorite.get_id();
                                break;
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Favorite>> call, Throwable t) {
                Log.d("Get Favorites", "failure");
            }
        });
    }

    private void createFavorite(String productId) {
        AuthorizationInterceptor.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwNzAzYTk4ZTMzNGY2NDY5NzFhMWRjNSIsImlhdCI6MTYxOTMwMDIzMSwiZXhwIjoxNjI3MDc2MjMxfQ.gooqnVKv_nHUXtrImlIQ8ZzFP5dJ61yhamtXxBxfaow");

        HashMap<String, String> map = new HashMap<>();
        map.put("product", productId);

        Call<Favorite> call = APIManager.getInstance().getAPIService().createFavorite(map);
        call.enqueue(new Callback<Favorite>() {
            @Override
            public void onResponse(Call<Favorite> call, Response<Favorite> response) {
                if (!response.isSuccessful()) {
                    Log.i("onResponse", "Fail!" + response.code());
                    return;
                }
                Log.i("onResponse", "" + response.code());

                Favorite favorite = response.body();
            }

            @Override
            public void onFailure(Call<Favorite> call, Throwable t) {
                Log.i("onFailure", "Fail!");
            }
        });
    }

    private void removeFavorite(String favoriteId) {
        AuthorizationInterceptor.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwNzAzYTk4ZTMzNGY2NDY5NzFhMWRjNSIsImlhdCI6MTYxOTMwMDIzMSwiZXhwIjoxNjI3MDc2MjMxfQ.gooqnVKv_nHUXtrImlIQ8ZzFP5dJ61yhamtXxBxfaow");

        Call<ResponseBody> call = APIManager.getInstance().getAPIService().removeFavorite(favoriteId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful() && response.code() == 200){
                    Log.d("Remove Favorite", "remove - Success");
                }
                try {
                    Log.d("Remove Favorite", response.code() + response.errorBody().string() + "");
                } catch (Exception e) {
                    Log.d("Null message", e.getMessage().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Remove Favorite", "failure");
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView merchant_shop_name, product_name, product_description,
                product_style, product_price_type, product_price;
        private ImageView merchant_shop_image, product_image;
        private CheckBox product_favourite;
        private CardView shop_card_view;
        public ProductViewHolder(View view) {
            super(view);
            merchant_shop_name = (TextView) view.findViewById(R.id.merchant_shop_name);
            product_name = (TextView) view.findViewById(R.id.product_name);
            product_description = (TextView) view.findViewById(R.id.product_description);
            product_style = (TextView) view.findViewById(R.id.product_style);
            product_price_type = (TextView) view.findViewById(R.id.product_price_type);
            product_price = (TextView) view.findViewById(R.id.product_price);
            merchant_shop_image = (ImageView) view.findViewById(R.id.merchant_shop_image);
            product_image = (ImageView) view.findViewById(R.id.product_image);
            product_favourite = (CheckBox) view.findViewById(R.id.product_favourite);
            shop_card_view = (CardView) view.findViewById(R.id.shop_card_view);
        }
    }
}

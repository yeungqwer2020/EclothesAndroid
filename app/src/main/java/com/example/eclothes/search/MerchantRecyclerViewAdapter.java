package com.example.eclothes.search;

import android.app.Activity;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eclothes.AddproductActivity;
import com.example.eclothes.R;
import com.example.eclothes.APIManager;
import com.example.eclothes.API.AuthorizationInterceptor;
import com.example.eclothes.Models.Following;
import com.example.eclothes.Models.Merchant;

import com.example.eclothes.ShopmainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchantRecyclerViewAdapter extends RecyclerView.Adapter<MerchantRecyclerViewAdapter.MerchantViewHolder> {
    private List<Merchant> merchantList;
    private LinearLayout.LayoutParams removeMargin, defaultMargin, districtParams;
    private final int defaultFontSize = 14;
    private String userId;
    private String[] followingIdList;
    private List<Following> followingList;
    private Activity activity;

    MerchantRecyclerViewAdapter(Activity activity, List<Merchant> merchantList, String userId){
        this.activity = activity;
        this.merchantList = merchantList;
        this.userId = userId;
        followingIdList = new String[getItemCount()];

        removeMargin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        defaultMargin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        districtParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        removeMargin.setMargins(0, 0, 0, 0);

        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        int px = (int) Math.ceil(5 * logicalDensity);
        defaultMargin.setMargins(px, px, px, px);
        districtParams.setMargins(px, px, px, px);
        districtParams.gravity = Gravity.END;
        Log.i("MerchantViewAdapter", "Checking" + logicalDensity + ',' + px);

        getFollowings(userId);
    }
    @Override
    public MerchantRecyclerViewAdapter.MerchantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_card_layout,parent,false);
        return new MerchantViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MerchantRecyclerViewAdapter.MerchantViewHolder holder, final int position) {
        final Merchant merchant = merchantList.get(position);
        holder.shop_name.setText(merchant.getShopName());
        holder.shop_desc.setText(merchant.getDesc());
        Picasso.get().load("http://ec2-54-175-85-90.compute-1.amazonaws.com/photo/merchants/" + merchant.get_id() + "/" + merchant.getPhoto()).error(R.mipmap.ic_mobile_shopping_clothes_500_gray).into(holder.shop_image);
        if (holder.shop_favourite.isChecked() != (followingIdList[position] != null)) {
            holder.shop_favourite.setChecked(followingIdList[position] != null);
        }

        Log.i("onBindViewHolder", "Check font size" + defaultFontSize);

        if (merchant.getLocation() == null) {
            holder.address_label.setTextSize(0);
            holder.address_label.setLayoutParams(removeMargin);

            holder.location_text.setTextSize(0);
            holder.location_text.setLayoutParams(removeMargin);

            holder.district_text.setTextSize(0);
            holder.district_text.setLayoutParams(removeMargin);
            holder.district_text.setVisibility(View.INVISIBLE);

            holder.shop_desc_hr.setVisibility(View.INVISIBLE);

            Log.i("onBindViewHolder", "Remove address layout!" + merchant.getShopName());
        } else {
            holder.address_label.setTextSize(defaultFontSize);
            holder.address_label.setLayoutParams(defaultMargin);

            holder.location_text.setTextSize(defaultFontSize);
            holder.location_text.setLayoutParams(defaultMargin);
            holder.location_text.setText(merchant.getLocation().getAddress());

            holder.district_text.setTextSize(defaultFontSize);
            holder.district_text.setLayoutParams(districtParams);
            holder.district_text.setVisibility(View.VISIBLE);
            holder.district_text.setText(merchant.getLocation().getDistrict().getDistrict());

            holder.shop_desc_hr.setVisibility(View.VISIBLE);

            Log.i("onBindViewHolder", "Set address layout!" + merchant.getShopName());
        }

        holder.shop_favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                 @Override
                 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                     Log.i("onCheckedChanged", "shop_favourite: " + isChecked + " - " + merchant.getShopName() + merchant.get_id());
                     if(!buttonView.isPressed()) {
                         Log.i("onCheckedChanged", "It is not Pressed!");
                         return;
                     }

                     if (isChecked) {
                         addFollowing(merchant.get_id());
                         getFollowings(userId);
                     } else {
                         removeFollowing(followingIdList[position]);
                         followingIdList[position] = null;
                     }
                 }
             }
        );

        holder.shop_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("shop_card.Listener", merchant.get_id());
                Intent intent = new Intent(activity, ShopmainActivity.class);
                intent.putExtra("merchantid", merchant.get_id());
                activity.startActivity(intent);
            }
        });
    }

    private void getFollowings(String userId) {
        Call<List<Following>> call = APIManager.getInstance().getAPIService().getFollowings(userId);

        call.enqueue(new Callback<List<Following>>() {
            @Override
            public void onResponse(Call<List<Following>> call, Response<List<Following>> response) {
                if (!response.isSuccessful()) {
                    Log.i("onResponse", "Fail!");
                    return;
                }

                followingList = response.body();

                if (followingList != null && followingList.size() > 0) {
                    for (int i = 0; i < getItemCount(); i++) {
                        for (Following following : followingList) {
                            if (following.getMerchant().get_id().equals(merchantList.get(i).get_id())) {
                                followingIdList[i] = following.get_id();
                                break;
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Following>> call, Throwable t) {
                Log.d("Get Followings", "failure");
            }
        });
    }

    private void addFollowing(String merchantId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("merchant", merchantId);

        Call<Following> call = APIManager.getInstance().getAPIService().addFollowing(map);

        call.enqueue(new Callback<Following>() {
            @Override
            public void onResponse(Call<Following> call, Response<Following> response) {
                if(response.isSuccessful() && response.code() == 200){
                    Log.d("Follow", " Success");
                }
                try {
                    Log.d("Follow", response.code() + response.errorBody().string() + "");
                } catch (Exception e) {
                    Log.d("Null message", e.getMessage().toString());
                }
            }

            @Override
            public void onFailure(Call<Following> call, Throwable t) {
                Log.d("Follow", "failure");
            }
        });
    }

    private void removeFollowing(String followingId) {
        Call<ResponseBody> call = APIManager.getInstance().getAPIService().removeFollowing(followingId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful() && response.code() == 200){
                    Log.d("Remove Follow", "remove - Success");
                }
                try {
                    Log.d("Remove Follow", response.code() + response.errorBody().string() + "");
                } catch (Exception e) {
                    Log.d("Null message", e.getMessage().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Remove Follow", "failure");
            }
        });
    }


    @Override
    public int getItemCount() {
        return merchantList.size();
    }
    public class MerchantViewHolder extends RecyclerView.ViewHolder {
        private TextView shop_name, shop_desc, address_label, location_text, district_text;
        private TableRow shop_desc_hr;
        private ImageView shop_image;
        private CheckBox shop_favourite;
        private CardView shop_card_view;
        public MerchantViewHolder(View view) {
            super(view);
            shop_name = (TextView) view.findViewById(R.id.shop_name);
            shop_desc = (TextView) view.findViewById(R.id.shop_desc);
            shop_desc_hr = (TableRow) view.findViewById(R.id.shop_desc_hr);
            address_label = (TextView) view.findViewById(R.id.address_label);
            location_text = (TextView) view.findViewById(R.id.location_text);
            district_text = (TextView) view.findViewById(R.id.district_text);
            shop_image = (ImageView) view.findViewById(R.id.shop_image);
            shop_favourite = (CheckBox) view.findViewById(R.id.shop_favourite);
            shop_card_view = (CardView) view.findViewById(R.id.shop_card_view);
        }
    }
}

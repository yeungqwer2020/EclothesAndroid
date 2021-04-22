package com.example.eclothes.API;

import com.example.eclothes.Models.CurrentMerchant;
import com.example.eclothes.Models.CurrentUser;
import com.example.eclothes.Models.Favorite;
import com.example.eclothes.Models.Following;
import com.example.eclothes.Models.Merchant;
import com.example.eclothes.Models.Product;
import com.example.eclothes.Models.User;
import com.example.eclothes.Models.Comment;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    // Register
    @POST("api/v1/users/register")
    Call<CurrentUser> register(@Body User user);

    @POST("api/v1/merchants/register")
    Call<CurrentMerchant> register(@Body Merchant merchant);


    // Login
    @POST("api/v1/users/login")
    Call<CurrentUser> login(@Body User user);

    @POST("api/v1/merchants/login")
    Call<CurrentMerchant> login(@Body Merchant merchant);


    // User
    @GET("api/v1/users")
    Call<List<User>> getUsers();

    @GET("api/v1/users/{id}")
    Call<Product> getUser(@Path("id") String userId);


    // Merchant
    @GET("api/v1/merchants")
    Call<List<Merchant>> getMerchants(@Query("shopName[regex]") String keyword);

    @GET("api/v1/merchants/{id}")
    Call<Product> getMerchant(@Path("id") String merchantId);


    // Product
    @GET("api/v1/products")
    Call<List<Product>> getProducts(
            @Query("name[regex]") String keyword,
            @Query("sort") String sort,
            @Query("category") String category,
            @Query("style[regex]") String style,
            @Query("price[gte]") Double minPrice,
            @Query("price[lte]") Double maxPrice
    );

    @GET("api/v1/products/{id}")
    Call<Product> getProduct(@Path("id") String productId);

    @GET("api/v1/merchants/{id}/products")
    Call<List<Product>> getProductsByMerchant(
            @Path("id") String merchantId,
            @Query("name[regex]") String keyword,
            @Query("sort") String sort,
            @Query("category") String category,
            @Query("style[regex]") String style,
            @Query("price[gte]") Double minPrice,
            @Query("price[lte]") Double maxPrice
    );

    @Multipart
    @POST("api/v1/products")
    Call<Product> createProduct(@Body Product product);


    @DELETE("api/v1/products/{id}")
    Call<ResponseBody> deleteProduct(@Body String productId);



    // Comment
    @GET("api/v1/products/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") String productId);

    @GET("api/v1/comments")
    Call<List<Comment>> getComments();

    @GET("api/v1/comments/{id}")
    Call<Product> getComment(@Path("id") String commentId);

    @POST("api/v1/comments")
    Call<Comment> createComment(@Body Comment comment);

    @DELETE("api/v1/comments/{id}")
    Call<ResponseBody> deleteComment(@Path("id") String commentId);



    // Favorite
    @GET("users/{id}/favorites")
    Call<List<Favorite>> getFavorites(@Path("id") String userId);

    @GET("comments")
    Call<List<Favorite>> getFavorites();

    @GET("favorites/{id}")
    Call<Favorite> getFavorite(@Path("id") String commentId);



    // Following
    @GET("users/{id}/followings")
    Call<List<Following>> getFollowings(@Path("id") String userId);

    @GET("comments")
    Call<List<Following>> getFollowings();

    @GET("followings/{id}")
    Call<Following> getFollowing(@Path("id") String commentId);

}

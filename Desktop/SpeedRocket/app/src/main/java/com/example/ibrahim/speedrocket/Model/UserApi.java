package com.example.ibrahim.speedrocket.Model;

import android.database.Observable;
import android.service.media.MediaBrowserService;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Ibrahim on 3/27/2018.
 */

public interface UserApi {

    @GET("websevUsers")
    Call<ResultModel> getPersonalusers();   // view user data

    @FormUrlEncoded
    @POST("websevUserDetails")
    Call<ResultModel> getProfileAccount(@Field("id") int id);


    @FormUrlEncoded
    @POST("websevCompanyDetails")
    Call<ResultModel> getCompanyAccount(@Field("id") int id);

    @FormUrlEncoded
    @POST("websevOffersDetails")
    Call<ResultModel> getOfferDetails(@Field("id") int id);

    @FormUrlEncoded
    @POST("websevGetProductDetails")
    Call<ResultModel> getProductDetails(@Field("id") int id);

    @GET("websevOffers")
    Call<ResultModel> getOffers();

    @GET("websevAllProducts")
    Call<ResultModel> getProducts();


    @FormUrlEncoded
    @POST("websevOffersInterest")
    Call<ResultModel> getOffersToUserInterest(@Field("interest") String interest);


    @FormUrlEncoded
    @POST("websevGetOfferByCompany")
    Call<ResultModel> getOffersToCompanyProfile(@Field("id") int id);

    @FormUrlEncoded
    @POST("websevGetProductByCompany")
    Call<ResultModel> getProductsToCompanyProfile(@Field("id") int id);

    @GET("websevGetUsersByAllOffer")
    Call<ResultModel> getUsersByOffers();

    @GET("StoreUser")
    Call<ResultModel> getErrors();

    @FormUrlEncoded
    @POST("websevRegistration")
    Call<ResultModel> adduser(@Field("firstName") String firstName,
                               @Field("lastName") String lastName,
                               @Field("email") String email,
                               @Field("password") String password,
                               @Field("gender") String gender,
                               @Field("language") String language,
                               @Field("interest") String interest,
                               @Field("mobile") String mobile,
                              @Field("city") String city,
                              @Field("country") String country
    );  // user Registration


    @FormUrlEncoded
    @POST("websevLogin")
    Call<ResultModel> loginuser(
            @Field("email") String email, @Field("password") String password
    );

    @FormUrlEncoded
    @POST("websevUserDetails")
    Call<ResultModel> getaccountbyid(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("websevGetUsersByIdOffer")
    Call<ResultModel> getEnteredOnOffer(
            @Field("offersId") int id
    );

    @Multipart
    @POST("webserrupdateUser")
    Call<ResultModel> uploadFile(@Part MultipartBody.Part file,
                                  @Part("file") RequestBody name
                                  );


    @GET("websevTypes")
    Call<ResultModel> getUsersInterest();


    @FormUrlEncoded
    @POST("websevStoreUserOffer")
    Call<ResultModel> enterOffer(@Field("userId") int userId,
    @Field("offersId") int offersId,
    @Field("srCoin") int srCoin);


    @GET("websevCategories")
    Call<ResultModel> getCategory();


    @FormUrlEncoded
    @POST("websevUserupdateCoins")
    Call<ResultModel> updateUserCoin(@Field("id") int id,
                              @Field("coins") int coins
    );  //  updateUserCoin



    @FormUrlEncoded
    @POST("WebServClosed")
    Call<ResultModel> test(@Field("id") int id

    );  //  updateUserCoin

    @FormUrlEncoded
    @POST("websevStoreWinner")
    Call<ResultModel> addProductOnWinners(@Field("userId") int userId,
                                     @Field("offerId") int offerId,
                                          @Field("srCoin") int winnerCoin
    );  //  addProductOnWinners

    @FormUrlEncoded
    @POST("websevWinnerUser")
    Call<ResultModel> getProductWinnersByUser(@Field("userId") int id);


    @FormUrlEncoded
    @POST("websevofferView")
    Call<ResultModel> counterViews(@Field("id") int id);

    @FormUrlEncoded
    @POST("websevUpdateImageUsers")
    Call<ResultModel> updateProfileImage(@Field("id") int id,
                                     @Field("image") String image
    );  //  updateProfileImage



    @Multipart
    @POST("websevUpdateImageUsers")
    Call<ResponseBody> postImage(@Part MultipartBody.Part file,
                                 @Part("file") RequestBody name
                                );
    //   @Part("name") RequestBody name,
    //@Part MultipartBody.Part image


    @FormUrlEncoded
    @POST("websevUpdateUser")
    Call<ResultModel> updateUser(@Field("id") int id,
                                 @Field("firstName") String firstName,
                                 @Field("lastName") String lastName,
                                 @Field("email") String email,
                                 @Field("gender") String gender,
                                 @Field("language") String language,
                                 @Field("interest") String interest,
                                 @Field("mobile") String mobile,
                                 @Field("city") String city,
                                 @Field("country") String country

    );  //  updateUserCoin


    @FormUrlEncoded
    @POST("websevProductsByCategory")
    Call<ResultModel> chooseCategory(
            @Field("categoryId") int categoryId
    );  //  updateProfileImage

    @FormUrlEncoded
    @POST("websevCompanies")
    Call<ResultModel> getCompany(@Field("userId") int id);


    @FormUrlEncoded
    @POST("WebServContact")
    Call<ResultModel> contactUs(@Field("name") String name,
                                     @Field("email") String email,
                                @Field("message") String message
    );  //  updateUserCoin

    @FormUrlEncoded
    @POST("websevDeleteCompany")
    Call<ResultModel> deleteCompany(@Field("id") int id);
    @FormUrlEncoded
    @POST("websevDeleteProduct")
    Call<ResultModel> deleteProduct(@Field("id") int id);
    @FormUrlEncoded
    @POST("websevDeleteOffer")
    Call<ResultModel> deleteOffer(@Field("id") int id);

    @FormUrlEncoded
    @POST("websevUpdateCompany")
    Call<ResultModel> updateCompany(@Field("id") int id);




    @FormUrlEncoded
    @POST("websevBanksOfUser")
    Call<ResultModel> getBankAccountWithUserId(@Field("userId") int userId);


    @FormUrlEncoded
    @POST("websevStoreBanks")
    Call<ResultModel> addBankAccount(
            @Field("userId") int userId,
            @Field("name") String name,
            @Field("bankAccount") String bankAccount,
            @Field("bankAddress") String bankAddress,
            @Field("swift") String swift

    );

    @FormUrlEncoded
    @POST("websevDeleteBank")
    Call<ResultModel> deleteBankAccount(@Field("id") int id);

    @FormUrlEncoded
    @POST("WebServStoreOrder")
    Call<ResultModel> orderCashOnDelivery(@Field("offerId") int offerId,
                                          @Field("userId") int userId,
                                          @Field("price") int price,
                                          @Field("type") int type,
                                           @Field("name") String name,
                                           @Field("phone") String phone,
                                           @Field("address") String address
    );


    @FormUrlEncoded
    @POST("WebServNetCash")
    Call<ResultModel> getMyCash(@Field("id") int id);


    @FormUrlEncoded
    @POST("WebServCashDaily")
    Call<ResultModel> getMyCashDay(@Field("id") int id);


    @FormUrlEncoded
    @POST("WebServCashMonth")
    Call<ResultModel> getMyCashMonth(@Field("id") int id);



} // UserApi

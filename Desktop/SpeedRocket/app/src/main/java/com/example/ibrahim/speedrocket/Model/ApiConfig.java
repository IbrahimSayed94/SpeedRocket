package com.example.ibrahim.speedrocket.Model;




import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Ibrahim on 5/2/2018.
 */

public interface ApiConfig
{

    @Multipart
    @POST("websevUpdateImageUsers")
    Call <ServerResponse>uploadFile(
            @Part("file") RequestBody name,
                                    @Part MultipartBody.Part file,
                                    @Part("id") Integer id
                                   ); // name and file ----> contain Image



    @Multipart
    @POST("websevAddCompany")
    Call <ServerResponse>uploadFileAndCompanyProfile(
            @Part("file") RequestBody name,
            @Part MultipartBody.Part file,
            @Part("en_name") String en_name,
            @Part("ar_name") String ar_name,
            @Part("email") String email,
            @Part("country") String country,
            @Part("categoryId") int categoryId,
            @Part("city") String city,
            @Part("phone") String phone,
            @Part("fax") String fax,
            @Part("userId") int userId
    );

    @Multipart
@POST("websevUpdateCompany")
Call <ServerResponse>uploadFileAndUpdateCompanyProfile(
                @Part("file") RequestBody name,
                @Part MultipartBody.Part file,
                @Part("id") Integer id ,
                @Part("en_name") String en_name,
                @Part("ar_name") String ar_name,
                @Part("email") String email,
                @Part("country") String country,
                @Part("categoryId") Integer categoryId,
                @Part("city") String city,
                @Part("phone") String phone,
                @Part("fax") String fax,
                @Part("userId") Integer userId
        );


    @FormUrlEncoded
    @POST("websevUpdateCompany")
    Call <ServerResponse>uploadFileAndUpdateCompanyProfile2(
            @Field("id") int id ,
            @Field("en_name") String en_name,
            @Field("ar_name") String ar_name,
            @Field("email") String email,
            @Field("country") String country,
            @Field("categoryId") int categoryId,
            @Field("city") String city,
            @Field("phone") String phone,
            @Field("fax") String fax,
            @Field("userId") int userId
    );


    @Multipart
    @POST("websevAddProduct")
    Call <ServerResponse>uploadFileAndProductData(
            @Part("file") RequestBody name,
            @Part MultipartBody.Part file,
            @Part("en_title") String en_title,
            @Part("ar_title") String ar_title,
            @Part("en_description") String en_description,
            @Part("ar_description") String ar_description,
            @Part("price") String price,
            @Part("companyId") int companyId
    );


    @FormUrlEncoded
    @POST("websevUpdateProduct")
    Call <ServerResponse>uploadFileAndProductData1(
            @Field("id") int id ,
            @Field("en_title") String en_title,
            @Field("ar_title") String ar_title,
            @Field("en_description") String en_description,
            @Field("ar_description") String ar_description,
            @Field("price") String price,
            @Field("companyId") int companyId
    );

    @Multipart
    @POST("websevUpdateProduct")
    Call <ServerResponse>uploadFileAndProductData2(
            @Part("file") RequestBody name,
            @Part MultipartBody.Part file,
            @Part("id") Integer id ,
            @Part("en_title") String en_title,
            @Part("ar_title") String ar_title,
            @Part("en_description") String en_description,
            @Part("ar_description") String ar_description,
            @Part("price") String price,
            @Part("companyId") Integer companyId
    );



    @Multipart
    @POST("websevAddOffers")
    Call <ServerResponse>uploadFileAndOfferData(
            @Part("file") RequestBody name,
            @Part MultipartBody.Part file,
            @Part("startTime") String startTime,
            @Part("endTime") String endTime,
            @Part("discount") String discount,
            @Part("productId") String  ProductId,
            @Part("packageId") int packageId,
            @Part("companyId") int companyId

    );


    @Multipart
    @POST("websevAddeOfferImage")
    Call <ServerResponse>uploadImagesWithOffer(
           @Part("file") RequestBody name,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("websevUpdateOffer")
    Call <ServerResponse>uploadFileAndOfferData1(
            @Field("id") int id ,
            @Field("en_title") String en_title,
            @Field("ar_title") String ar_title,
            @Field("en_description") String en_description,
            @Field("ar_description") String ar_description,
            @Field("price") String price,
            @Field("type") String type,
            @Field("startTime") String startTime,
            @Field("endTime") String endTime,
            @Field("discount") String discount,
            @Field("count") String count,
            @Field("srcoin") String srcoin,
            @Field("minutes") int minutes,
            @Field("country") int country,
            @Field("companyId") int companyId
    );


    @Multipart
    @POST("websevUpdateOffer")
    Call <ServerResponse>uploadFileAndOfferData2(
            @Field("id") int id ,
            @Part("file") RequestBody name,
            @Part MultipartBody.Part file,
            @Part("en_title") String en_title,
            @Part("ar_title") String ar_title,
            @Part("en_description") String en_description,
            @Part("ar_description") String ar_description,
            @Part("price") String price,
            @Part("type") String type,
            @Part("startTime") String startTime,
            @Part("endTime") String endTime,
            @Part("discount") String discount,
            @Part("count") String count,
            @Part("srcoin") String srcoin,
            @Part("minutes") int minutes,
            @Part("country") int country,
            @Part("companyId") int companyId

    );
}

package br.com.wirecard.plugin.api

import androidx.annotation.VisibleForTesting
import br.com.wirecard.base.business.dto.Token
import br.com.wirecard.feature.order.business.dto.OrderList
import br.com.wirecard.feature.order.business.dto.OrderFilters
import br.com.wirecard.model.Order
import retrofit2.Call
import retrofit2.http.*

interface RetrofitAPI {

    @FormUrlEncoded
    @POST("oauth/token")
    fun getToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("device_id") deviceId: String,
        @Field("scope") scope: String): Call<Token>


    @GET("orders")
    fun getOrders(@Query("filters") filters: String,
                  @Query("limit") limit: Int,
                  @Query("offset") offset: Int): Call<OrderList>


    @GET("orders/{orderId}")
    fun getOrderById(@Path("orderId") orderId: String): Call<Order>


    @GET("test")
    @VisibleForTesting
    fun dumbRequest(): Call<Any?>
}
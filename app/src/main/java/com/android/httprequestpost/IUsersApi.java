package com.android.httprequestpost;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IUsersApi {
    @POST("/api/users")
    Call<User> createUser(@Body User user);
}

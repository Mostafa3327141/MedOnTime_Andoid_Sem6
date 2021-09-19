package fingertiptech.medontime.ui.jsonplaceholder;

import java.util.List;

import fingertiptech.medontime.ui.model.TestComment;
import fingertiptech.medontime.ui.model.TestItem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface test_JSONPlaceholder {

    @GET("posts")
    Call<List<TestItem>> getItems();


    // 2 way to GET from url, one is use path one is use query
    // But when you call it is the same
    // Call<List<TestComment>> callComments = testJsonPlaceholder.getComments(2);
//    https://jsonplaceholder.typicode.com/posts/1/comments
//    @GET("post/{id}/comments")
//    Call<List<TestComment>> getComments(@Path("id") int postId);

//    https://jsonplaceholder.typicode.com/comments?postId=1
    @GET("comments")
    Call<List<TestComment>> getComments(@Query("postId") int postId);

    // There is 2 way for POST,
    // this is for Call<TestItem> call = testJsonPlaceholder.createItem(testItem);
    @POST("posts")
    Call<TestItem> createItem(@Body TestItem testItem);

    // this is for Call<TestItem> call = testJsonPlaceholder.createItem("20","test", "body test");
    @FormUrlEncoded
    @POST("posts")
    Call<TestItem> createItem(@Field("userId") String userId,
                              @Field("title") String title,
                              @Field("body") String text);

    // Different between PUT and PATCH
    // If you want update data using PUT you need to send whole attribute,
    // However, if you using PATCH you can just send the filed you want to update
    // e.g. there is data for person {"first name":"nancy","last name":"chen"}
    // you want to update my "first name", if you use PUT you need to send "first name" and "last name" together
    // as for PATCH just send "first name"
    @PUT("posts/{id}")
    Call<TestItem> putItem(@Path("id") int id, @Body TestItem testItem);

    @PATCH("posts/{id}")
    Call<TestItem> patchItem(@Path("id") int id,@Body TestItem testItem );

    @DELETE("posts/{id}")
    Call<Void> deleteItem(@Path("id") int id);

}

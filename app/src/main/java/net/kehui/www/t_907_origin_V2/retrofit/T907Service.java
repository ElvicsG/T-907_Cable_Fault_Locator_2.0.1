package net.kehui.www.t_907_origin_V2.retrofit;

import net.kehui.www.t_907_origin_V2.bean907.AssistHelpVO;
import net.kehui.www.t_907_origin_V2.bean.BaseResponseAssistListDO1;
import net.kehui.www.t_907_origin_V2.bean907.AssistListVOBean;
import net.kehui.www.t_907_origin_V2.bean907.BaseResponseAssistDetailBean;
import net.kehui.www.t_907_origin_V2.bean907.BaseResponseAssistListDOBean;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * 基于T907Service服务器创建Java接口
 */
public interface T907Service {
    //1.方法注解    @GET @POST @PUT @DELETE @PATCH @HEAD @OPTIONS @HTTP()
    //2.标记注解    @FormUrlEncoded @Multipart @Streaming
    //3.参数配置    @Query() @QueryMap @Body @Field() @FieldMap @Part @PartMap

    //一、协助记录获取方法：get  //GC20231220    后台定义方法错误，弃用
    @GET("assistList/get")  //@GET  //协助记录获取1，无法得到返回值
    Call<ResponseBody> get(@Query("deviceId") String deviceId, @Query("testTime") String testTime, @Query("replyStatus") int replyStatus);

    @HTTP(method = "get",path = "assistList/",hasBody = true)  //@HTTP自定义   //协助记录获取2 使用错误:get方法无法调用Body
    Flowable<BaseResponseAssistListDO1> httpGetBody(@Body AssistListVOBean param);

    @GET("assistList/get")  //@GET  //协助记录获取3，添加适配器观察，无法得到返回值
    Flowable<BaseResponseAssistListDO1> getRxjava3(@Query("deviceId") String deviceId, @Query("testTime") String testTime, @Query("replyStatus") int replyStatus);

    //一、协助记录获取方法：post  //GC20231223   后台定义方法改为post
    @POST("assistList") //协助记录获取post1
    @FormUrlEncoded
    Call<BaseResponseAssistListDO1> post1(@Field("param") String json);

    @POST("assistList") //协助记录获取post2   //定义的BaseResponseAssistListDO1不对，所以反馈不对 post1、2、3这里都错
    Call<BaseResponseAssistListDO1> post2(@Body AssistListVOBean param);
//    Call<BaseResponseAssistListDO1> post2(@Body RequestBody requestBody);

    @POST("assistList") //协助记录获取post3
    @Multipart
    Call<BaseResponseAssistListDO1> post3(@Part MultipartBody.Part param);

    @POST("assistList") //协助记录获取post4   //不支持@FormUrlEncoded
    @FormUrlEncoded
    Call<BaseResponseAssistListDOBean> post4(@Field("param") String json);

    @POST("assistList") //协助记录获取post5
    Call<BaseResponseAssistListDOBean> postAssistListVO(@Body AssistListVOBean param);
//    Call<BaseResponseAssistListDOBean> postAssistListVO(@Body RequestBody requestBody);   //更改定义的方法也可以，但写法麻烦

    @POST("assistList") //协助记录获取post6   //不支持multipart/form-data
    @Multipart
//    Call<BaseResponseAssistListDOBean> post6(@Part MultipartBody.Part param); //对应结果1、2、3
//    Call<BaseResponseAssistListDOBean> post6(@Part("param") RequestBody param); //对应结果5
    Call<BaseResponseAssistListDOBean> post6(@Part("param") AssistListVOBean param);  //对应结果5

    //二、协助详情获取    //GC20231221
    @GET("assistDetail")
    Call<ResponseBody> getResponseBody(@Query("assistId") String assistId);

    @GET("assistDetail")
    Call<BaseResponseAssistDetailBean> getAssistDetail(@Query("assistId") String assistId);

    //三、协助信息上传    //GC20231222
    @POST("assistUpload")
    @Multipart
    Call<ResponseBody> upload(@Part("formParam") AssistHelpVO formParam, @Part MultipartBody.Part faData);
    /*  @Part扩展
        如果类型是okhttp3.MultipartBody.Part，内容将被直接使用；
        省略part中的名称,即 @Part MultipartBody.Part part
        如果类型是RequestBody，那么该值将直接与其内容类型一起使用；
        在注释中提供part名称（例如，@Part（“foo”）RequestBody foo）
        其他对象类型将通过使用转换器转换为适当的格式；
        在注释中提供part名称（例如，@Part（“foo”）Image photo）
        */
}

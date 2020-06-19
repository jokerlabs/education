package com.example.vodservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.example.baseservice.exception.MyException;
import com.example.vodservice.service.VodService;
import com.example.vodservice.utils.VodConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideo(MultipartFile file) {
        String accessKeyId = VodConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = VodConstantPropertiesUtils.ACCESS_KEY_SECRET;

        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
            // filename, 原始文件名
            String fileName = file.getOriginalFilename();
            // 上传后标题名
            assert fileName != null;
            String title = fileName.substring(0, fileName.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
            //request.setShowWaterMark(true);
            /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
            //request.setCallback("http://callback.sample.com");
            /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
            //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
            /* 视频分类ID(可选) */
            //request.setCateId(0);
            /* 视频标签,多个用逗号分隔(可选) */
            //request.setTags("标签1,标签2");
            /* 视频描述(可选) */
            //request.setDescription("视频描述");
            /* 封面图片(可选) */
            //request.setCoverURL("http://cover.sample.com/sample.jpg");
            /* 模板组ID(可选) */
            //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
            /* 工作流ID(可选) */
            //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
            /* 存储区域(可选) */
            //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
            /* 开启默认上传进度回调 */
            // request.setPrintProgress(true);
            /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
            // request.setProgressListener(new PutObjectProgressListener());
            /* 设置应用ID*/
            //request.setAppId("app-1000000");
            /* 点播服务接入点 */
            //request.setApiRegionId("cn-shanghai");
            /* ECS部署区域*/
            // request.setEcsRegionId("cn-shanghai");

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            // System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            return response.getVideoId();

        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException(200001, e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteVideo(String videoId) {
        String accessKeyId = VodConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = VodConstantPropertiesUtils.ACCESS_KEY_SECRET;
        try {
            DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
            // 创建request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(videoId);

            // 删除视频
            client.getAcsResponse(request);
        } catch (Exception e) {
            throw new MyException(20001, e.getLocalizedMessage());
        }
    }

    /**
     * 初始化
     */
    public DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        return new DefaultAcsClient(profile);
    }
}

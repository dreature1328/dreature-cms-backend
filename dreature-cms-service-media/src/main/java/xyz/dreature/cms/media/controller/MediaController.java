package xyz.dreature.cms.media.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.dreature.cms.common.vo.ImageUploadResult;
import xyz.dreature.cms.media.service.MediaService;

@RestController
public class MediaController {
    @Autowired
    private MediaService mediaService;

    // 图片上传
    @RequestMapping("/upload")
    public ImageUploadResult imageUpload(MultipartFile image) {
        System.out.println(image);
        ImageUploadResult result = mediaService.imageUpload(image);

        return result;
    }
}

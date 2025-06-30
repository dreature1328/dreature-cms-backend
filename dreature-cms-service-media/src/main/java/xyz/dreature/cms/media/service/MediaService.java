package xyz.dreature.cms.media.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.dreature.cms.common.util.FtpUtils;
import xyz.dreature.cms.common.util.IDUtils;
import xyz.dreature.cms.common.vo.ImageUploadResult;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

@Service
public class MediaService {

    @Value("${FTP.ADDRESS}")
    private String host;
    // 端口
    @Value("${FTP.PORT}")
    private int port;
    // ftp用户名
    @Value("${FTP.USERNAME}")
    private String userName;
    // ftp用户密码
    @Value("${FTP.PASSWORD}")
    private String passWord;
    // 文件在服务器端保存的主目录
    @Value("${FTP.BASEPATH}")
    private String basePath;

    // 访问图片时的基础url(存数据库)
    @Value("${IMAGE.BASE.URL}")
    private String baseUrl;

    public ImageUploadResult imageUpload(MultipartFile image) {
        System.out.println(image);
        ImageUploadResult result = new ImageUploadResult();
        // 1)判断后缀合法
        //获取原始文件名
        String originName = image.getOriginalFilename();
        String extName = originName.substring(originName.lastIndexOf("."));
        boolean isok = extName.matches(".(jpg|png|gif|JPG|PNG|GIF)$");
        if (!isok) {
            result.setError(1);
            return result;
        }
        // 2)判断是不是木马
        try {
            BufferedImage bufImg = ImageIO.read(image.getInputStream());
            bufImg.getWidth();
            bufImg.getHeight();
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(1);
            return result;
        }
        //使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
        String newName = IDUtils.genImageName();
        newName = "image-" + newName + extName;
        //生成文件在服务器端存储的子目录
        //String filePath = new DateTime().toString("/yyyy/MM/dd");
        String filePath = "";
        try {
//			System.out.print(basePath + "/" + newName);
            InputStream input = image.getInputStream();

            // 3)调用FtpUtil工具类进行上传
            FtpUtils.uploadFile(host, port, userName, passWord, basePath, filePath, newName, input);

        } catch (Exception e) {
            e.printStackTrace();
            result.setError(1);
            return result;
        }

        result.setUrl(baseUrl + filePath + "/" + newName);

        return result;
    }

}

package com.changyu.web.admin;

import com.changyu.util.ImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ImageUploadController {

    @PostMapping("/saveContentImg")
    @ResponseBody
    public Map<String, Object> saveContentImg(@RequestParam(value="editormd-image-file", required = false) MultipartFile image,
                               HttpServletRequest request) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            saveImageFile(image, request);

            String url = "http://localhost:8080/img/upload/" + image.getOriginalFilename();
            resultMap.put("url", url);
            resultMap.put("message", "上传成功！");
            resultMap.put("success", 1);
        } catch (Exception e) {
            resultMap.put("success", 0);
            resultMap.put("message", "上传失败！");
            e.printStackTrace();
        }
        return resultMap;
    }

    private void saveImageFile(MultipartFile image, HttpServletRequest request) throws IOException {
        File imageFolder  = new File(request.getServletContext().getRealPath("img/upload/"));
        File file = new File(imageFolder, image.getOriginalFilename());
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        System.out.println(file.getParentFile());
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }
}

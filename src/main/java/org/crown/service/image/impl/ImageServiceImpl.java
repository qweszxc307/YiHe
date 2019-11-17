/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown.service.image.impl;

import com.qiniu.common.QiniuException;
import org.crown.common.utils.QiNiuUtils;
import org.crown.enums.ImagesEnum;
import org.crown.framework.responses.ApiResponses;
import org.crown.model.image.dto.ImageDTO;
import org.crown.model.image.entity.Image;
import org.crown.mapper.image.ImageMapper;
import org.crown.service.image.IImageService;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.crown.common.utils.ApiUtils.currentUid;
import static org.crown.framework.responses.ApiResponses.success;

/**
 * <p>
 * 存储所有图片信息 服务实现类
 * </p>
 *
 * @author whZhang
 */
@Service
public class ImageServiceImpl extends BaseServiceImpl<ImageMapper, Image>implements IImageService {
    @Autowired
    private QiNiuUtils qiniuService;
    @Autowired
    private IImageService imageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResponses<ImageDTO> uploadImg(HttpServletResponse response, MultipartFile file, ImagesEnum type) {
        ImageDTO imageDTO = new ImageDTO();
        try {
            String originalFilename = file.getOriginalFilename();
            String fileExtend = originalFilename.substring(originalFilename.lastIndexOf("."));
            //默认不指定key的情况下，以文件内容的hash值作为文件名
            String fileKey = UUID.randomUUID().toString().replace("-", "") + fileExtend;
            String imgUrl = qiniuService.upload(file.getInputStream(),fileKey);
            if(imgUrl.equals(null)){
                return success(response, HttpStatus.BAD_REQUEST,null);
            }else{
                Image baseImg = new Image();
                baseImg.setImgUrl(imgUrl);
                baseImg.setType(type);
                imageService.save(baseImg);
                imageDTO.setId(baseImg.getId());
                imageDTO.setImgUrl(imgUrl);
                return success(response,HttpStatus.OK,imageDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return success(response,HttpStatus.BAD_REQUEST,null);
        }
    }
}

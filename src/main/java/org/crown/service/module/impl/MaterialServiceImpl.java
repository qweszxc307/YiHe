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
package org.crown.service.module.impl;

import lombok.extern.log4j.Log4j2;
import org.crown.common.utils.ImageUtils;
import org.crown.common.utils.QiNiuUtils;
import org.crown.framework.responses.ApiResponses;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.crown.mapper.module.MaterialMapper;
import org.crown.model.module.dto.MaterialImgDTO;
import org.crown.model.module.entity.Material;
import org.crown.service.module.IMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

import static org.crown.framework.responses.ApiResponses.success;

/**
 * <p>
 * 素材表 服务实现类
 * </p>
 *
 * @author ykMa
 */
@Log4j2
@Service
public class MaterialServiceImpl extends BaseServiceImpl<MaterialMapper, Material> implements IMaterialService {
        @Autowired
        private QiNiuUtils qiniuService;

        @Override
        public ApiResponses<MaterialImgDTO> uploadImg(HttpServletResponse response, MultipartFile file) {
                try {
                        MaterialImgDTO materialImgDTO = new MaterialImgDTO();
                        BufferedImage image = ImageIO.read(file.getInputStream());
                        materialImgDTO.setHigh(image.getHeight());
                        materialImgDTO.setWidth(image.getWidth());
                        String originalFilename = file.getOriginalFilename();
                        String fileExtend = originalFilename.substring(originalFilename.lastIndexOf("."));
                        String fileKey = UUID.randomUUID().toString().replace("-", "") + fileExtend;
                        String imgUrl = qiniuService.upload(file.getInputStream(), fileKey);
                        materialImgDTO.setImage(imgUrl);
                        return success(response, HttpStatus.OK, materialImgDTO);
                } catch (IOException e) {
                        log.error("文件上传出现问题"+e.getMessage());
                        return success(response, HttpStatus.BAD_REQUEST, null);

                }
        }
}


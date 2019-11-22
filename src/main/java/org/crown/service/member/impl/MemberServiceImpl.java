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
package org.crown.service.member.impl;


import org.crown.common.utils.QiNiuUtils;
import org.crown.enums.ImagesEnum;
import org.crown.framework.responses.ApiResponses;
import org.crown.mapper.customer.CustomerMapper;
import org.crown.model.image.entity.Image;
import org.crown.model.member.dto.MemberImgDTO;
import org.crown.model.member.entity.Member;
import org.crown.mapper.member.MemberMapper;
import org.crown.model.member.entity.MemberDAO;
import org.crown.model.member.entity.MemberSum;
import org.crown.model.member.parm.MemberPARM;
import org.crown.service.image.IImageService;
import org.crown.service.member.IMemberService;
import org.crown.framework.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

import static org.crown.common.utils.ApiUtils.currentUid;
import static org.crown.framework.responses.ApiResponses.success;

/**
 * <p>
 * 会员等级表 服务实现类
 * </p>
 *
 * @author ykMa
 */
@Service
public class MemberServiceImpl extends BaseServiceImpl<MemberMapper, Member> implements IMemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private QiNiuUtils qiniuService;
    @Autowired
    IImageService imageService;

    /**
     * @param id         会员等级id
     * @param memberPARM 接受的修改的等级内容
     */
    @Transactional(readOnly = false)
    @Override
    public void updateMember(Integer id, MemberPARM memberPARM) {
        Member member = memberPARM.convert(Member.class);
        member.setId(id);
        updateCustomerLevel(member);
        memberMapper.updateById(member);
    }

    /**
     * 修改所有满足的客户修改其权限等级
     *
     * @param member
     */
    @Transactional(readOnly = false)
    @Override
    public void updateCustomerLevel(Member member) {

        MemberSum memberSum = memberMapper.queryNextUpgradeByUpgrade(member.getUpgrade());
        if (memberSum != null) {
            List<MemberDAO> maps = customerMapper.queryIdAndSumByUpgrade(member.getUpgrade(), memberSum.getUpgrade());
            if (maps.size() != 0) {
                for (MemberDAO map : maps) {
                    customerMapper.updateMIdById(map.getId(), member.getId());
                }
            }
        }else {
            List<MemberDAO> memberDAOS = customerMapper.queryIdAndSumByFront(member.getUpgrade());
            if (memberDAOS.size() != 0) {
                for (MemberDAO memberDAO : memberDAOS) {
                    customerMapper.updateMIdById(memberDAO.getId(), member.getId());
                }
            }
        }
    }

    /**
     * 根据id 删除会员等级
     *
     * @param id 会员等级id
     */
    @Transactional(readOnly = false)
    @Override
    public void deleteMember(Integer id) {
        Member member = memberMapper.selectById(id);
        MemberSum memberSumNext = memberMapper.queryNextUpgradeByUpgrade(member.getUpgrade());
        MemberSum memberSumFront = memberMapper.queryFrontUpgradeByUpgrade(member.getUpgrade());

        List<MemberDAO> memberDAOS = new ArrayList<>();
        if (Objects.isNull(memberSumNext)) {

            BigDecimal front = memberSumFront.getUpgrade();
            memberDAOS = customerMapper.queryIdAndSumByFront(front);

        } else if (Objects.isNull(memberSumFront)) {
            BigDecimal next = memberSumNext.getUpgrade();
            memberDAOS = customerMapper.queryIdAndSumByNext(next);
        } else {
            BigDecimal front = memberSumFront.getUpgrade();
            BigDecimal next = memberSumNext.getUpgrade();
            memberDAOS = customerMapper.queryIdAndSumByUpgrade(front, next);
        }


        if (memberDAOS.size() != 0) {
            for (MemberDAO memberDAO : memberDAOS) {
                customerMapper.updateMIdById(memberDAO.getId(), memberSumFront.getId());
            }
        }

        memberMapper.deleteById(member);
    }


    @Transactional(readOnly = false)
    @Override
    public Member queryOneByMemberName(String memberName) {
        return memberMapper.queryMemberByMemberName(memberName);
    }


    @Transactional(readOnly = false)
    @Override
    public ApiResponses<MemberImgDTO> uploadImg(HttpServletResponse response, MultipartFile file, ImagesEnum type) {
        MemberImgDTO imageDTO = new MemberImgDTO();
        try {
            String originalFilename = file.getOriginalFilename();
            String fileExtend = originalFilename.substring(originalFilename.lastIndexOf("."));
            //默认不指定key的情况下，以文件内容的hash值作为文件名
            String fileKey = UUID.randomUUID().toString().replace("-", "") + fileExtend;
            String imgUrl = qiniuService.upload(file.getInputStream(), fileKey);
            if (imgUrl.equals(null)) {
                return success(response, HttpStatus.BAD_REQUEST, null);
            } else {
                Image baseImg = new Image();
                baseImg.setImgUrl(imgUrl);
                baseImg.setCreateUid(currentUid());
                baseImg.setType(type);
                imageService.save(baseImg);
                imageDTO.setImgId(baseImg.getId());
                imageDTO.setImgUrl(imgUrl);
                return success(response, HttpStatus.OK, imageDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return success(response, HttpStatus.BAD_REQUEST, null);
        }
    }
}

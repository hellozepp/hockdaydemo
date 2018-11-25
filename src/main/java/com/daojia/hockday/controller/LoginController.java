package com.daojia.hockday.controller;

import com.alibaba.fastjson.JSON;
import com.daojia.hockday.entity.UserInfo;
import com.daojia.hockday.mapper.UserInfoMapper;
import com.daojia.hockday.util.EncryptUtil;
import com.daojia.hockday.util.MD5Util;
import com.daojia.hockday.util.ResultDto;
import com.daojia.hockday.util.UniqueIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;
import java.util.UUID;

/**
 * @author Dawei 2018/11/17
 */
@RestController
public class LoginController {


    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserInfoMapper userInfoMapper;

    @GetMapping(value = "/login")
    public String login(String token) {
        ResultDto<UserInfo> resultDto = new ResultDto<>();
        resultDto.setParamError();
        if (StringUtils.isEmpty(token)) {
            return JSON.toJSONString(resultDto);
        }
        String tokenNo = MD5Util.userToken(token);

        UserInfo userInfo = userInfoMapper.getUserByMd5Key(tokenNo);
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setId(UniqueIDUtil.getUniqueID());
            userInfo.setMd5key(tokenNo);
            userInfo.setUserName(UUID.randomUUID().toString());
            Random random = new Random(System.nanoTime());
            Integer countUser = 117;
            try {
                countUser = userInfoMapper.getCountUser();
            } catch (Exception e) {
                logger.error("failel ==== e", e);
            } finally {
                if(countUser == null || countUser < 0) {
                    countUser = random.nextInt();
                }
            }
            userInfo.setPhotoUrl(photeList[countUser%56]);
            userInfo.setToken(token);
            Integer integer = userInfoMapper.insertSelective(userInfo);
        }
        resultDto.setSuccess();
        resultDto.setData(userInfo);
        logger.info("reuslt ", JSON.toJSONString(resultDto));
        return JSON.toJSONString(resultDto);
    }

    String[] photeList = new String[]
            {"https://images.djtest.cn/house/test/3d84b531f80ff3f7e41cf66be5b28e71.jpg",
                    "https://images.djtest.cn/house/test/639109a854acbd59534396a717f4e6e6.jpg",
                    "https://images.djtest.cn/house/test/8df285f9623d4842be0eb8003b997bbb.jpg",
                    "https://images.djtest.cn/house/test/0a6d3d64c8a61b7b34ebbacc3eec91d9.jpg",
                    "https://images.djtest.cn/house/test/da0abb043684f76e66b8adf896fee2b9.jpg",
                    "https://images.djtest.cn/house/test/777eaf4e11b20a390de809b9a8bac26e.jpg",
                    "https://images.djtest.cn/house/test/f4b3db3e78b704c7da5d92b67f7d5a9b.jpg",
                    "https://images.djtest.cn/house/test/5527ba9389c284135d5d2a2f64d9b379.jpg",
                    "https://images.djtest.cn/house/test/ff7fa72ac97ddfa8987534d725624027.jpg",
                    "https://images.djtest.cn/house/test/8c4b4258d0371dad7c4c56b0025c565b.jpg",
                    "https://images.djtest.cn/house/test/cca49d543c18d37a41dcdf041d56ce65.jpg",
                    "https://images.djtest.cn/house/test/1739f09a3117552647c678b6ac1f402b.jpg",
                    "https://images.djtest.cn/house/test/cbe5c9cc734dbf2974d7e4764a4a1d85.jpg",
                    "https://images.djtest.cn/house/test/40da8373e92b199a6edf6afbc0e299d0.jpg",
                    "https://images.djtest.cn/house/test/bc4d0c1360a0c68c38fc2a5ae7472143.jpg",
                    "https://images.djtest.cn/house/test/26e9f21f4247b4f6bc1e570c7696cea0.jpg",
                    "https://images.djtest.cn/house/test/97fbc98afbb5057d3ad9783648b851b8.jpg",
                    "https://images.djtest.cn/house/test/1403cefef6929912c0a83c4abdd0ab9a.jpg",
                    "https://images.djtest.cn/house/test/9e7ee05096fe0e8063177b9763d5742e.jpg",
                    "https://images.djtest.cn/house/test/db1671bd98782db7936c481bf1370b9f.jpg",
                    "https://images.djtest.cn/house/test/be418cf13fbdcd4ddc901460a4f418ce.jpg",
                    "https://images.djtest.cn/house/test/995ffe00c26c30dbbdd0e43cb75cea74.jpg",
                    "https://images.djtest.cn/house/test/ce51006aa5476ec231ad8884c48a9def.jpg",
                    "https://images.djtest.cn/house/test/bda62443f53ab41126fd71958194e278.jpg",
                    "https://images.djtest.cn/house/test/c8e5e964536909e18430d46a609e8264.jpg",
                    "https://images.djtest.cn/house/test/7b34d78984239d41c3b4673d83a733a8.jpg",
                    "https://images.djtest.cn/house/test/59e8cc9ef48880cc062ad738c50f9935.jpg",
                    "https://images.djtest.cn/house/test/763d05fc058ac3872cd6bf2b6d4dabef.jpg",
                    "https://images.djtest.cn/house/test/991aed3c7cd791a8ea2642f02d17852a.jpg",
                    "https://images.djtest.cn/house/test/a1dea15dd21ed90afa4f99ee91b9af04.jpg",
                    "https://images.djtest.cn/house/test/a5fe5605abbedc8ad4eb424adb3f0ff1.jpg",
                    "https://images.djtest.cn/house/test/35a932707f8bd1e10ac886528346de49.jpg",
                    "https://images.djtest.cn/house/test/442ce13bd0b4b2fb0b0c67bbb6ebc5b5.jpg",
                    "https://images.djtest.cn/house/test/cd6f0d98a7b79a3c50d4a684434cfbf0.jpg",
                    "https://images.djtest.cn/house/test/0408d887794a052940d52ff87cb3f6af.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/d44182fc7505a4ac7ff608cb23fc5539.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/1a8ab9599c8cebf121d86bc999f95f28.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/f89629fe097156a61d02450cff1f1f7d.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/998eac74f4f488c12ffab91adfbf6eab.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/512250d34c18c6d2c93dd31b4d250720.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/d5295d58e220e1ce5f275b10d3c9891f.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/febc9913706f7d47855fbfc4481ec870.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/906cd8c23446f8b31b1192604749bc80.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/64c0de224df4662163e36fb740b2df0b.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/5a150e468b52b2229e73e7e604670073.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/da608cff1d580a205d4a6c37289426f8.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/d387b43dd6431fd966dcd519757dee21.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/459e827598b60930625651e29518da60.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/5d94910ac8c3b7e1060e707d61867d86.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/5cc6e7d1924a35b7f77922e01d4fd711.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/d58ab9585983acd4b7a33accf54c89c1.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/dc50ecf9093409001bd805c14c166b1f.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/ff208a97eb086dc5b102392f6554cfad.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/2ee550d6a4fe428af5ca75b71c4c6682.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/389accd041ad3ca46b4cb40584a78160.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/2d5bd8867890a293aae56f0900cf7926.jpg",
                    "https://images.djtest.cn/house/test/hockday-dawei/fbdd218ab7e0cd8d1524739365f3262f.jpg"
            };


}

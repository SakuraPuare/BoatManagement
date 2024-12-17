package com.sakurapuare.boatmanagement.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sakurapuare.boatmanagement.constant.TableName;
import com.sakurapuare.boatmanagement.mapper.CodesMapper;
import com.sakurapuare.boatmanagement.pojo.entity.Codes;
import com.sakurapuare.boatmanagement.pojo.entity.Users;
import com.sakurapuare.boatmanagement.service.CodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 验证码表 服务层实现。
 *
 * @author sakurapuare
 * @since 2024-12-17
 */
@Service
public class CodesServiceImpl extends ServiceImpl<CodesMapper, Codes> implements CodesService {

    @Autowired
    CodesMapper codesMapper;

    @Override
    public Codes generateCode(Users user) {
        Codes code = Codes.builder().userId(user.getUserId()).code(RandomUtil.randomNumbers(6)).expirationTime(Timestamp.valueOf(LocalDateTime.now().plusDays(1))).build();

        codesMapper.insertSelective(code);

        return code;
    }

    @Override
    public boolean verifyCode(Users user, String password) {
        QueryWrapper qw = QueryWrapper.create();
        qw.eq(TableName.USER_ID, user.getUserId()).orderBy(TableName.CREATED_AT, false);
        List<Codes> codes = codesMapper.selectListByQuery(qw);

        if (codes.isEmpty()) {
            return false;
        }

        for (Codes code : codes) {
            // parse from timestamp to LocalDateTime
            Timestamp timestamp = code.getExpirationTime();
            LocalDateTime expirationTime = timestamp.toLocalDateTime();
            if (code.getCode().equals(password)) {
                codesMapper.deleteById(code.getId());
                return true;
            }
            // if expired, delete the code
            if (expirationTime.isBefore(LocalDateTime.now())) {
                codesMapper.deleteById(code.getId());
            }
        }

        return false;
    }

}

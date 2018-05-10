package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    int checkEmail(String email);

    int checkEmailByUserId(@Param("email") String email,@Param("userId")Integer userId);

    String getQuestionByUsername(String username);

    int forgetCheckAnswer(@Param("username") String username, @Param("question") String question,@Param("answer") String answer );

    int updatePasswordByUsername(@Param("username")String username,@Param("password")String password);

    User login(@Param("username") String username, @Param("password") String password);

    int checkPasswordByUserId(@Param("password") String password,@Param("id") Integer id);


}
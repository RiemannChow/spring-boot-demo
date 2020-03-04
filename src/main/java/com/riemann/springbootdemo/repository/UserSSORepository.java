package com.riemann.springbootdemo.repository;

import com.riemann.springbootdemo.model.UserSSO;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author riemann
 * @date 2019/06/29 17:57
 */
public interface UserSSORepository extends PagingAndSortingRepository<UserSSO, Long>, JpaSpecificationExecutor<UserSSO> {
    /**
     * 重点知识：SpringData 查询方法定义规范
     *
     * 1. 查询方法名一般以 find | read | get 开头，建议用find
     * 	findByAccount : 通过account查询User
     * 	account是User的属性，拼接时首字母需大写
     * 2. 支持的关键词有很多比如 Or,Between,isNull,Like,In等
     * 	findByEmailEndingWithAndCreatedDateLessThan : 查询在指定时间前注册，并以xx邮箱结尾的用户
     * 	And : 并且
     * 	EndingWith : 以某某结尾
     * 	LessThan : 小于
     *
     * 注意
     * 若有SSOUser(用户表) Platform(用户平台表) 存在一对一的关系，且User表中有platformId字段
     * SpringData 为了区分：
     * findByPlatFormId 	表示通过platformId字段查询
     * findByPlatForm_Id 	表示通过platform实体类中id字段查询
     *
     * 开发建议
     * 表的设计，尽量做单表查询，以确保高并发场景减轻数据库的压力。
     */

    // 通过账号查用户信息
    UserSSO findByAccount(String account);
    // 获取指定时间内以xx邮箱结尾的用户信息
    List<UserSSO> findByEmailEndingWithAndCreatedDateLessThan(String email, String createdDate);

    /**
     * 重点知识：使用 @Query 注解
     *
     * 上面的方法虽然简单(不用写sql语句)，但它有最为致命的问题-----不支持复杂查询，其次是命名太长
     * 1. 使用@Query 注解实现复杂查询，设置 nativeQuery=true 使查询支持原生sql
     * 2. 配合@Modifying 注解实现创建，修改，删除操作
     * 3. SpringData 默认查询事件为只读事务，若要修改数据则需手动添加事务注解
     *
     * 注意
     * 若@Query 中有多个参数，SpringData 提供两种方法：
     * 第一种 ?1 ... ?2 		要求参数顺序一致
     * 第二种 :xxx ... :yyy 	xxx 和 yyy 必须是实体类对应的属性值，不要求参数顺序但参数前要加上@Param("xxx")
     * 模糊查询可使用 %xxx%
     *
     * 开发建议
     * 1. 参数填写的顺序要保持一致，不要给自己添加麻烦
     * 2. 建议使用@Query，可读性较高
     */
    // 获取某平台活跃用户数量
    @Query(value="SELECT count(u.id) FROM UserSSO u WHERE u.platform = :platform AND u.updatedDate <= :updatedDate")
    long getActiveUserCount(@Param("platform")String platform, @Param("updatedDate")String updatedDate);

    // 通过邮箱或者手机号模糊查询用户信息
    @Query(value="SELECT u FROM UserSSO u WHERE u.email LIKE %?1% OR u.iphone LIKE %?2%")
    List<UserSSO> findByEmailAndIhpneLike(String email, String iphone);

    // 修改用户邮箱
    @Modifying
    @Query("UPDATE UserSSO u SET u.email = :email WHERE u.id = :id")
    void updateUserEmail(@Param("id") Long id, @Param("email") String email);

}

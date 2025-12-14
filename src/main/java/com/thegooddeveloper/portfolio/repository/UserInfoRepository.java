package com.thegooddeveloper.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thegooddeveloper.portfolio.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>{

}

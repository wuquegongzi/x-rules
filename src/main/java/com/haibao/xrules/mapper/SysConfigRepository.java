package com.haibao.xrules.mapper;

import com.haibao.xrules.model.SysConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 系统配置
 *
 * @author ml.c
 * @date 8:56 PM 5/16/21
 **/
public interface SysConfigRepository extends JpaRepository<SysConfig, Long> {
}

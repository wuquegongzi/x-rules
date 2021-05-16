package com.haibao.xrules.mapper;

import com.haibao.xrules.model.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepository extends JpaRepository<BlackList,Long> {

    boolean existsByDimensionAndTypeAndValue(String dimension, String type, String value);

    BlackList findByDimensionAndTypeAndValue(String dimension, String type, String value);
}

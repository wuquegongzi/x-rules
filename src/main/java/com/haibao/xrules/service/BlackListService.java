package com.haibao.xrules.service;

import com.haibao.xrules.model.BlackList;
import java.util.List;

/**
 *
 *
 * @author ml.c
 * @date 9:53 PM 5/15/21
 **/
public interface BlackListService {

    List<BlackList> queryAll();
    void add(BlackList blackList);
    void pub(BlackList blackList);
    BlackList get(String key);

}

package com.haibao.xrules.dao.impl;

import java.util.Date;
import org.bson.BsonReader;
import org.bson.Document;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;

/**
 * 特殊处理codec类
 *
 * @author ml.c
 * @date 8:28 PM 5/23/21
 **/
public class DocumentDecoder extends DocumentCodec {

    @Override
    public Document decode(BsonReader reader, DecoderContext decoderContext) {
        Document document = super.decode(reader, decoderContext);

        //特殊处理operateTime，时间格式
        String operateTime = "operateTime";
        long time = document.getLong(operateTime);
        document.put(operateTime, new Date(time));

        return document;
    }
}

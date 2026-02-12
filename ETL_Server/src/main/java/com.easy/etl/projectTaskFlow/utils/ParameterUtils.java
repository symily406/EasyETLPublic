package com.easy.etl.projectTaskFlow.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easy.etl.common.expression.kit.Expression;
import com.easy.etl.projectTaskFlow.entity.FlowJobParameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterUtils {

    public static Map<String, String> parse( Map<String,String> params) {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String value = Expression.keyExpression(entry.getValue());
            map.put("${" + entry.getKey() + "}", value);
        }
        return map;
    }
}

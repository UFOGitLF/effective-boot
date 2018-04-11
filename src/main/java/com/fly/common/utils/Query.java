package com.fly.common.utils;

import com.fly.common.xss.SqlFilter;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 * <p>
 * @author liufei
 */
@Data
public class Query extends LinkedHashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private int page;
    /**
     * 每页条数
     */
    private int limit;

    public Query(Map<String, Object> params) {
        this.putAll(params);
        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.put("offset", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);

        //防止SQL注入
        String sidx = (String) params.get("sidx");
        String order = (String) params.get("order");

        if (StringUtils.isNotBlank(sidx)) {
            this.put("sidx", SqlFilter.sqlInject(sidx));
        }
        if (StringUtils.isNotBlank(order)) {
            this.put("order", SqlFilter.sqlInject(order));
        }

    }

}

package com.example.mybatis.param;

import lombok.Data;

//分页的基础类
@Data
public class PageParam {
    private int beginLine; //起始行
    private Integer pageSize = 3; //一页多少条
    private Integer currentPage = 1; //当前页

    public int getBeginLine() {
        return (currentPage-1)*pageSize;  //自动计算起始行
    }

}

package com.example.mybatis.result;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
//Page<E> java类泛型
public class Page<E> implements Serializable {

    private int currentPage = 0; //当前页数
    private long pageSize;       //一页多少条
    private long totalPage;      //总页数
    private long totalNumber;    //总记录数
    private List<E> list;        //数据集

    public Page(int currentPage,long pageSize,long totalNumber,List<E> list){
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalNumber = totalNumber;
        this.list = list;
        this.totalPage = totalNumber % pageSize == 0 ? totalNumber
                / pageSize : totalNumber / pageSize + 1;
    }

}

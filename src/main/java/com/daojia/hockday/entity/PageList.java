package com.daojia.hockday.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lei shuiyu
 * @date 2018/11/25 0:08
 */
public class PageList<T> {

    private List<T> list;
    private int page;
    private int prev;
    private int next;
    private long amount;
    private int pageSize;
    private int totalPage;
    private List<Integer> slidePages;

    public PageList() {
    }


    public PageList(int page, List<T> list, long amount, int pageSize) {
        this.list = list;
        this.page = page;
        this.amount = amount;
        this.pageSize = pageSize;
        this.totalPage = (int) Math.ceil((double) amount / ((double) pageSize * 1.0D));
        if (this.hasNext()) {
            this.next = this.page + 1;
        }

        if (this.hasPrev()) {
            this.prev = this.page - 1;
        }

        this.slidePages = new ArrayList();
        int slideStart = 1;
        if (this.page > 5) {
            slideStart = this.page - 5;
        }

        int slideEnd = slideStart + 10;
        if (slideEnd > this.totalPage) {
            slideEnd = this.totalPage;
        }

        for (int i = slideStart; i <= slideEnd; ++i) {
            this.slidePages.add(Integer.valueOf(i));
        }

    }


    public <I> PageList<I> replaceList(List<I> list) {
        return new PageList(this.page, list, this.amount, this.pageSize);
    }


    public boolean hasNext() {
        return this.page < this.totalPage;
    }


    public boolean hasPrev() {
        return this.page > 1;
    }


    public int getPage() {
        return this.page;
    }


    public void setPage(int page) {
        this.page = page;
    }


    public int getPrev() {
        return this.prev;
    }


    public void setPrev(int prev) {
        this.prev = prev;
    }


    public int getNext() {
        return this.next;
    }


    public void setNext(int next) {
        this.next = next;
    }


    public long getAmount() {
        return this.amount;
    }


    public void setAmount(long amount) {
        this.amount = amount;
    }


    public int getPageSize() {
        return this.pageSize;
    }


    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public int getTotalPage() {
        return this.totalPage;
    }


    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }


    public List<Integer> getSlidePages() {
        return this.slidePages;
    }


    public void setSlidePages(List<Integer> slidePages) {
        this.slidePages = slidePages;
    }


    public List<T> getList() {
        return this.list;
    }


    public void setList(List<T> list) {
        this.list = list;
    }


}

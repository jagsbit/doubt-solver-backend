package com.project.doubt_solver.payloads;

import java.util.List;

public class DoubtResponse {
    private List<DoubtDto> content;
    private int pageNumber;
    private int pageSize;

    public List<DoubtDto> getContent() {
        return content;
    }

    public void setContent(List<DoubtDto> content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    private long totalElements;
    private int totalPages;
    private boolean lastPage;
}

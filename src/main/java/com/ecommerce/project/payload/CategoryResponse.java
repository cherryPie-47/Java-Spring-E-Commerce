package com.ecommerce.project.payload;

import java.util.List;

public class CategoryResponse {
    private List<CategoryDTO> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;


    public CategoryResponse(){
    }

    public CategoryResponse(List<CategoryDTO> content, Integer pageNumber, Integer pageSize, Long totalElements, Integer totalPages, boolean lastPage){
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.lastPage = lastPage;
    }

    public List<CategoryDTO> getContent() {
        return content;
    }

    public void setContent(List<CategoryDTO> content) {
        this.content = content;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    public void setTotalElement(Long totalElements) {
        this.totalElements = totalElements;
    }
    public void setTotalPages (Integer totalPages) {
        this.totalPages = totalPages;
    }
    public void setLastPage (boolean lastPage) {
        this.lastPage = lastPage;
    }
    public Integer getPageNumber() {
        return pageNumber;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public Long getTotalElement() {
        return totalElements;
    }
    public Integer getTotalPages() {
        return totalPages;
    }
    public boolean isLastPage() {
        return lastPage;
    }

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "content=" + content +
                '}';
    }
}

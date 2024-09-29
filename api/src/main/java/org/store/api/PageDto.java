package org.store.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Page of any items")
public class PageDto<T> {

    @Schema(description = "List of items (objects) on the page", example = "[{1, Bread, 1.99, Food}, {2, Milk, 1.05, Food}]")
    private List<T> content;

    @Schema(description = "Current page number", example = "2")
    private int pageNumber;

    @Schema(description = "Total pages in request", example = "15")
    private int totalPages;

    @Schema(description = "Number of items (objects) on the page", example = "10")
    private int size;

    @Schema(description = "Total number of items (objects) in the request", example = "145")
    private long totalElements;

    public PageDto() {
    }

    public PageDto(List<T> content, int pageNumber, int totalPages, int size, long totalElements) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
        this.size = size;
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}

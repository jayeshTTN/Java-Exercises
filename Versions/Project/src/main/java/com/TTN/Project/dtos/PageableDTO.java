package com.TTN.Project.dtos;

public class PageableDTO {
    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    private Long offset;

    private Long size;


}

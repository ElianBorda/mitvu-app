package com.unq.mitvu.controller.body;

import lombok.Data;

import java.util.List;

@Data
public class IDsBodyDTO {
    private List<String> ids;

    public IDsBodyDTO(List<String> ids) {
        this.ids = ids;
    }
}
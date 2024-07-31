package com.studymavernspringboot.mustachajax.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCategoryDto {
    private String orderByWord;
    private String searchName;
    private Integer rowsOnePage;
    private Integer firstIndex;
    public Integer getFirstIndex() {
        return (this.page - 1) * this.rowsOnePage;
    }

    private Integer page;
    private Integer total;
}
package com.studymavernspringboot.mustachajax.board;

import com.studymavernspringboot.mustachajax.category.ICategory;

public interface IBoard {
    Long getId();
    void setId(Long id);

    String getName();
    void setName(String name);

    default void copyFields(ICategory from) {
        if ( from == null ) {
            return;
        }
        if ( from.getId() != null ) {
            this.setId(from.getId());
        }
        if ( from.getName() != null && !from.getName().isEmpty() ) {
            this.setName(from.getName());
        }
    }
}

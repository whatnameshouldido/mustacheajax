package com.studymavernspringboot.mustachajax.board;

import com.studymavernspringboot.mustachajax.commons.dto.IBase;

public interface IBoard extends IBase {
    Long getId();
    void setId(Long id);

    String getName();
    void setName(String name);

    String getContent();
    void setContent(String content);

    Integer getViewQty();
    void setViewQty(Integer viewQty);

    Integer getLikeQty();
    void setLikeQty(Integer likeQty);

    String getDelFlag();
    void setDelFlag(String delFlag);

    default void copyFields(IBoard from) {
        if ( from == null ) {
            return;
        }
        if ( from.getId() != null ) {
            this.setId(from.getId());
        }
        if ( from.getName() != null && !from.getName().isEmpty() ) {
            this.setName(from.getName());
        }
        if ( from.getContent() != null && !from.getContent().isEmpty() ) {
            this.setContent(from.getContent());
        }
        if ( from.getViewQty() != null) {
            this.setViewQty(from.getViewQty());
        }
        if ( from.getLikeQty() != null) {
            this.setLikeQty(from.getLikeQty());
        }
        if ( from.getDelFlag() != null && !from.getDelFlag().isEmpty() ) {
            this.setDelFlag(from.getDelFlag());
        }
        IBase.super.copyFields(from);
    }
}

package me.takara.shared.rest;

import lombok.Data;

import java.util.List;

@Data
public class WhereClause {
    private String where;
    private String whereOp;
    private List<String> whereContent;
}

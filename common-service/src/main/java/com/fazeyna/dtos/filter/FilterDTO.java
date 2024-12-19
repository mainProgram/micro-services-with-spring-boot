package com.fazeyna.dtos.filter;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterDTO {

    private String columnName;

    private Object columnValue;

}

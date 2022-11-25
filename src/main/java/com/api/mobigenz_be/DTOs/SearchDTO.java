package com.api.mobigenz_be.DTOs;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private String valueSearch;
    List<ListSortDTO> listSortDTO;
}

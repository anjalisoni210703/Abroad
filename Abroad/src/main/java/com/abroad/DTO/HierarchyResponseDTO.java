package com.abroad.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HierarchyResponseDTO {
    private long totalCount;
    private List<AbroadContinentDTO> results;
}

package com.vironit.kazimirov.dto;

import com.vironit.kazimirov.entity.Purpose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurposeDto {
    private int id;
    private String purpose;

    public PurposeDto(Purpose purpose1){
        this.id=purpose1.getId();
        this.purpose=purpose1.getPurpose();
    }
}

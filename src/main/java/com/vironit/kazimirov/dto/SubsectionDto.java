package com.vironit.kazimirov.dto;

import com.vironit.kazimirov.entity.Subsection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubsectionDto implements Serializable {

    private int id;
    private String title;

    public SubsectionDto(Subsection subsection){
        this.id=subsection.getId();
        this.title=subsection.getTitle();
    }
}

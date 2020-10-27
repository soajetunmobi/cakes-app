package com.cakes.favorite.domain;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Cake {
    private int id;
    @NotBlank
    private String name;
    
    @NotBlank
    private String imageUrl;
}

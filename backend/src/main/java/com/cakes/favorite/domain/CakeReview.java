package com.cakes.favorite.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CakeReview extends Cake {
    @NotBlank
    private String comment;
    
    @Min(1)
    @Max(5)
    private int yumFactor;

    @Builder(builderMethodName = "cakeReviewBuilder")
    public CakeReview(int id, String name, String imageUrl, String comment, int yumFactor) {
        super(id, name, imageUrl);
        this.comment = comment;
        this.yumFactor =yumFactor;
    }
}

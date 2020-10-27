package com.cakes.favorite.service;

import java.util.List;

import com.cakes.favorite.domain.Cake;
import com.cakes.favorite.domain.CakeReview;
import com.cakes.favorite.repository.CakeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CakeService {
    private final CakeMapper cakeMapper;

    public CakeService(CakeMapper cakeMapper) {
        this.cakeMapper = cakeMapper;
    }

    public List<Cake> getAllCakes() {
        return cakeMapper.getAllCakes();
    }

    public CakeReview getCakeReviewById(int cakeId) {
        return cakeMapper.getCakeReviewById(cakeId);
    }

    @Transactional
    public void saveCakeReview(CakeReview cakeReview) {
        Cake cake = new Cake();
        cake.setName(cakeReview.getName());
        cake.setImageUrl(cakeReview.getImageUrl());
        
        int rowCount = cakeMapper.saveCake(cake);
        if(rowCount == 1){
            cakeReview.setId(cake.getId());
            cakeMapper.saveReview(cakeReview);
        }
    }

    @Transactional
    public void updateCakeReview(CakeReview cakeReview) {
        Cake cake = new Cake();
        if (null != cakeReview.getName() && !cakeReview.getName().isBlank()) {
            cake.setName(cakeReview.getName());
        }
        if (null != cakeReview.getImageUrl() && !cakeReview.getImageUrl().isBlank()) {
            cake.setName(cakeReview.getImageUrl());
        }

        int rowCount = cakeMapper.updateCake(cake);
        if (rowCount == 1) {
            cakeMapper.updateReview(cakeReview);
        }
    }

    @Transactional
    public void deleteCakeById(int cakeId) {
        cakeMapper.deleteCakeById(cakeId);
    }
}

package com.cakes.favorite.web;

import java.util.List;

import com.cakes.favorite.domain.Cake;
import com.cakes.favorite.domain.CakeReview;
import com.cakes.favorite.exception.MissingIdException;
import com.cakes.favorite.service.CakeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@CrossOrigin
@RequestMapping("/api")
public class CakeController {

    private final CakeService cakeService;

    public CakeController(CakeService cakeService) {
        this.cakeService = cakeService;
    }

    @GetMapping("/cakes")
    public List<Cake> getAllCakes() {
        return cakeService.getAllCakes();
    }

    @GetMapping("/cakes/{id}/review")
    public ResponseEntity<CakeReview> getCakeReviewById(@PathVariable("id") String id) throws MissingIdException {
        if(id == null || id.isBlank()) {
            throw new MissingIdException("Id is missing");
        }
        return ResponseEntity.ok().body(cakeService.getCakeReviewById(Integer.parseInt(id)));
    }

    @PostMapping("/cakes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveCakeReview(@RequestBody CakeReview cakeReview) {
        cakeService.saveCakeReview(cakeReview);
    }

    @PutMapping("/cakes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCakeReview(@RequestBody CakeReview cakeReview) {
        cakeService.updateCakeReview(cakeReview);
    }

    @DeleteMapping("/cakes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCake(@PathVariable("id") String id) throws MissingIdException {
        if(id == null || id.isBlank()) {
            throw new MissingIdException("Id is missing");
        }
        cakeService.deleteCakeById(Integer.parseInt(id));
    }

}

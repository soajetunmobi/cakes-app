package com.cakes.favorite.service;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.cakes.favorite.UnitTest;
import com.cakes.favorite.domain.Cake;
import com.cakes.favorite.domain.CakeReview;
import com.cakes.favorite.repository.CakeMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Category(UnitTest.class)
public class CakeServiceTest {

    private CakeService cakeService;

    @MockBean
    private CakeMapper cakeMapperMock;

    @Before
    public void setUp() {
        cakeService = new CakeService(cakeMapperMock);
    }

    @Test
    public void testGetAllCakes() {
        //Given
        Cake cake = Cake.builder().id(1).name("Cake 1").imageUrl("url1").build();
        Cake cake2 = Cake.builder().id(2).name("Cake 2").imageUrl("url2").build();
        Cake cake3 = Cake.builder().id(3).name("Cake 3").imageUrl("url3").build();
        List<Cake> expectedCakes = List.of(cake, cake2, cake3);

        when(cakeMapperMock.getAllCakes()).thenReturn(expectedCakes);

        //When
        final List<Cake> actualCakes = cakeService.getAllCakes();

        //Then
        assertThat(expectedCakes.size()).isEqualTo(actualCakes.size());
        assertThat(expectedCakes).hasSameElementsAs(actualCakes);
    }

    @Test
    public void testGetCakeReviewById() {
        //Given
       final int cakeId = 1;

        CakeReview expectedCakeReview = CakeReview.cakeReviewBuilder().id(cakeId).name("Cake 1").imageUrl("url")
                .comment("comment").yumFactor(4).build();


        when(cakeMapperMock.getCakeReviewById(cakeId)).thenReturn(expectedCakeReview);

        //When
        final CakeReview actualCakeReview = cakeService.getCakeReviewById(cakeId);

        //Then
        verify(cakeMapperMock).getCakeReviewById(cakeId);
        assertThat(expectedCakeReview.getId()).isEqualTo(actualCakeReview.getId());
        assertThat(expectedCakeReview.getName()).isEqualTo(actualCakeReview.getName());
        assertThat(expectedCakeReview.getImageUrl()).isEqualTo(actualCakeReview.getImageUrl());
        assertThat(expectedCakeReview.getComment()).isEqualTo(actualCakeReview.getComment());
        assertThat(expectedCakeReview.getYumFactor()).isEqualTo(actualCakeReview.getYumFactor());
    }

    @Test
    public void testShouldReturnNullWithNonExistingIdWhenGetCakeReviewById() {
        //Given
        final int cakeId = 1;

        when(cakeMapperMock.getCakeReviewById(cakeId)).thenReturn(null);

        //When
        final CakeReview actualCakeReview = cakeService.getCakeReviewById(cakeId);

        //Then
        verify(cakeMapperMock).getCakeReviewById(cakeId);
        assertThat(actualCakeReview).isNull();
    }

    @Test
    public void testShouldSaveCakeReview() {
        //Given
        final int cakeId = 1;

        Cake expectedCake = Cake.builder().name("name").imageUrl("url").build();

        CakeReview expectedCakeReview = CakeReview.cakeReviewBuilder().id(cakeId).name(expectedCake.getName())
                .imageUrl(expectedCake.getImageUrl()).comment("comment").yumFactor(4).build();


        when(cakeMapperMock.saveCake(any(Cake.class))).thenReturn(1);
        when(cakeMapperMock.saveReview(any(CakeReview.class))).thenReturn(1);

        //When
        cakeService.saveCakeReview(expectedCakeReview);

        //Then
        verify(cakeMapperMock).saveCake(any(Cake.class));
        expectedCake.setId(cakeId);
        verify(cakeMapperMock).saveReview(any(CakeReview.class));
    }

    @Test
    public void testShouldUpdateCakeReview() {
        //Given
        Cake expectedCake = Cake.builder().id(2).name("name").imageUrl("url").build();

        CakeReview expectedCakeReview = CakeReview.cakeReviewBuilder().id(expectedCake.getId())
                .name(expectedCake.getName()).imageUrl(expectedCake.getImageUrl())
                .comment("comment").yumFactor(4).build();


        when(cakeMapperMock.updateCake(any(Cake.class))).thenReturn(1);
        when(cakeMapperMock.updateReview(any(CakeReview.class))).thenReturn(1);

        //When
        cakeService.updateCakeReview(expectedCakeReview);

        //Then
        verify(cakeMapperMock).updateCake(any(Cake.class));
        verify(cakeMapperMock).updateReview(any(CakeReview.class));
    }

    @Test
    public void testShouldDeleteCakeById() {
        //Given
        final int cakeId = 1;

        when(cakeMapperMock.deleteCakeById(cakeId)).thenReturn(1);

        //When
        cakeService.deleteCakeById(cakeId);

        //Then
        verify(cakeMapperMock).deleteCakeById(cakeId);
    }

}
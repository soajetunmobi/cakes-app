package com.cakes.favorite.web;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.cakes.favorite.IntegrationTest;
import com.cakes.favorite.domain.Cake;
import com.cakes.favorite.domain.CakeReview;
import com.cakes.favorite.service.CakeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Category(IntegrationTest.class)
public class CakeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    CakeService cakeService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetAllCakes() throws Exception {
        //When
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/cakes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andReturn().getResponse().getContentAsString();

        //Then
        final List<Cake> cakes = objectMapper.readValue(response, new TypeReference<List<Cake>>() {});

        assertThat(cakes.size()).isEqualTo(4);
    }

    @Test
    public void testGetCakeReviewById() throws Exception {
        //When
        final int cakeId = 2;
        String url = String.format("/api/cakes/%d/review", cakeId);
        String response = mockMvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andReturn().getResponse().getContentAsString();

        //Then
        final CakeReview actualCakeReview = objectMapper.readValue(response, CakeReview.class);

        assertThat(actualCakeReview).isNotNull();
        assertThat(actualCakeReview.getId()).isGreaterThan(0);
        assertThat(actualCakeReview.getName()).isNotEmpty();
        assertThat(actualCakeReview.getImageUrl()).isNotEmpty();
        assertThat(actualCakeReview.getComment()).isNotEmpty();
        assertThat(actualCakeReview.getYumFactor()).isBetween(1, 5);
    }

    @Test
    public void testShouldReturnNothingForNoneExistingIdWhenGetCakeReviewById() throws Exception {
        //When
        final int cakeId = 7;
        String url = String.format("/api/cakes/%d/review", cakeId);
        String response = mockMvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andReturn().getResponse().getContentAsString();

        //Then
        assertThat(response).isEmpty();
    }

    @Test
    public void testShouldThrowMissingIdWhenGetCakeReviewById() throws Exception {
        //When
        String url = "/api/cakes/review";
        mockMvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void testShouldSaveCakeReview() throws Exception {
        //When
        final CakeReview cakeReview = CakeReview.cakeReviewBuilder().comment("comment").imageUrl("url").name("name").yumFactor(4).build();
        String payload = objectMapper.writeValueAsString(cakeReview);
        String url = "/api/cakes";
        mockMvc.perform(MockMvcRequestBuilders.post(url).content(payload).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testShouldNotSaveCakeReviewWhenMissingRequiredFields() throws Exception {
        //When
        final CakeReview cakeReview = CakeReview.cakeReviewBuilder().comment(null).imageUrl(null).name("name").yumFactor(4).build();
        String payload = objectMapper.writeValueAsString(cakeReview);
        String url = "/api/cakes";
        mockMvc.perform(MockMvcRequestBuilders.post(url).content(payload).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testShouldUpdateCakeReview() throws Exception {
        //When
        final CakeReview cakeReview = CakeReview.cakeReviewBuilder().id(1).comment("comment").imageUrl("url").name("name").yumFactor(2).build();
        String payload = objectMapper.writeValueAsString(cakeReview);
        String url = "/api/cakes";
        mockMvc.perform(MockMvcRequestBuilders.put(url).content(payload).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testShouldNotUpdateCakeReviewWhenMissingRequiredFields() throws Exception {
        //When
        final CakeReview cakeReview = CakeReview.cakeReviewBuilder().id(1).comment(null).imageUrl(null).name("name").yumFactor(4).build();
        String payload = objectMapper.writeValueAsString(cakeReview);
        String url = "/api/cakes";
        mockMvc.perform(MockMvcRequestBuilders.post(url).content(payload).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }



    @Test
    public void testShouldDeleteCakeById() throws Exception {
        //When
        final CakeReview cakeReview = CakeReview.cakeReviewBuilder().id(1).comment("comment").imageUrl("url").name("name").yumFactor(2).build();
        String payload = objectMapper.writeValueAsString(cakeReview);
        String url = String.format("/api/cakes/%d", 1);
        mockMvc.perform(MockMvcRequestBuilders.delete(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
}
package com.cakes.favorite.repository;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import com.cakes.favorite.IntegrationTest;
import com.cakes.favorite.domain.Cake;
import com.cakes.favorite.domain.CakeReview;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureWebClient
@Category(IntegrationTest.class)
public class CakeMapperIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CakeMapper cakeMapper;

    @Test
    @Transactional
    public void testGetAllCakes() {
        //Given
        List<Cake> expectedCakes = jdbcTemplate.query("SELECT * FROM cake", new BeanPropertyRowMapper(Cake.class));

        //When
        final List<Cake> actualCakes = cakeMapper.getAllCakes();

        //Then
        assertThat(expectedCakes.size()).isEqualTo(actualCakes.size());
        assertThat(expectedCakes).hasSameElementsAs(actualCakes);
    }

    @Test
    public void testGetCakeReviewById() {
        //Given
        final int cakeId = 2;
        final CakeReview expectedCakeReview = jdbcTemplate.queryForObject("SELECT c.id, c.name, c.image_url, r.comment, yf.number as yum_factor FROM cake c\n" +
                "    INNER JOIN review r on c.id = r.cake_id\n" +
                "    INNER JOIN yum_factor yf on yf.id = r.yum_factor_id\n" +
                "    AND c.id = ?", new Object[]{cakeId}, new BeanPropertyRowMapper<>(CakeReview.class));

        //When
        final CakeReview actualCakeReview = cakeMapper.getCakeReviewById(cakeId);

        //Then
        assertThat(expectedCakeReview.getId()).isEqualTo(actualCakeReview.getId());
        assertThat(expectedCakeReview.getName()).isEqualTo(actualCakeReview.getName());
        assertThat(expectedCakeReview.getImageUrl()).isEqualTo(actualCakeReview.getImageUrl());
        assertThat(expectedCakeReview.getComment()).isEqualTo(actualCakeReview.getComment());
        assertThat(expectedCakeReview.getYumFactor()).isEqualTo(actualCakeReview.getYumFactor());
    }

    @Test
    public void testShouldReturnNullWithNonExistingIdWhenGetCakeReviewById() {
        //Given
        final int cakeId = 6;

        //When
        final CakeReview actualCakeReview = cakeMapper.getCakeReviewById(cakeId);

        //Then
        assertThat(actualCakeReview).isNull();
    }

    @Test
    public void testShouldSaveCake() {
        //Given
        final Integer currentCakeCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cake", Integer.class);
        final Cake expectedCake = Cake.builder().name("Cake 6").imageUrl("url6").build();

        //When
        cakeMapper.saveCake(expectedCake);
        final Integer newId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM cake", Integer.class);
        final Integer updatedCakeCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cake", Integer.class);
        final Cake actualCake = jdbcTemplate.queryForObject("SELECT * FROM cake WHERE id = ?", new Object[]{newId}, new BeanPropertyRowMapper<>(Cake.class));

        //Then
        assertThat(updatedCakeCount).isGreaterThan(currentCakeCount);
        assertThat(expectedCake.getId()).isEqualTo(actualCake.getId());
        assertThat(expectedCake.getName()).isEqualTo(actualCake.getName());
        assertThat(expectedCake.getImageUrl()).isEqualTo(actualCake.getImageUrl());
    }

    @Test
    public void testShouldSaveReview() {
        //Given
        final Integer currentReviewCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM review", Integer.class);

        jdbcTemplate.update("INSERT INTO cake (name, image_url) " +
                "VALUES ('Cake 6', 'url6')");

        Integer cakeId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM cake", Integer.class);
        Integer yumFactorId = jdbcTemplate.queryForObject("SELECT id FROM yum_factor WHERE number = 5", Integer.class);

        final CakeReview expectedCakeReview = CakeReview.cakeReviewBuilder().id(cakeId).name("Cake 6").imageUrl("url6")
                .comment("Comment 6").yumFactor(5).build();

        //When
        cakeMapper.saveReview(expectedCakeReview);
        final Integer newId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM review", Integer.class);
        final Integer updatedReviewCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM review", Integer.class);
        final CakeReview actualReview = jdbcTemplate.queryForObject("SELECT * FROM review WHERE id = ?", new Object[]{newId}, new BeanPropertyRowMapper<>(CakeReview.class));

        //Then
        assertThat(updatedReviewCount).isGreaterThan(currentReviewCount);
        assertThat(cakeId).isEqualTo(actualReview.getId());
        assertThat(expectedCakeReview.getComment()).isEqualTo(actualReview.getComment());
        assertThat(expectedCakeReview.getYumFactor()).isEqualTo(yumFactorId);
    }

    @Test
    public void testShouldUpdateCake() {
        //Given
        final int cakeId = 1;
        final Cake originalCake = jdbcTemplate.queryForObject("SELECT * FROM cake WHERE id = ?", new Object[]{cakeId}, new BeanPropertyRowMapper<>(Cake.class));
        final Cake expectedCake = new Cake();
        expectedCake.setName("new name");
        expectedCake.setImageUrl("newUrl");
        expectedCake.setId(originalCake.getId());

        //When
        cakeMapper.updateCake(expectedCake);
        final Cake actualCake = jdbcTemplate.queryForObject("SELECT * FROM cake WHERE id = ?", new Object[]{cakeId}, new BeanPropertyRowMapper<>(Cake.class));

        //Then
        assertThat(expectedCake.getId()).isEqualTo(actualCake.getId());
        assertThat(expectedCake.getName()).isEqualTo(actualCake.getName());
        assertThat(expectedCake.getImageUrl()).isEqualTo(actualCake.getImageUrl());
        assertThat(originalCake.getImageUrl()).isNotEqualTo(actualCake.getImageUrl());
        assertThat(originalCake.getName()).isNotEqualTo(actualCake.getName());
    }

    @Test
    public void testShouldUpdateReview() {
        //Given
        final int yumFactor = 2;
        final int cakeId = 1;
        final CakeReview originalReview = jdbcTemplate.queryForObject("SELECT r.cake_id as id, r.comment, " +
                " yf.number as yum_factor FROM review r INNER JOIN yum_factor yf on yf.id = r.yum_factor_id AND r.cake_id = ? ",
                new Object[]{cakeId}, new BeanPropertyRowMapper<>(CakeReview.class));
        final CakeReview expectedCakeReview = new CakeReview();
        expectedCakeReview.setComment("new comment");
        expectedCakeReview.setYumFactor(yumFactor);
        expectedCakeReview.setId(cakeId);



        //When
        cakeMapper.updateReview(expectedCakeReview);
        final CakeReview actualReview = jdbcTemplate.queryForObject("SELECT r.cake_id as id, r.comment, " +
                        " yf.number as yum_factor FROM review r INNER JOIN yum_factor yf on yf.id = r.yum_factor_id AND r.cake_id = ? ",
                new Object[]{cakeId}, new BeanPropertyRowMapper<>(CakeReview.class));

        //Then
        assertThat(expectedCakeReview.getComment()).isEqualTo(actualReview.getComment());
        assertThat(expectedCakeReview.getYumFactor()).isEqualTo(actualReview.getYumFactor());
        assertThat(originalReview.getYumFactor()).isNotEqualTo(actualReview.getYumFactor());
        assertThat(originalReview.getId()).isEqualTo(actualReview.getId());
        assertThat(originalReview.getComment()).isNotEqualTo(actualReview.getComment());
        assertThat(originalReview.getId()).isEqualTo(actualReview.getId());
    }

    @Test
    public void testShouldDeleteCake() {
        //Given
        final int cakeId = 1;
        final Cake originalCake = jdbcTemplate.queryForObject("SELECT * FROM cake WHERE id = ?", new Object[]{cakeId}, new BeanPropertyRowMapper<>(Cake.class));
        final Integer currentCakeCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cake", Integer.class);
        final Integer currentReviewCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM review", Integer.class);

        //When
        cakeMapper.deleteCakeById(originalCake.getId());
        final Integer actualCakeCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM cake", Integer.class);
        final Integer actualReviewCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM review", Integer.class);

        //Then
        assertThat(currentCakeCount).isGreaterThan(actualCakeCount);
        assertThat(currentReviewCount).isGreaterThan(actualReviewCount);
    }
}
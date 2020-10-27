package com.cakes.favorite.repository;

import java.util.List;

import com.cakes.favorite.domain.Cake;
import com.cakes.favorite.domain.CakeReview;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CakeMapper {

//    private static final String CAKE_SELECT = "SELECT c.id, c.name, c.image_url, r.comment, yf.number FROM cake c INNER";

    @Select("SELECT *  FROM cake")
    List<Cake> getAllCakes();

    @Select("SELECT c.id, c.name, c.image_url, r.comment, yf.number as yum_factor FROM cake c " +
            " INNER JOIN review r on c.id = r.cake_id " +
            " INNER JOIN yum_factor yf on yf.id = r.yum_factor_id " +
            " AND c.id = #{id}")
    CakeReview getCakeReviewById(int id);

    @Insert("INSERT INTO cake (name, image_url) VALUES (#{name}, #{imageUrl})")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    int saveCake(Cake cake);

    @Insert("INSERT INTO review (cake_id, comment, yum_factor_id) VALUES (#{id}, #{comment}, (SELECT id FROM yum_factor WHERE number = #{yumFactor}))")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn = "id")
    int saveReview(CakeReview cakeReview);

    @Insert("UPDATE cake SET name=#{name}, image_url=#{imageUrl} WHERE id = #{id}")
    int updateCake(Cake cake);

    @Insert("UPDATE review SET comment=#{comment}, yum_factor_id=(SELECT id FROM yum_factor WHERE number = #{yumFactor}) WHERE cake_id = #{id}")
    int updateReview(CakeReview cakeReview);

    @Insert("DELETE FROM cake WHERE id=#{id}")
    int deleteCakeById(int id);
}

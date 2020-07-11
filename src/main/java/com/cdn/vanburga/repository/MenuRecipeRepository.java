package com.cdn.vanburga.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cdn.vanburga.model.MenuRecipe;
import com.cdn.vanburga.model.Order;

public interface MenuRecipeRepository extends JpaRepository<MenuRecipe, Long> {
	
	/*@Query(" select m from MenuRecipe m where m.code = ?1")
	Optional<List<MenuRecipe>> findByCode( String code);*/
}

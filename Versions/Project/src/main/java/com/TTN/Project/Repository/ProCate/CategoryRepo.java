package com.TTN.Project.Repository.ProCate;

import com.TTN.Project.entities.ProCate.Category;
import com.TTN.Project.entities.ProCate.CategoryMetadataField;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findByName(String name);
    Category findById(long id);

    List<Category> findAllByParentId(long id);

    List<Category> findAll(Sort sort);
}

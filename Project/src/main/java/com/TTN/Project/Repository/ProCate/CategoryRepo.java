package com.TTN.Project.Repository.ProCate;

import com.TTN.Project.entities.ProCate.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
}

package com.TTN.Project.entities.ProCate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class CategoryMetadataFieldValuesId implements Serializable {
    @Column(name = "category_id")
    private long categoryId;

    @Column(name = "category_metadata_field_id")
    private long categoryMetadataFieldId;
}

package com.TTN.Project.entities.ProCate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CategoryMetadataFieldValues")
public class CategoryMetadataFieldValues implements Serializable{

    @EmbeddedId
    private CategoryMetadataFieldValuesId categoryMetadataFieldValuesId;

    @ManyToOne
    @MapsId("category_id") //This is the name of attr in EmployerDeliveryAgentPK class
    private Category category;

    @ManyToOne
    @MapsId("category_metadata_field_id")
    private CategoryMetadataField categoryMetadataField;

    @JoinColumn(name = "value")
    private String value;


    public CategoryMetadataFieldValues() {
    }




}

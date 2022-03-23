package com.REST2.Webservices2.Filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringExample {

    @GetMapping("/filtering")
    public MappingJacksonValue userBean(){
        List<User> list=  Arrays.asList(new User("Jayesh Gupta", 1, "jay@123"),
                new User("Mukesh Thakur", 2, "muk@123"),
                new User("Harish Singh", 3, "har@123"));

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name");

        FilterProvider filters = new SimpleFilterProvider().addFilter("Filtering", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(list);

        mapping.setFilters(filters);

        return mapping;

    }
}
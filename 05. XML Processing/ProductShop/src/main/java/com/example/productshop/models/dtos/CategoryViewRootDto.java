package com.example.productshop.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryViewRootDto {

    @XmlElement(name = "category")
    private List<CategoryByProductsDto> categories;

    public List<CategoryByProductsDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryByProductsDto> categories) {
        this.categories = categories;
    }
}

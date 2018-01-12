package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    private String productName;

    @ManyToMany(mappedBy = "productCategoryRels")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Category> productCategories = new HashSet<>();

    @ManyToOne
    private Price price;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public Product productId(String productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public Product productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Set<Category> getProductCategories() {
        return productCategories;
    }

    public Product productCategories(Set<Category> categories) {
        this.productCategories = categories;
        return this;
    }

    public Product addProductCategory(Category category) {
        this.productCategories.add(category);
        category.getProductCategoryRels().add(this);
        return this;
    }

    public Product removeProductCategory(Category category) {
        this.productCategories.remove(category);
        category.getProductCategoryRels().remove(this);
        return this;
    }

    public void setProductCategories(Set<Category> categories) {
        this.productCategories = categories;
    }

    public Price getPrice() {
        return price;
    }

    public Product price(Price price) {
        this.price = price;
        return this;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productId='" + getProductId() + "'" +
            ", productName='" + getProductName() + "'" +
            "}";
    }
}

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
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Category> parentCategories = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "category_product_category_rel",
               joinColumns = @JoinColumn(name="categories_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="product_category_rels_id", referencedColumnName="id"))
    private Set<Product> productCategoryRels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Category categoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category getCategory() {
        return category;
    }

    public Category category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Category> getParentCategories() {
        return parentCategories;
    }

    public Category parentCategories(Set<Category> categories) {
        this.parentCategories = categories;
        return this;
    }

    public Category addParentCategory(Category category) {
        this.parentCategories.add(category);
        category.setCategory(this);
        return this;
    }

    public Category removeParentCategory(Category category) {
        this.parentCategories.remove(category);
        category.setCategory(null);
        return this;
    }

    public void setParentCategories(Set<Category> categories) {
        this.parentCategories = categories;
    }

    public Set<Product> getProductCategoryRels() {
        return productCategoryRels;
    }

    public Category productCategoryRels(Set<Product> products) {
        this.productCategoryRels = products;
        return this;
    }

    public Category addProductCategoryRel(Product product) {
        this.productCategoryRels.add(product);
        product.getProductCategories().add(this);
        return this;
    }

    public Category removeProductCategoryRel(Product product) {
        this.productCategoryRels.remove(product);
        product.getProductCategories().remove(this);
        return this;
    }

    public void setProductCategoryRels(Set<Product> products) {
        this.productCategoryRels = products;
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
        Category category = (Category) o;
        if (category.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", categoryName='" + getCategoryName() + "'" +
            "}";
    }
}

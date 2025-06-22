import React from 'react';
import { Link } from 'react-router-dom';

const CategoryCard = ({ category, productCount, discount }) => {
  const getCategoryColor = (categoryName) => {
    switch (categoryName.toLowerCase()) {
      case 'men': return '#8B5CF6';
      case 'women': return '#EC4899';
      case 'kids': return '#06B6D4';
      default: return '#6B7280';
    }
  };

  return (
    <div 
      className="category-card"
      style={{ backgroundColor: getCategoryColor(category.categoryName) }}
    >
      <Link to={`/browse?category=${category.categoryId}`}>
        <div className="category-content">
          <h3 className="category-name">{category.categoryName}</h3>
          <p className="category-description">{category.description}</p>
          <div className="category-stats">
            <span className="product-count">{productCount} Products</span>
            {discount && <span className="discount">Up to {discount}% OFF</span>}
          </div>
        </div>
      </Link>
    </div>
  );
};

export default CategoryCard;

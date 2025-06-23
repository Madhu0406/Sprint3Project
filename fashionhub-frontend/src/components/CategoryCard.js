import React from 'react';

const CategoryCard = ({ category, stats, onClick }) => {
  const getCategoryIcon = (categoryName) => {
    const name = categoryName.toLowerCase();
    if (name.includes('men') && !name.includes('women')) return '👔';
    if (name.includes('women')) return '👗';
    if (name.includes('kid')) return '🧸';
    if (name.includes('shoe')) return '👟';
    if (name.includes('accessory')) return '👜';
    return '👕';
  };

  const getCategoryClass = (categoryName) => {
    const name = categoryName.toLowerCase();
    if (name.includes('men') && !name.includes('women')) return 'men-category';
    if (name.includes('women')) return 'women-category';
    if (name.includes('kid')) return 'kids-category';
    return 'default-category';
  };

  return (
    <div className={`category-card ${getCategoryClass(category.categoryName)}`}>
      <span className="category-icon">
        {getCategoryIcon(category.categoryName)}
      </span>

      <h3>{category.categoryName}</h3>

      <p>Discover our latest collection of {category.categoryName.toLowerCase()}</p>

      <div className="category-stats">
        <div className="stat">
          <span className="stat-number">{stats?.count || 0}</span>
          <span className="stat-label">Products</span>
        </div>
        <div className="stat">
          <span className="stat-number">{stats?.avgDiscount || 0}%</span>
          <span className="stat-label">Avg Discount</span>
        </div>
      </div>

      <button className="explore-btn" onClick={onClick}>
        Explore {category.categoryName}
      </button>
    </div>
  );
};

export default CategoryCard;

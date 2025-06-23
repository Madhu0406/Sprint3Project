import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import CategoryCard from '../components/CategoryCard';
import { categoryAPI, productAPI } from '../services/api';

const Home = () => {
  const [categories, setCategories] = useState([]);
  const [categoryStats, setCategoryStats] = useState({});
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      await Promise.all([fetchCategories(), fetchCategoryStats()]);
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchCategories = async () => {
    try {
      const response = await categoryAPI.getAllCategories();
      setCategories(response.data);
    } catch (error) {
      console.error('Error fetching categories:', error);
    }
  };

  const fetchCategoryStats = async () => {
    try {
      const response = await productAPI.getAllProducts();
      const products = response.data;
      const stats = {};

      products.forEach(product => {
        const categoryName = product.categoryName;
        if (!stats[categoryName]) {
          stats[categoryName] = { count: 0, totalDiscount: 0, products: 0 };
        }
        stats[categoryName].count++;
        stats[categoryName].totalDiscount += product.discountPercent || 0;
        stats[categoryName].products++;
      });

      Object.keys(stats).forEach(category => {
        stats[category].avgDiscount = Math.round(stats[category].totalDiscount / stats[category].products);
      });

      setCategoryStats(stats);
    } catch (error) {
      console.error('Error fetching category stats:', error);
    }
  };

  const handleCategoryClick = (categoryName) => {
    navigate(`/browse?category=${categoryName.toLowerCase()}`);
  };

  const handleExploreAll = () => {
    navigate('/browse');
  };

  if (loading) {
    return (
      <div className="loading-container">
        <div className="loading-spinner"></div>
        <p className="loading">Loading Fashion Hub...</p>
      </div>
    );
  }

  return (
    <div className="home-page">
      <section className="hero-section">
        <div className="hero-content">
          <div className="hero-header">
            <div className="hero-text">
              <h1>
                Welcome to <span className="brand-name">Fashion Hub</span>
              </h1>
              <p>
                Discover the latest trends in fashion with our curated collection of premium clothing and accessories. Experience style like never before.
              </p>

              <div className="features">
                <div className="feature">
                  <span className="feature-icon">🚚</span>
                  <span>Free Shipping</span>
                </div>
                <div className="feature">
                  <span className="feature-icon">↩️</span>
                  <span>Easy Returns</span>
                </div>
                <div className="feature">
                  <span className="feature-icon">⭐</span>
                  <span>Premium Quality</span>
                </div>
              </div>

              <div className="hero-buttons">
                <button className="btn-primary" onClick={handleExploreAll}>
                  Explore Collection
                </button>
                <button className="btn-secondary" onClick={() => navigate('/browse')}>
                  Shop Now
                </button>
              </div>
            </div>

            <div className="hero-logo">
              <div className="hero-logo-container">
                <div className="hero-logo-shape">
                  <div className="hero-logo-accent1"></div>
                  <div className="hero-logo-accent2"></div>
                  <div className="hero-logo-center"></div>
                </div>
              </div>
            </div>
          </div>

          <div className="category-preview">
            {categories.slice(0, 3).map((category) => (
              <button
                key={category.categoryId}
                className={`category-btn ${category.categoryName.toLowerCase()}`}
                onClick={() => handleCategoryClick(category.categoryName)}
              >
                {category.categoryName}
              </button>
            ))}
          </div>
        </div>
      </section>

      <section className="categories-section">
        <h2>Shop by Category</h2>
        <div className="categories-grid">
          {categories.map((category) => (
            <CategoryCard
              key={category.categoryId}
              category={category}
              stats={categoryStats[category.categoryName]}
              onClick={() => handleCategoryClick(category.categoryName)}
            />
          ))}
        </div>
      </section>
    </div>
  );
};

export default Home;

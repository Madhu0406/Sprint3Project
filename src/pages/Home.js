import React, { useState, useEffect, useRef } from 'react';
import { Link } from 'react-router-dom';
import { categoryAPI } from '../services/api';
import CategoryCard from '../components/CategoryCard';
import '../styles/Home.css';

const Home = () => {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isVisible, setIsVisible] = useState({});
  const sectionRefs = useRef({});

  useEffect(() => {
    fetchCategories();
    setupIntersectionObserver();
  }, []);

  const fetchCategories = async () => {
    try {
      const response = await categoryAPI.getAllCategories();
      setCategories(response.data || []);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching categories:', error);
      setLoading(false);
    }
  };

  const setupIntersectionObserver = () => {
    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            setIsVisible(prev => ({
              ...prev,
              [entry.target.id]: true
            }));
          }
        });
      },
      { threshold: 0.1 }
    );

    Object.values(sectionRefs.current).forEach(ref => {
      if (ref) observer.observe(ref);
    });

    return () => observer.disconnect();
  };

  const getCategoryStats = (categoryName) => {
    const stats = {
      'Men': { count: 2, discount: 25 },
      'Women': { count: 1, discount: 32 },
      'Kids': { count: 1, discount: 33 }
    };
    return stats[categoryName] || { count: 0, discount: 0 };
  };

  if (loading) {
    return (
      <div className="loading">
        <div className="loading-spinner"></div>
        <p>Loading amazing fashion...</p>
      </div>
    );
  }

  return (
    <div className="home">
      <section 
        className="hero-section"
        id="hero"
        ref={el => sectionRefs.current.hero = el}
      >
        <div className="hero-content">
          {/* Animated Logo and Title Section */}
          <div className="brand-section">
            <div className="animated-logo">
              <div className="logo-container">
                <div className="logo-icon">
                  <div className="fashion-symbol">
                    <div className="symbol-part part-1"></div>
                    <div className="symbol-part part-2"></div>
                    <div className="symbol-part part-3"></div>
                    <div className="symbol-center"></div>
                  </div>
                </div>
              </div>
            </div>
            <h1 className="hero-title">
              <span className="title-main">FashionHub</span>
              <span className="title-tagline">Style Redefined</span>
            </h1>
          </div>
          
          <p className="hero-subtitle">
            Discover the latest trends in fashion with premium quality and unbeatable style
          </p>
          <div className="hero-features">
            <div className="feature">
              <h3>✨ Premium Quality</h3>
              <p>High-quality materials and expert craftsmanship in every piece</p>
            </div>
            <div className="feature">
              <h3>🚚 Free Delivery</h3>
              <p>Complimentary shipping on orders above ₹999 nationwide</p>
            </div>
            <div className="feature">
              <h3>🔄 Easy Returns</h3>
              <p>30-day hassle-free return policy with full refund guarantee</p>
            </div>
          </div>
          <div className="hero-actions">
            <Link to="/browse" className="btn btn-primary">
              Shop Now
            </Link>
            <Link to="/browse" className="btn btn-secondary">
              Explore Collections
            </Link>
          </div>
        </div>
      </section>

      <section 
        className="categories-section"
        id="categories"
        ref={el => sectionRefs.current.categories = el}
      >
        <div className="container">
          <h2 className="section-title">Shop by Category</h2>
          <div className="categories-grid">
            {categories.map((category, index) => {
              const stats = getCategoryStats(category.categoryName);
              return (
                <div
                  key={category.categoryId}
                  className="category-card-wrapper"
                  style={{
                    animationDelay: `${index * 0.1}s`
                  }}
                >
                  <CategoryCard
                    category={category}
                    productCount={stats.count}
                    discount={stats.discount}
                  />
                </div>
              );
            })}
          </div>
        </div>
      </section>
    </div>
  );
};

export default Home;

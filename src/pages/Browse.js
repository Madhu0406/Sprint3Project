import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { productAPI, categoryAPI } from '../services/api';
import ProductCard from '../components/ProductCard';
import SearchBar from '../components/SearchBar';
import '../styles/Browse.css';

const Browse = () => {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchParams] = useSearchParams();

  useEffect(() => {
    fetchCategories();
    fetchProducts();
  }, []);

  useEffect(() => {
    const categoryId = searchParams.get('category');
    if (categoryId) {
      fetchProductsByCategory(parseInt(categoryId));
    }
  }, [searchParams]);

  const fetchCategories = async () => {
  try {
    const response = await categoryAPI.getAllCategories();
    console.log('Categories API Response:', response.data); // Debug line
    
    // Ensure we set an array
    if (Array.isArray(response.data)) {
      setCategories(response.data);
    } else {
      console.error('Categories response is not an array:', response.data);
      setCategories([]);
    }
  } catch (error) {
    console.error('Error fetching categories:', error);
    setCategories([]); // Set empty array on error
  }
};


  const fetchProducts = async () => {
    try {
      setLoading(true);
      const response = await productAPI.getAllProducts();
      setProducts(response.data);
    } catch (error) {
      console.error('Error fetching products:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchProductsByCategory = async (categoryId) => {
    try {
      setLoading(true);
      const response = await productAPI.getProductsByCategory(categoryId);
      setProducts(response.data);
    } catch (error) {
      console.error('Error fetching products by category:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (searchTerm, categoryId) => {
    try {
      setLoading(true);
      let response;
      
      if (categoryId && searchTerm) {
        response = await productAPI.searchProductsByCategoryAndKeyword(categoryId, searchTerm);
      } else if (searchTerm) {
        response = await productAPI.searchProducts(searchTerm);
      } else if (categoryId) {
        response = await productAPI.getProductsByCategory(categoryId);
      } else {
        response = await productAPI.getAllProducts();
      }
      
      setProducts(response.data);
    } catch (error) {
      console.error('Error searching products:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="loading">Loading products...</div>;
  }

  return (
    <div className="browse-page">
      <div className="container">
        <h1 className="page-title">Search & Browse Products</h1>
        
        <SearchBar 
          onSearch={handleSearch}
          categories={categories}
        />

        <div className="products-section">
          <div className="products-header">
            <h2>Products ({products.length})</h2>
          </div>
          
          {products.length === 0 ? (
            <div className="no-products">
              <p>No products found matching your criteria.</p>
            </div>
          ) : (
            <div className="products-grid">
              {products.map((product) => (
                <ProductCard key={product.productId} product={product} />
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Browse;

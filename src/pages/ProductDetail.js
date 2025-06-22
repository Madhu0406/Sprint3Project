import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { productAPI } from '../services/api';
import '../styles/ProductDetail.css';

const ProductDetail = () => {
  const { productId } = useParams();
  const navigate = useNavigate();
  const [product, setProduct] = useState(null);
  const [selectedSize, setSelectedSize] = useState('');
  const [quantity, setQuantity] = useState(1);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchProduct();
  }, [productId]);

  const fetchProduct = async () => {
    try {
      setLoading(true);
      const response = await productAPI.getProductById(productId);
      setProduct(response.data);
      if (response.data.sizes && response.data.sizes.length > 0) {
        setSelectedSize(response.data.sizes[0]);
      }
    } catch (error) {
      console.error('Error fetching product:', error);
      setError('Product not found');
    } finally {
      setLoading(false);
    }
  };

  const handleQuantityChange = (change) => {
    const newQuantity = quantity + change;
    if (newQuantity >= 1 && newQuantity <= product.quantityInStock) {
      setQuantity(newQuantity);
    }
  };

  const handleAddToCart = () => {
    if (!selectedSize && product.sizes && product.sizes.length > 0) {
      alert('Please select a size');
      return;
    }
    
    // Add to cart logic would go here
    alert(`Added ${quantity} ${product.productName} (Size: ${selectedSize}) to cart!`);
  };

  if (loading) {
    return <div className="loading">Loading product details...</div>;
  }

  if (error) {
    return (
      <div className="error-page">
        <h2>Product Not Found</h2>
        <p>{error}</p>
        <button onClick={() => navigate('/browse')} className="btn btn-primary">
          Back to Browse
        </button>
      </div>
    );
  }

  if (!product) {
    return <div className="loading">Product not found</div>;
  }

  return (
    <div className="product-detail-page">
      <div className="container">
        <button onClick={() => navigate(-1)} className="back-button">
          ← Back
        </button>

        <div className="product-detail">
          <div className="product-image-section">
            <img 
              src={product.imageUrl} 
              alt={product.productName}
              className="product-image"
            />
          </div>

          <div className="product-info-section">
            <div className="product-header">
              <h1 className="product-title">{product.productName}</h1>
              <p className="product-brand">{product.brand}</p>
              <p className="product-category">{product.categoryName}</p>
            </div>

            <div className="product-pricing">
              <span className="current-price">₹{product.price}</span>
              {product.originalPrice && (
                <>
                  <span className="original-price">₹{product.originalPrice}</span>
                  <span className="discount-badge">{product.discountPercent}% OFF</span>
                </>
              )}
            </div>

            <div className="product-description">
              <h3>Description</h3>
              <p>{product.description}</p>
            </div>

            <div className="product-stock">
              <h3>Availability</h3>
              {product.quantityInStock > 0 ? (
                <p className="in-stock">
                  ✅ In Stock ({product.quantityInStock} items left)
                </p>
              ) : (
                <p className="out-of-stock">❌ Out of Stock</p>
              )}
            </div>

            {product.sizes && product.sizes.length > 0 && (
              <div className="product-sizes">
                <h3>Size Options</h3>
                <div className="size-options">
                  {product.sizes.map((size) => (
                    <button
                      key={size}
                      className={`size-option ${selectedSize === size ? 'selected' : ''}`}
                      onClick={() => setSelectedSize(size)}
                    >
                      {size}
                    </button>
                  ))}
                </div>
              </div>
            )}

            <div className="quantity-selector">
              <h3>Quantity</h3>
              <div className="quantity-controls">
                <button 
                  onClick={() => handleQuantityChange(-1)}
                  disabled={quantity <= 1}
                  className="quantity-btn"
                >
                  -
                </button>
                <span className="quantity-display">{quantity}</span>
                <button 
                  onClick={() => handleQuantityChange(1)}
                  disabled={quantity >= product.quantityInStock}
                  className="quantity-btn"
                >
                  +
                </button>
              </div>
            </div>

            {product.offers && product.offers.length > 0 && (
              <div className="product-offers">
                <h3>Special Offers</h3>
                <ul className="offers-list">
                  {product.offers.map((offer, index) => (
                    <li key={index} className="offer-item">
                      🎁 {offer}
                    </li>
                  ))}
                </ul>
              </div>
            )}

            <div className="product-actions">
              <button 
                onClick={handleAddToCart}
                disabled={product.quantityInStock === 0}
                className="btn btn-primary add-to-cart-btn"
              >
                Add to Cart
              </button>
              <button className="btn btn-secondary buy-now-btn">
                Buy Now
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetail;

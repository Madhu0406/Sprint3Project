import React from 'react';
import { useNavigate } from 'react-router-dom';

const ProductCard = ({ product }) => {
  const navigate = useNavigate();

  const getImageUrl = (imageUrl) => {
    if (!imageUrl) return '/placeholder-image.jpg';
    if (imageUrl.startsWith('http')) {
      return imageUrl;
    }
    return `http://localhost:8080/static/images/${imageUrl}`;
  };

  const formatPrice = (price) => {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      minimumFractionDigits: 0,
      maximumFractionDigits: 0,
    }).format(price).replace('₹', '₹');
  };

  const calculateDiscountedPrice = (price, discountPercent) => {
    if (!discountPercent) return price;
    return price - (price * discountPercent / 100);
  };

  const handleViewDetails = () => {
    navigate(`/product/${product.productId}`);
  };

  const discountedPrice = calculateDiscountedPrice(product.price, product.discountPercent);

  return (
    <div className="product-card">
      <div className="product-image">
        <img
          src={getImageUrl(product.imageUrl)}
          alt={product.productName}
          onError={(e) => {
            e.target.src = '/placeholder-image.jpg';
          }}
        />
      </div>

      <div className="product-info">
        <h3>{product.productName}</h3>

        <div className="product-brand">
          {product.brand}
        </div>

        <div className="price-section">
          <span className="current-price">
            {formatPrice(discountedPrice)}
          </span>
          {product.discountPercent > 0 && (
            <>
              <span className="original-price">
                {formatPrice(product.price)}
              </span>
              <span className="discount-badge">
                {product.discountPercent}% OFF
              </span>
            </>
          )}
        </div>

        <div className="product-meta">
          <span className="category">{product.categoryName}</span>
          <span className={`stock-status ${product.quantityInStock > 10 ? 'in-stock' : product.quantityInStock > 0 ? 'low-stock' : 'out-of-stock'}`}>
            {product.quantityInStock > 10 ? 'In Stock' :
             product.quantityInStock > 0 ? `Only ${product.quantityInStock} left` : 'Out of Stock'}
          </span>
        </div>

        <p className="product-description">
          {product.description?.length > 100
            ? `${product.description.substring(0, 100)}...`
            : product.description}
        </p>

        <button
          className="view-details-btn"
          onClick={handleViewDetails}
        >
          View Details
        </button>
      </div>
    </div>
  );
};

export default ProductCard;

import React from 'react';
import { Link } from 'react-router-dom';

const ProductCard = ({ product }) => {
  return (
    <div className="product-card">
      <Link to={`/product/${product.productId}`}>
        <div className="product-image">
          <img src={product.imageUrl} alt={product.productName} />
          {product.discountPercent > 0 && (
            <div className="discount-badge">
              {product.discountPercent}% OFF
            </div>
          )}
        </div>
        <div className="product-info">
          <h3 className="product-name">{product.productName}</h3>
          <p className="product-brand">{product.brand}</p>
          <div className="product-pricing">
            <span className="current-price">₹{product.price}</span>
            {product.originalPrice && (
              <span className="original-price">₹{product.originalPrice}</span>
            )}
          </div>
          <div className="product-stock">
            {product.quantityInStock > 0 ? (
              <span className="in-stock">In Stock ({product.quantityInStock} left)</span>
            ) : (
              <span className="out-of-stock">Out of Stock</span>
            )}
          </div>
        </div>
      </Link>
    </div>
  );
};

export default ProductCard;

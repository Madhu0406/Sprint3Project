import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/Footer.css';

const Footer = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="footer">
      <div className="footer-content">
        <div className="container">
          <div className="footer-sections">
            
            {/* Company Info Section */}
            <div className="footer-section">
              <div className="footer-brand">
                <h3 className="footer-logo">FashionHub</h3>
                <p className="footer-description">
                  Your ultimate destination for premium fashion. Discover the latest trends 
                  with high-quality materials and expert craftsmanship.
                </p>
                <div className="social-links">
                  <a href="#" className="social-link" aria-label="Facebook">
                    <i className="fab fa-facebook-f"></i>
                  </a>
                  <a href="#" className="social-link" aria-label="Instagram">
                    <i className="fab fa-instagram"></i>
                  </a>
                  <a href="#" className="social-link" aria-label="Twitter">
                    <i className="fab fa-twitter"></i>
                  </a>
                  <a href="#" className="social-link" aria-label="LinkedIn">
                    <i className="fab fa-linkedin-in"></i>
                  </a>
                </div>
              </div>
            </div>

            {/* Quick Links Section */}
            <div className="footer-section">
              <h4 className="footer-title">Quick Links</h4>
              <ul className="footer-links">
                <li><Link to="/">Home</Link></li>
                <li><Link to="/browse">Browse Products</Link></li>
                <li><Link to="/browse?category=1">Men's Fashion</Link></li>
                <li><Link to="/browse?category=2">Women's Style</Link></li>
                <li><Link to="/browse?category=3">Kids Collection</Link></li>
                <li><a href="#about">About Us</a></li>
              </ul>
            </div>

            {/* Customer Service Section */}
            <div className="footer-section">
              <h4 className="footer-title">Customer Service</h4>
              <ul className="footer-links">
                <li><a href="#contact">Contact Us</a></li>
                <li><a href="#shipping">Shipping Info</a></li>
                <li><a href="#returns">Returns & Exchanges</a></li>
                <li><a href="#size-guide">Size Guide</a></li>
                <li><a href="#faq">FAQ</a></li>
                <li><a href="#track-order">Track Your Order</a></li>
              </ul>
            </div>

            {/* Contact Info Section */}
            <div className="footer-section">
              <h4 className="footer-title">Get in Touch</h4>
              <div className="contact-info">
                <div className="contact-item">
                  <i className="fas fa-map-marker-alt"></i>
                  <span>123 Fashion Street, Style City, SC 12345</span>
                </div>
                <div className="contact-item">
                  <i className="fas fa-phone"></i>
                  <span>+91 98765 43210</span>
                </div>
                <div className="contact-item">
                  <i className="fas fa-envelope"></i>
                  <span>support@fashionhub.com</span>
                </div>
                <div className="contact-item">
                  <i className="fas fa-clock"></i>
                  <span>Mon - Sat: 9:00 AM - 8:00 PM</span>
                </div>
              </div>
            </div>

            {/* Newsletter Section */}
            <div className="footer-section">
              <h4 className="footer-title">Stay Updated</h4>
              <p className="newsletter-text">
                Subscribe to our newsletter for exclusive offers and latest fashion updates.
              </p>
              <form className="newsletter-form">
                <div className="newsletter-input-group">
                  <input 
                    type="email" 
                    placeholder="Enter your email"
                    className="newsletter-input"
                    required
                  />
                  <button type="submit" className="newsletter-btn">
                    Subscribe
                  </button>
                </div>
              </form>
              <div className="payment-methods">
                <h5>We Accept</h5>
                <div className="payment-icons">
                  <i className="fab fa-cc-visa"></i>
                  <i className="fab fa-cc-mastercard"></i>
                  <i className="fab fa-cc-paypal"></i>
                  <i className="fab fa-google-pay"></i>
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>

      {/* Footer Bottom */}
      <div className="footer-bottom">
        <div className="container">
          <div className="footer-bottom-content">
            <div className="copyright">
              <p>&copy; {currentYear} FashionHub. All rights reserved.</p>
            </div>
            <div className="footer-bottom-links">
              <a href="#privacy">Privacy Policy</a>
              <a href="#terms">Terms of Service</a>
              <a href="#cookies">Cookie Policy</a>
            </div>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;

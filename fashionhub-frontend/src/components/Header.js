import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Header = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  // Function to get display name from user data
  const getDisplayName = () => {
    if (!user) return '';

    // First try firstName from database
    if (user.firstName) {
      return user.firstName;
    }

    // Fallback: extract first name from email if firstName is not available
    if (user.email) {
      const emailPrefix = user.email.split('@')[0];
      const nameParts = emailPrefix.split('.');
      if (nameParts.length > 0) {
        // Capitalize first letter
        return nameParts[0].charAt(0).toUpperCase() + nameParts[0].slice(1);
      }
    }

    return 'User';
  };

  return (
    <header className="header">
      <div className="header-container">
        <Link to="/" className="logo">
          <div className="logo-icon">
            <div className="logo-shape">
              <div className="logo-dot"></div>
            </div>
          </div>
          <span className="logo-text">Fashion Hub</span>
        </Link>

        <nav className="nav-menu">
          <Link to="/" className="nav-link">Home</Link>
          <Link to="/browse" className="nav-link">Browse</Link>

          {user ? (
            <div className="nav-user">
              <span className="nav-welcome">
                Welcome, {getDisplayName()}!
              </span>
              <button onClick={handleLogout} className="nav-button logout">
                Logout
              </button>
            </div>
          ) : (
            <div className="nav-auth">
              <Link to="/login" className="nav-button">Login</Link>
              <Link to="/register" className="nav-button register">Register</Link>
            </div>
          )}
        </nav>
      </div>
    </header>
  );
};

export default Header;

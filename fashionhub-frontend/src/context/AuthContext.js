import React, { createContext, useContext, useState, useEffect } from 'react';
import { authAPI } from '../services/api';

const AuthContext = createContext();

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const savedUser = localStorage.getItem('user');
    if (savedUser) {
      try {
        const userData = JSON.parse(savedUser);
        setUser(userData);
      } catch (error) {
        console.error('Error parsing saved user:', error);
        localStorage.removeItem('user');
      }
    }
    setLoading(false);
  }, []);

  const login = async (credentials) => {
    try {
      const response = await authAPI.login(credentials);

      if (response.data && response.data.email) {
        const userData = {
          userId: response.data.userId,
          firstName: response.data.firstName,
          lastName: response.data.lastName,
          email: response.data.email,
          phoneNumber: response.data.phoneNumber
        };

        setUser(userData);
        localStorage.setItem('user', JSON.stringify(userData));
        return { success: true, user: userData };
      } else {
        return { success: false, message: 'Invalid response from server' };
      }
    } catch (error) {
      console.error('Login error:', error);
      const message = error.response?.data?.message || 'Login failed. Please try again.';
      return { success: false, message };
    }
  };

  const register = async (userData) => {
    try {
      console.log('Making registration request with:', userData);

      const response = await authAPI.register(userData);
      console.log('Registration response:', response);

      
      if (response.status === 200 || response.status === 201) {
        return {
          success: true,
          message: 'Registration successful! Please login to continue.',
          data: response.data
        };
      } else {
        return {
          success: false,
          message: 'Registration failed. Please try again.'
        };
      }
    } catch (error) {
      console.error('Registration error details:', {
        message: error.message,
        response: error.response?.data,
        status: error.response?.status,
        config: error.config
      });

      if (error.response) {
        
        const errorMessage = error.response.data?.message ||
                           error.response.data?.error ||
                           error.response.data ||
                           `Registration failed with status ${error.response.status}`;
        return { success: false, message: errorMessage };
      } else if (error.request) {
        
        return {
          success: false,
          message: 'No response from server. Please check if the backend is running on port 8080.'
        };
      } else {
        
        return {
          success: false,
          message: 'Registration failed. Please try again.'
        };
      }
    }
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem('user');
  };

  const value = {
    user,
    login,
    register,
    logout,
    loading
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

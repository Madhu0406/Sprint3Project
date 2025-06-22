import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const authAPI = {
  login: (credentials) => api.post('/auth/login', credentials),
  register: (userData) => api.post('/auth/register', userData),
};

export const productAPI = {
  getAllProducts: () => api.get('/products'),
  getProductById: (id) => api.get(`/products/${id}`),
  getProductsByCategory: (categoryId) => api.get(`/products/category/${categoryId}`),
  searchProducts: (keyword) => api.get(`/products/search?keyword=${keyword}`),
  searchProductsByCategoryAndKeyword: (categoryId, keyword) => 
    api.get(`/products/search/category?categoryId=${categoryId}&keyword=${keyword}`),
};

export const categoryAPI = {
  getAllCategories: () => api.get('/categories'),
  getCategoriesWithCount: () => api.get('/categories/with-count'),
};

export default api;

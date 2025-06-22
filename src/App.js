// import React, { useState, useEffect } from 'react';
// import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
// import Header from './components/Header';
// import Home from './pages/Home';
// import Login from './pages/Login';
// import Register from './pages/Register';
// import Browse from './pages/Browse';
// import ProductDetail from './pages/ProductDetail';
// import './styles/App.css';

// function App() {
//   const [user, setUser] = useState(null);

//   useEffect(() => {
//     // Check if user is logged in from localStorage
//     const savedUser = localStorage.getItem('user');
//     if (savedUser) {
//       setUser(JSON.parse(savedUser));
//     }
//   }, []);

//   return (
//     <Router>
//       <div className="App">
//         <Header user={user} setUser={setUser} />
//         <main className="main-content">
//           <Routes>
//             <Route path="/" element={<Home />} />
//             <Route path="/login" element={<Login setUser={setUser} />} />
//             <Route path="/register" element={<Register setUser={setUser} />} />
//             <Route path="/browse" element={<Browse />} />
//             <Route path="/product/:productId" element={<ProductDetail />} />
//           </Routes>
//         </main>
//       </div>
//     </Router>
//   );
// }

// export default App;


import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer'; // Import Footer
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import Browse from './pages/Browse';
import ProductDetail from './pages/ProductDetail';
import './styles/App.css';

function App() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    // Check if user is logged in from localStorage
    const savedUser = localStorage.getItem('user');
    if (savedUser) {
      setUser(JSON.parse(savedUser));
    }
  }, []);

  return (
    <Router>
      <div className="App">
        <Header user={user} setUser={setUser} />
        <main className="main-content">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login setUser={setUser} />} />
            <Route path="/register" element={<Register setUser={setUser} />} />
            <Route path="/browse" element={<Browse />} />
            <Route path="/product/:productId" element={<ProductDetail />} />
          </Routes>
        </main>
        <Footer /> {/* Add Footer here */}
      </div>
    </Router>
  );
}

export default App;

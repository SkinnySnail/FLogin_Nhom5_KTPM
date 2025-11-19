// src/App.js - Tạo file này
import React, { useState } from 'react';
import Login from './components/Login';
import ProductForm from './components/ProductForm';
import './App.css';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [view, setView] = useState('login'); // 'login' | 'product'

  const handleLoginSuccess = (response) => {
    console.log('Login successful:', response);
    setIsLoggedIn(true);
    setView('product');
  };

  const handleProductCreated = (product) => {
    console.log('Product created:', product);
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
    setView('login');
  };

  return (
    <div className="App">
      {/* Navigation */}
      {isLoggedIn && (
        <nav className="navbar">
          <div className="nav-container">
            <h1>Product Management System</h1>
            <div className="nav-actions">
              <button 
                className={`nav-btn ${view === 'product' ? 'active' : ''}`}
                onClick={() => setView('product')}
              >
                Add Product
              </button>
              <button 
                className="nav-btn logout"
                onClick={handleLogout}
              >
                Logout
              </button>
            </div>
          </div>
        </nav>
      )}

      {/* Main Content */}
      <main className="main-content">
        {!isLoggedIn ? (
          <Login onLoginSuccess={handleLoginSuccess} />
        ) : (
          view === 'product' && <ProductForm onProductCreated={handleProductCreated} />
        )}
      </main>
    </div>
  );
}

export default App;
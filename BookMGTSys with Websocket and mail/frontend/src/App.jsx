import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import BookPages from './components/BookPages';
import Login from './components/Login';
import Register from './components/Register';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login/>} />
        <Route path="/register" element={<Register/>} />
        <Route path="/books" element={<BookPages/>} />
      </Routes>
    </Router>
  );
};

export default App;

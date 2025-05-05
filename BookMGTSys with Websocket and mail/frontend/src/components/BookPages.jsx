import React, { useEffect, useState } from 'react';
import { connectWebSocket, disconnectWebSocket } from '../services/websocket';
import axios from 'axios';

const BookPages = () => {
  const [books, setBooks] = useState([]);
  const [newBook, setNewBook] = useState({
    title: '',
    author: '',
    pages: ''
  });
  const [message, setMessage] = useState('');

  useEffect(() => {
    fetchBooks();

  
    connectWebSocket((newBook) => {
      setBooks((prevBooks) => [newBook, ...prevBooks]);
    });

    return () => {
      disconnectWebSocket();
    };
  }, []);

  const fetchBooks = async () => {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
      setMessage('Please login first.');
      return;
    }

    try {
      const res = await axios.get('http://localhost:8080/api/book/getAlBook', {
        headers: {
          Authorization: `Bearer ${token}`, 
        },
      });
      setBooks(res.data);
    } catch (err) {
      console.error('Failed to fetch books', err);
      setMessage('Failed to fetch books.');
    }
  };

  const handleChange = (e) => {
    setNewBook({ ...newBook, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const token = localStorage.getItem('jwtToken'); 
    if (!token) {
      setMessage('Please login first.');
      return;
    }

    try {
      await axios.post('http://localhost:8080/api/book/create', newBook, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setMessage('Book added successfully!');
      setNewBook({ title: '', author: '', pages: '' });
    } catch (err) {
      console.error(err);
      setMessage('Failed to add book.');
    }
  };

  return (
    <div>
      <h2>Books</h2>
      {message && <p>{message}</p>}
      
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="title"
          value={newBook.title}
          onChange={handleChange}
          placeholder="Title"
          required
        />
        <input
          type="text"
          name="author"
          value={newBook.author}
          onChange={handleChange}
          placeholder="Author"
          required
        />
        <input
          type="number"
          name="pages"
          value={newBook.pages}
          onChange={handleChange}
          placeholder="Pages"
          required
        />
        <button type="submit">Add Book</button>
      </form>

      <ul>
      {Array.isArray(books) && books.length > 0 ? (
        books.map((book, index) => (
          <li key={index}>
            <strong>{book.title}</strong> - {book.author} - {book.pages} pages
          </li>
        ))
      ) : (
        <p>no book</p>
      )
    }
      </ul>
    </div>
  );
};

export default BookPages;

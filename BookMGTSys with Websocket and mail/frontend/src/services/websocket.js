// // src/services/websocket.js
// import { Client } from '@stomp/stompjs';
// import SockJS from 'sockjs-client';

// let stompClient = null;

// export const connectWebSocket = (onBookReceived) => {
//   const socket = new SockJS('http://localhost:8080/ws'); // adjust if port differs
//   stompClient = new Client({
//     webSocketFactory: () => socket,
//     debug: (str) => console.log(str),
//     reconnectDelay: 5000,
//     onConnect: () => {
//       console.log('Connected to WebSocket');
//       stompClient.subscribe('/topic/books', (message) => {
//         const book = JSON.parse(message.body);
//         onBookReceived(book); // callback to handle new book
//       });
//     },
//     onStompError: (frame) => {
//       console.error('STOMP error:', frame);
//     },
//   });

//   stompClient.activate();
// };

// export const disconnectWebSocket = () => {
//   if (stompClient) {
//     stompClient.deactivate();
//   }
// };
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

let stompClient = null;

/**
 * Connects to WebSocket using STOMP protocol over SockJS.
 * @param {Function} onBookReceived - callback triggered on receiving new book data.
 */
export const connectWebSocket = (onBookReceived) => {
  const socket = new SockJS('http://localhost:8080/ws');

  stompClient = new Client({
    webSocketFactory: () => socket,
    debug: (str) => console.log(str),
    reconnectDelay: 5000,
    onConnect: () => {
      console.log('Connected to WebSocket');

      
      stompClient.subscribe('/topic/books', (message) => {
        const book = JSON.parse(message.body);
        onBookReceived(book);
      });
    },
    onStompError: (frame) => {
      console.error('STOMP error:', frame);
    },
  });

  stompClient.activate();
};

/**
 * Disconnects from WebSocket
 */
export const disconnectWebSocket = () => {
  if (stompClient) {
    stompClient.deactivate();
  }
};

const { Server } = require('socket.io');
const express = require('express');
const app = express();

const server = app.listen(3001, () => {
  console.log('Server running on port 3001');
});

const io = new Server(server, {
  cors: { origin: "*" }
});

const rooms = {};

function checkWin(board, player) {
  const ROWS = 6;
  const COLS = 7;

  // Check horizontal
  for (let row = 0; row < ROWS; row++) {
    for (let col = 0; col < COLS - 3; col++) {
      if (board[row][col] === player &&
          board[row][col + 1] === player &&
          board[row][col + 2] === player &&
          board[row][col + 3] === player) {
        return true;
      }
    }
  }

  // Check vertical
  for (let row = 0; row < ROWS - 3; row++) {
    for (let col = 0; col < COLS; col++) {
      if (board[row][col] === player &&
          board[row + 1][col] === player &&
          board[row + 2][col] === player &&
          board[row + 3][col] === player) {
        return true;
      }
    }
  }

  // Check diagonal (positive slope)
  for (let row = 0; row < ROWS - 3; row++) {
    for (let col = 0; col < COLS - 3; col++) {
      if (board[row][col] === player &&
          board[row + 1][col + 1] === player &&
          board[row + 2][col + 2] === player &&
          board[row + 3][col + 3] === player) {
        return true;
      }
    }
  }

  // Check diagonal (negative slope)
  for (let row = 3; row < ROWS; row++) {
    for (let col = 0; col < COLS - 3; col++) {
      if (board[row][col] === player &&
          board[row - 1][col + 1] === player &&
          board[row - 2][col + 2] === player &&
          board[row - 3][col + 3] === player) {
        return true;
      }
    }
  }

  return false;
}

function isBoardFull(board) {
  return board.every(row => row.every(cell => cell !== 0));
}

io.on('connection', (socket) => {
  socket.on('join-room', ({ roomId, playerName }) => {
    if (!rooms[roomId]) {
      rooms[roomId] = { players: [], board: Array(6).fill().map(() => Array(7).fill(0)) };
    }

    if (rooms[roomId].players.length < 2) {
      socket.join(roomId);
      rooms[roomId].players.push({ id: socket.id, name: playerName, playerNumber: rooms[roomId].players.length + 1 });
      socket.emit('player-assigned', { playerNumber: rooms[roomId].players.length, players: rooms[roomId].players });

      if (rooms[roomId].players.length === 2) {
        io.to(roomId).emit('game-start', { board: rooms[roomId].board, currentPlayer: 1 });
      } else {
        socket.emit('waiting', { message: 'Waiting for another player...' });
      }
    } else {
      socket.emit('room-full');
    }
  });

  socket.on('make-move', ({ roomId, col, player }) => {
    const room = rooms[roomId];
    if (!room || room.players.find(p => p.id === socket.id)?.playerNumber !== player) return;

    const board = room.board;
    for (let row = 5; row >= 0; row--) {
      if (board[row][col] === 0) {
        board[row][col] = player;
        break;
      }
    }

    const currentPlayer = 3 - player;
    io.to(roomId).emit('move-made', { board, currentPlayer });

    if (checkWin(board, player)) {
      io.to(roomId).emit('game-over', { winner: player });
    } else if (isBoardFull(board)) {
      io.to(roomId).emit('game-over', { isDraw: true });
    }
  });

  socket.on('disconnect', () => {
    for (const roomId in rooms) {
      const index = rooms[roomId].players.findIndex(p => p.id === socket.id);
      if (index !== -1) {
        rooms[roomId].players.splice(index, 1);
        io.to(roomId).emit('player-disconnected');
        if (rooms[roomId].players.length === 0) {
          delete rooms[roomId];
        }
        break;
      }
    }
  });
});
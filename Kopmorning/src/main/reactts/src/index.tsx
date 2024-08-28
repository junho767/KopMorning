// URL 의 이동을 토큰이 존재하냐 안하냐의 여부로 제어할 것이기 때문에
// 'react-router-dom 의 BrowserRouter 를 App 위에 덮어주었고
// 그 위에 Context 적용을 위해 AuthContextProvider 를 덮어주었다.

import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';

import './index.css';
import App from './App';
import { AuthContextProvider } from './store/auth-context';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <AuthContextProvider>
      <BrowserRouter>
          <App />
      </BrowserRouter>
    </AuthContextProvider>
);
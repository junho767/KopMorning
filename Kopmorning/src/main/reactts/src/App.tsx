import React, { useContext } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';


import CreateAccountForm from './components/Auth/CreateAccountForm';
import Layout from './components/Layout/Layout';
import AuthPage from './pages/AuthPage';
import HomePage from './pages/HomePage';
import ArticleListPage from './pages/ArticleListPage';
import ArticleOnePage from './pages/ArticleOnePage';
import PutArticlePage from './pages/PutArticlePage';
import CreateArticlePage from './pages/CreateArticlePage';
import ProfilePage from './pages/ProfilePage';
import AuthContext from './store/auth-context';
import logo from './logo.svg';
import './App.css';

function App() {

  const authCtx = useContext(AuthContext);

  return (
    <Layout>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="page/:pageId" element={<ArticleListPage/>}/>
        <Route path="/signup/" element={authCtx.isLoggedIn ? <Navigate to='/' /> : <CreateAccountForm />} />
        <Route path="/putArticle/:articleId"
          element={authCtx.isLoggedIn ? <PutArticlePage/> : <Navigate to ='/'/>}
          />
        <Route path="/postArticle/" element={<CreateArticlePage/>}/>
        <Route path="/article/:articleId" element={<ArticleOnePage/>}/>
        <Route path="/login/*"
          element={authCtx.isLoggedIn ? <Navigate to='/' /> : <AuthPage />}
        />
        <Route path="/profile/" element={!authCtx.isLoggedIn ? <Navigate to='/' /> : <ProfilePage />} />
      </Routes>
    </Layout>
  );
}

export default App;

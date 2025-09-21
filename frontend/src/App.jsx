import './App.css'
import React from "react";
import Header from "./layout/header";
import Footer from "./layout/footer";

import { useAuth0 } from "@auth0/auth0-react";

import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";

import Home from "./pages/home";
import Dashboard from "./pages/dashboard";

function App() {
  const { loginWithRedirect, logout, user, isAuthenticated } = useAuth0();

  const ProtectedRoute = ({ children }) => {
    const { isAuthenticated, isLoading } = useAuth0();
    if (isLoading) return <div>Loading...</div>;
    return isAuthenticated ? children : <Navigate to="/" />;
  };

  return (
    <Router>
      <div className="flex flex-col min-h-screen">
        <Header />

        <main className="flex-grow p-4">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
          </Routes>
        </main>

        <Footer />
      </div>
    </Router>
  );
}

export default App;
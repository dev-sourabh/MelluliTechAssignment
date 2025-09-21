import { useAuth0 } from "@auth0/auth0-react";
import { useNavigate } from "react-router-dom";
import React, { useEffect, useState } from "react";

const Home = () => {
  const { loginWithRedirect } = useAuth0();
  const navigate = useNavigate();

  return (
    <div>
      <h2>Home Page</h2>
      <a href="/dashboard"
        className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
      >
        Dashboard
      </a>
    </div>
  );
};

export default Home;
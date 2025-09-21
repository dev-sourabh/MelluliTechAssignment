import { Link } from "react-router-dom";
import { useAuth0 } from "@auth0/auth0-react";

const Header = () => {
  const { isAuthenticated, loginWithRedirect, logout, user } = useAuth0();

  return (
    <nav className="bg-blue-600 text-white p-4 flex items-center justify-between">
      <div className="flex items-center space-x-2">
        <img src="vite.svg" alt="Logo" className="h-8" />
        <span className="font-bold text-xl">Network Monitor</span>
      </div>

      {/* <div className="hidden md:flex space-x-4">
        <Link to="/dashboard" className="hover:underline">Dashboard</Link>
      </div> */}

      <div>
        {!isAuthenticated ? (
          <button
            onClick={() => loginWithRedirect()}
            className="bg-green-500 px-3 py-1 rounded hover:bg-green-600"
          >
            Login
          </button>
        ) : (
          <div className="flex items-center space-x-2">
            <span className="hidden sm:block">{user.name}</span>
            <button
              onClick={() => logout({ returnTo: window.location.origin })}
              className="bg-red-500 px-3 py-1 rounded hover:bg-red-600"
            >
              Logout
            </button>
          </div>
        )}
      </div>
    </nav>
  );
};

export default Header;
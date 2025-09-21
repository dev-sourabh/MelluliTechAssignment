import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { Auth0Provider } from "@auth0/auth0-react";

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Auth0Provider
      domain="dev-jaw02p1aya2gig26.us.auth0.com"
      clientId="ryLCMA8DDDAa6Qwv9MBC8udX8z1rtoFH"
      authorizationParams={{ redirect_uri: window.location.origin, audience: "https://myapi.mellulitech.com" }}
      useRefreshTokens={true}
      cacheLocation="localstorage"
    >
      <App />
    </Auth0Provider>,
  </StrictMode>,
)

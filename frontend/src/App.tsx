/* eslint-disable react/no-children-prop */
import React, { FC } from 'react';
import { BrowserRouter as Router, Link, Route, Routes } from 'react-router-dom';
import LoginPage from './componenst/Pages/LoginPage';
import SignUpPage from './componenst/Pages/SignUpPage';
import { routes } from './utils/routes';

const App: FC = () => {

  return (
    <Router>
      <div>
        <ul>
          <li>
            <Link to={routes.appPagePath()}>Home</Link>
          </li>
          <li>
            <Link to={routes.loginPagePath()}>Log In</Link>
          </li>
          <li>
            <Link to={routes.signupPagePath()}>Sign Up</Link>
          </li>
        </ul>
      </div>
      <Routes>
        <Route path={routes.loginPagePath()} element={<LoginPage />} />
        <Route path={routes.signupPagePath()} element={<SignUpPage />} />
      </Routes>
    </Router>
  );
};

export default App;

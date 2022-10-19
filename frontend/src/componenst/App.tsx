import React, { FC, ReactElement } from 'react';
import { BrowserRouter as Router, Navigate, Route, Routes } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Header from './header';
import Modal from './modals';
import AppPage from '../screens/appPage';
import LoginPage from '../screens/loginPage';
import MainPage from '../screens/mainPage';
import NewAppPage from '../screens/newAppPage';
import ProfilePage from '../screens/profilePage';
import SignUpPage from '../screens/signUpPage';
import UserAppsPage from '../screens/userAppsPage';
import { useAppSelector } from '../hooks/defaultHooks';
import selectors from '../selectors';
import { routes } from '../utils/routes';

interface RouterProps {
  children: ReactElement;
}

const PrivateRouter: FC<RouterProps> = ({ children }) => {
  const isAuth = useAppSelector(selectors.userAuth);
  return isAuth ? children : <Navigate to={routes.pages.loginPagePath()} />;
};

const AuthRouter: FC<RouterProps> = ({ children }) => {
  const isAuth = useAppSelector(selectors.userAuth);
  return isAuth ? <Navigate to={routes.pages.mainPagePath()} /> : children;
};

const App: FC = () => (
  <div className='d-flex flex-column h-100'>
    <Router>
      <Header />
      <Routes>
        <Route
          path={routes.pages.loginPagePath()}
          element={
            <AuthRouter>
              <LoginPage />
            </AuthRouter>
          }
        />
        <Route
          path={routes.pages.signupPagePath()}
          element={
            <AuthRouter>
              <SignUpPage />
            </AuthRouter>
          }
        />
        <Route
          path={routes.pages.mainPagePath()}
          element={
            <PrivateRouter>
              <MainPage />
            </PrivateRouter>
          }
        />
        <Route
          path={routes.pages.appsPagePath()}
          element={
            <PrivateRouter>
              <AppPage />
            </PrivateRouter>
          }
        />
        <Route
          path={routes.pages.newAppPagePath()}
          element={
            <PrivateRouter>
              <NewAppPage />
            </PrivateRouter>
          }
        />
        <Route
          path={routes.pages.userAppsPagePath()}
          element={
            <PrivateRouter>
              <UserAppsPage />
            </PrivateRouter>
          }
        />
        <Route
          path={routes.pages.profilePagePath()}
          element={
            <PrivateRouter>
              <ProfilePage />
            </PrivateRouter>
          }
        />
      </Routes>
      <Modal />
      <ToastContainer autoClose={5000} />
    </Router>
  </div>
);

export default App;

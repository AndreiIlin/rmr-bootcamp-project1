/* eslint-disable react/no-children-prop */
import React, { FC, ReactElement } from 'react';
import { BrowserRouter as Router, Navigate, Route, Routes, useParams } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import Header from './componenst/Header/Header';
import Modal from './componenst/Modals';
import AppPage from './componenst/Pages/appPage';
import LoginPage from './componenst/Pages/LoginPage/LoginPage';
import MainPage from './componenst/Pages/mainPage/MainPage';
import NewAppPage from './componenst/Pages/newAppPage/NewAppPage';
import ProfilePage from './componenst/Pages/profilePage';
import SignUpPage from './componenst/Pages/SignUpPage/SignUpPage';
import UserAppsPage from './componenst/Pages/userAppsPage/UserAppsPage';
import { useAppSelector } from './hooks/defaultHooks';
import selectors from './selectors';
import 'react-toastify/dist/ReactToastify.css';
import { routes } from './utils/routes';

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

const App: FC = () => {
  const { id } = useParams();
  console.log(id);
  return (
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
};

export default App;

/* eslint-disable react/no-children-prop */
import React, { FC, ReactElement } from 'react';
import { BrowserRouter as Router, Navigate, Route, Routes, useParams } from 'react-router-dom';
import Header from './componenst/Header/Header';
import Modal from './componenst/Modals';
import AppPage from './componenst/Pages/appPage/AppPage';
import LoginPage from './componenst/Pages/LoginPage/LoginPage';
import MainPage from './componenst/Pages/mainPage/MainPage';
import NewAppPage from './componenst/Pages/newAppPage/NewAppPage';
import NotFoundPage from './componenst/Pages/NotFound/NotFoundPage';
import SignUpPage from './componenst/Pages/SignUpPage/SignUpPage';
import UserAppsPage from './componenst/Pages/userAppsPage/UserAppsPage';
import { useAppSelector } from './hooks/defaultHooks';
import selectors from './selectors';
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

const AppRouter: FC = () => {
  const { id } = useParams();
  return !id?.match(/\D+/i) ? <AppPage /> : <NotFoundPage />;
};

const App: FC = () => {
  return (
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
          path={routes.pages.appPagePath()}
          element={
            <PrivateRouter>
              <AppRouter />
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
      </Routes>
      <Modal />
    </Router>
  );
};

export default App;

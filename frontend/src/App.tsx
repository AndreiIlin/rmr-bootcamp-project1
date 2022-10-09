/* eslint-disable react/no-children-prop */
import React, { FC } from 'react';
import { BrowserRouter as Router, Navigate, Outlet, Route, Routes } from 'react-router-dom';
import Modal from './componenst/Modals';
import Header from './componenst/Header/Header';
import AppPage from './componenst/Pages/AppPage/AppPage';
import LoginPage from './componenst/Pages/LoginPage/LoginPage';
import NotFoundPage from './componenst/Pages/NotFound/NotFoundPage';
import SignUpPage from './componenst/Pages/SignUpPage/SignUpPage';
import { useAppSelector } from './hooks/defaultHooks';
import selectors from './selectors';
import { routes } from './utils/routes';

interface MainOutletProps {
  goStorePage: boolean;
}

const MainOutlet: FC<MainOutletProps> = ({ goStorePage }) => {
  const auth = useAppSelector(selectors.userAuth);
  if (goStorePage) {
    return auth ? <Outlet /> : <Navigate to={routes.loginPagePath()} />;
  }
  return auth ? <Navigate to={routes.appPagePath()} /> : <Outlet />;
};

const App: FC = () => {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path={routes.appPagePath()} element={<MainOutlet goStorePage={true} />}>
          <Route index element={<AppPage />} />
        </Route>
        <Route path={routes.loginPagePath()} element={<MainOutlet goStorePage={false} />}>
          <Route index element={<LoginPage />} />
        </Route>
        <Route path={routes.signupPagePath()} element={<MainOutlet goStorePage={false} />}>
          <Route index element={<SignUpPage />} />
        </Route>
        <Route path='/*' element={<NotFoundPage />} />
      </Routes>
      <Modal />
    </Router>
  );
};

export default App;

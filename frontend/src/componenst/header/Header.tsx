import React, { FC } from 'react';
import { Container, Navbar } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import logo from '../../assets/logo.png';
import { useAppSelector } from '../../hooks/defaultHooks';
import selectors from '../../selectors';
import { routes } from '../../utils/routes';
import LanguageSwitcher from './languageSwitcher';
import Menu from './menu';

const Nav: FC = () => {
  const isAuth = useAppSelector(selectors.userAuth);

  return (
    <Navbar variant="dark" expand="false" className="shadow shadow-lg header-bg">
      <Container className="flex-nowrap p-0">
        <Navbar.Brand className="flex-grow-1" as={Link} to={routes.pages.mainPagePath()}>
          <img src={logo} alt="TrueStore" />
        </Navbar.Brand>
        <LanguageSwitcher />
        {isAuth && <Menu />}
      </Container>
    </Navbar>
  );
};

export default Nav;

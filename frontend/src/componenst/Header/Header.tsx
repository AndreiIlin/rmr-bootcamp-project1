import React, { FC } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Navbar } from 'react-bootstrap';
import { useAppSelector } from '../../hooks/defaultHooks';
import selectors from '../../selectors';
import logo from '../../assets/logo.png';
import Dropdown from './Dropdown';
import { routes } from '../../utils/routes';

const Nav: FC = () => {
  const isAuth = useAppSelector(selectors.userAuth);
  const navigate = useNavigate();
  const logoClickHandler = (): void => {
    navigate(routes.pages.mainPagePath());
  };
  return (
    <Navbar collapseOnSelect bg='dark' variant='dark'>
      <Container className='justify-content-between' fluid>
        <Navbar.Brand onClick={logoClickHandler}>
          <img src={logo} alt='TrueStore' />
        </Navbar.Brand>
        {isAuth && <Dropdown />}
      </Container>
    </Navbar>
  );
};

export default Nav;

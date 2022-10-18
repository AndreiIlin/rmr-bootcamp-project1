import React, { FC } from 'react';
import { Container, Navbar } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import logo from '../../assets/logo.png';
import { useAppSelector } from '../../hooks/defaultHooks';
import selectors from '../../selectors';
import { routes } from '../../utils/routes';
import Menu from './menu';

const Nav: FC = () => {
  const isAuth = useAppSelector(selectors.userAuth);

  return (
    <Navbar bg='dark' variant='dark' expand='false' className='shadow shadow-lg'>
      <Container fluid>
        <Navbar.Brand as={Link} to={routes.pages.mainPagePath()}>
          <img src={logo} alt='TrueStore' />
        </Navbar.Brand>
        {isAuth && <Menu />}
      </Container>
    </Navbar>
  );
};

export default Nav;

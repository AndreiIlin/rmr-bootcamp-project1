import React, { FC } from 'react';
import { Container, Navbar } from 'react-bootstrap';
import { useAppSelector } from '../../hooks/defaultHooks';
import selectors from '../../selectors';
import logo from '../../assets/logo.png';
import Dropdown from './Dropdown';

const Nav: FC = () => {
  const isAuth = useAppSelector(selectors.userAuth);
  return (
    <Navbar collapseOnSelect bg='dark' variant='dark'>
      <Container className='justify-content-between'>
        <Navbar.Brand>
          <img src={logo} alt='TrueStore' />
        </Navbar.Brand>
        {isAuth && <Dropdown />}
      </Container>
    </Navbar>
  );
};

export default Nav;

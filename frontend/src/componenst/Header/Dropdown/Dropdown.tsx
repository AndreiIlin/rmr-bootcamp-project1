import React, { FC } from 'react';
import { Nav, Navbar, NavDropdown } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useAppDispatch } from '../../../hooks/defaultHooks';
import { useGetUserInfoQuery } from '../../../store/api/userInfoApiSlice/userInfoApiSlice';
import { logout } from '../../../store/slices/authSlice';
import './index.css';

const Dropdown: FC = () => {
  const dispatch = useAppDispatch();
  const { t } = useTranslation();
  const { data } = useGetUserInfoQuery();
  const logoutHandle = () => {
    dispatch(logout());
  };
  return (
    <>
      <Navbar.Toggle aria-controls="responsive-navbar-nav" />
      <Navbar.Collapse id="responsive-navbar-nav" className="flex-grow-0">
        <Nav className="me-auto">
          <NavDropdown
            title=""
            id="collasible-nav-dropdown"
            className="navbar-toggler-icon"
            align="end"
          >
            <NavDropdown.Header>
              {data && data.email}
            </NavDropdown.Header>
            <NavDropdown.Item>
              {t('header.profile')}
            </NavDropdown.Item>
            <NavDropdown.Divider />
            <NavDropdown.Item>
              {t('header.newApp')}
            </NavDropdown.Item>
            <NavDropdown.Divider />
            <NavDropdown.Item onClick={logoutHandle}>
              {t('header.logout')}
            </NavDropdown.Item>
          </NavDropdown>
        </Nav>
      </Navbar.Collapse>
    </>
  );
};

export default Dropdown;

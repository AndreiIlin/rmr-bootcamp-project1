import React, { FC, useState } from 'react';
import { Button, Nav, Offcanvas } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import { useAppDispatch } from '../../../hooks/defaultHooks';
import { logout } from '../../../store/slices/authSlice';
import { routes } from '../../../utils/routes';

const Menu: FC = () => {
  const dispatch = useAppDispatch();
  const { t } = useTranslation();
  const logoutHandle = () => {
    dispatch(logout());
  };
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  return (
    <>
      <Button variant='outline-light' onClick={handleShow}>
        {t('header.menu')}
      </Button>
      <Offcanvas show={show} onHide={handleClose} placement='end'>
        <Offcanvas.Header closeButton />
        <Offcanvas.Body>
          <Nav>
            <Nav.Item>
              <Nav.Link as={Link} to={routes.pages.profilePagePath()}>
                {t('header.profile')}
              </Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link as={Link} to={routes.pages.mainPagePath()}>
                {t('header.apps')}
              </Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link as={Link} to={routes.pages.userAppsPagePath()}>
                {t('header.userApps')}
              </Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link as={Link} to={routes.pages.newAppPagePath()}>
                {t('header.newApp')}
              </Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link onClick={logoutHandle}>{t('header.logout')}</Nav.Link>
            </Nav.Item>
          </Nav>
        </Offcanvas.Body>
      </Offcanvas>
    </>
  );
};

export default Menu;

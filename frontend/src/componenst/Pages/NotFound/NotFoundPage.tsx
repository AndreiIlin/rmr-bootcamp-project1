import React from 'react';
import { Button, Container } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { routes } from '../../../utils/routes';

const NotFoundPage = () => {
  const navigate = useNavigate();
  const { t } = useTranslation();
  const navigateHandle = () => {
    navigate(routes.pages.mainPagePath());
  };
  return (
    <Container fluid className="wh-100 align-items-center text-center">
      <p className="display-1">{t('page404.header')}</p>
      <p className="display-6">{t('page404.error')}</p>
      <Button variant="outline-dark" onClick={navigateHandle}>{t('page404.button')}</Button>
    </Container>
  );
};

export default NotFoundPage;

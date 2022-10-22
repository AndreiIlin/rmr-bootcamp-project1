import React, { FC } from 'react';
import { Container, Row, Button, Col } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { routes } from '../../utils/routes';

const LandingPage: FC = () => {
  const navigate = useNavigate();
  const { t } = useTranslation();
  const clickHandler = () => {
    navigate(routes.pages.loginPagePath());
  };
  return (
    <Container as="main" className="d-flex flex-column mt-5 text-center text-light">
      <Row>
        <Col>
          <h3>{t('landingPage.header')}</h3>
          <p>{t('landingPage.description')}</p>
          <Button onClick={clickHandler} variant="outline-light" className="px-5 mt-3">
            {t('landingPage.enterButton')}
          </Button>
        </Col>
      </Row>
      <Row className="d-fex flex-column mt-5 gap-3">
        <Row className="d-flex align-items-center gap-5">
          <Col xs={2} className="bg-dark p-3 rounded rounded-3">
            {t('landingPage.firstRole')}
          </Col>
          <Col className="text-start">{t('landingPage.firstDescription')}</Col>
        </Row>
        <Row className="d-flex align-items-center gap-5">
          <Col xs={2} className="bg-dark p-3 rounded rounded-3">
            {t('landingPage.secondRole')}
          </Col>
          <Col className="text-start">{t('landingPage.secondDescription')}</Col>
        </Row>
        <Row className="d-flex align-items-center gap-5">
          <Col xs={2} className="bg-dark p-3 rounded rounded-3">
            {t('landingPage.thirdRole')}
          </Col>
          <Col className="text-start">{t('landingPage.thirdDescription')}</Col>
        </Row>
      </Row>
    </Container>
  );
};

export default LandingPage;

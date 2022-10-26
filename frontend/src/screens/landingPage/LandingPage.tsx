import React, { FC } from 'react';
import { Container, Row, Button, Col, Image } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { routes } from '../../utils/routes';
import coding from '../../assets/coding.png';
import tester from '../../assets/tester.png';
import enterprise from '../../assets/enterprise.png';

const LandingPage: FC = () => {
  const navigate = useNavigate();
  const { t } = useTranslation();
  const clickHandler = () => {
    navigate(routes.pages.loginPagePath());
  };
  return (
    <Container as="main" className="d-flex flex-column mt-5">
      <Row className="text-left">
        <Col>
          <h3>{t('landingPage.header')}</h3>
          <p className="mt-2">{t('landingPage.description')}</p>
        </Col>
      </Row>
      <Row className="m-auto my-5">
        <Button onClick={clickHandler} className="main-color" size="lg">
          {t('landingPage.enterButton')}
        </Button>
      </Row>
      <Row className="d-fex mt-5 gap-3">
        <Col className="d-flex flex-column rounded border border-dark py-5 justify-content-center align-items-center gap-3">
          <Image src={coding} className="w-25" />
          <Row>{t('landingPage.firstRole')}</Row>
          <Row className="text-center px-5">{t('landingPage.firstDescription')}</Row>
        </Col>
        <Col className="d-flex flex-column rounded border border-dark py-5 justify-content-center align-items-center gap-3">
          <Image src={tester} className="w-25" />
          <Row>{t('landingPage.secondRole')}</Row>
          <Row className="text-center px-5">{t('landingPage.secondDescription')}</Row>
        </Col>
        <Col className="d-flex flex-column rounded border border-dark py-5 justify-content-center align-items-center gap-3">
          <Image src={enterprise} className="w-25" />
          <Row>{t('landingPage.thirdRole')}</Row>
          <Row className="text-center px-5">{t('landingPage.thirdDescription')}</Row>
        </Col>
      </Row>
    </Container>
  );
};

export default LandingPage;

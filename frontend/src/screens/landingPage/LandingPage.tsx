import React, { FC } from 'react';
import { Container, Row, Button, Col } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { routes } from '../../utils/routes';

const LandingPage: FC = () => {
  const navigate = useNavigate();
  const clickHandler = () => {
    navigate(routes.pages.loginPagePath());
  };
  return (
    <Container as="main" className="d-flex flex-column mt-5 text-center text-light vh-100">
      <Row>
        <Col>
          <h3>Платформа от разработчиков для разработчиков и тестировщиков </h3>
          <p>Благодаря нам, ваш продукт станет качественнее</p>
          <Button onClick={clickHandler} variant="outline-light" className="px-5 mt-3">
            Войти
          </Button>
        </Col>
      </Row>
      <Row className="d-fex flex-column mt-5 gap-3">
        <Row className="d-flex align-items-center gap-5">
          <Col xs={2} className="bg-dark p-3 rounded rounded-3">
            Development
          </Col>
          <Col className="text-start">
            Простая система загрузки, обновления приложения и получения обратной связи от
            пользователей
          </Col>
        </Row>
        <Row className="d-flex align-items-center gap-5">
          <Col xs={2} className="bg-dark p-3 rounded rounded-3">
            QA
          </Col>
          <Col className="text-start">
            Простая система заключения контракта на тестирование и получения опыта на реальном
            проекте
          </Col>
        </Row>
        <Row className="d-flex align-items-center gap-5">
          <Col xs={2} className="bg-dark p-3 rounded rounded-3">
            Company
          </Col>
          <Col className="text-start">
            Надежная платформа для найма сотрудников с опытом разработки и тестирования
          </Col>
        </Row>
      </Row>
    </Container>
  );
};

export default LandingPage;

import React from 'react';
import { Button, Col, Container, Row, Tab, Tabs } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useParams } from 'react-router-dom';
import { useGetAppQuery } from '../../../store/api/appsApiSlice/appsApiSlice';

const AppPage = () => {
  const { id } = useParams();
  const { data } = useGetAppQuery(id as string);
  const { t } = useTranslation();
  return (
    <Container>
      <Row>
        <Col className="d-flex">
          <Col className="col-4">
            <img src={data?.iconImage} alt={data?.appName} className="w-100" />
          </Col>
          <Col className="col-8 d-flex flex-column justify-content-center">
            <h3>{data?.appName}</h3>
            <p>{t('app.rating')}:</p>
            <p>{t('app.bugPrice')}: {t('app.cost', { count: data?.bugPrice })}</p>
            <p>{t('app.featurePrice')}: {t('app.cost', { count: data?.featurePrice })}</p>
          </Col>
        </Col>
        <Col className="d-flex justify-content-end align-items-start mt-3">
          <Button>{t('app.testing')}</Button>
        </Col>
      </Row>
      <Tabs
        defaultActiveKey="description"
        className="mb-3 mt-5"
        id="justify-tab-example"
        justify
      >
        <Tab eventKey="description" title={t('app.description')}>
          {data?.appDescription}
        </Tab>
        <Tab eventKey="comments" title={t('app.comments')}>
          Comments...
        </Tab>
      </Tabs>
    </Container>
  );
};

export default AppPage;

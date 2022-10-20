import React from 'react';
import { Button, Col, Container, Row, Tab, Tabs } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useParams } from 'react-router-dom';
import { useGetAppQuery } from '../../store/api/appsApiSlice/appsApiSlice';

const AppPage = () => {
  const { id } = useParams();
  const { data } = useGetAppQuery(id as string);
  const { t } = useTranslation();
  return (
    <Container className="app-container bg-light my-5 p-5 shadow shadow-lg rounded-3">
      <Row>
        <Col xs={5} sm={4}>
          <img src={data?.iconImage} alt={data?.appName} className="img-fluid shadow rounded-5" />
        </Col>
        <Col xs={7} sm={8}>
          <h3 className="mb-3">{data?.appName}</h3>
          <p>
            {t('app.bugPrice')}: {t('app.cost', { count: data?.bugPrice })}
          </p>
          <p>
            {t('app.featurePrice')}: {t('app.cost', { count: data?.featurePrice })}
          </p>
          <Button variant="outline-primary" className="mt-3">
            {t('app.testing')}
          </Button>
        </Col>
      </Row>
      <Tabs defaultActiveKey="description" className="mt-5" id="justify-tab-example" justify>
        <Tab eventKey="description" title={t('app.description')} className="mt-2">
          {data?.appDescription}
        </Tab>
        <Tab eventKey="comments" title={t('app.comments')} className="mt-3">
          Comments...
        </Tab>
      </Tabs>
    </Container>
  );
};

export default AppPage;
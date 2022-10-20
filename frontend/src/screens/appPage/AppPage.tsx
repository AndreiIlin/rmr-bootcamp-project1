import React from 'react';
import { Button, Col, Container, Row, Tab, Tabs } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useParams } from 'react-router-dom';
import { toast } from 'react-toastify';
import { useGetAppQuery } from '../../store/api/appsApiSlice/appsApiSlice';
import { useSetContractMutation } from '../../store/api/contractsApiSlice/contractsApiSlice';

const AppPage = () => {
  const { id } = useParams();
  const { data, isLoading } = useGetAppQuery(id as string);
  const userId = JSON.parse(localStorage.getItem('trueStore') as string).user_id;
  const isUserApp = userId === data?.ownerId;
  const { t } = useTranslation();
  const [setContract] = useSetContractMutation();
  const contractHandler = async () => {
    try {
      if (data) {
        await setContract({ appId: data?.id }).unwrap();
      }
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (error: any) {
      console.log(error);
    }
  };

  return (
    <Container className="vh-100 wrapper bg-light my-5 p-5 shadow shadow-lg rounded-3">
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
          {!isLoading && !isUserApp ? (
            data?.contractId ? (
              <Button variant="outline-primary" className="mt-3" onClick={contractHandler}>
                {t('app.testing')}
              </Button>
            ) : (
              <Button
                variant="outline-primary"
                className="mt-3"
                onClick={() => toast.success('Приложение удачно скачано')}
              >
                Скачать приложение
              </Button>
            )
          ) : null}
        </Col>
      </Row>
      <Tabs defaultActiveKey="description" className="mt-5" id="justify-tab-example" justify>
        <Tab eventKey="description" title={t('app.description')} className="mt-2 overflow-hidden">
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

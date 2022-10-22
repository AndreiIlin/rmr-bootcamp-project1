import React, { FC } from 'react';
import { useTranslation } from 'react-i18next';
import { App } from '../../models/services/app';
import { useAppDispatch } from '../../hooks/defaultHooks';
import { openModal } from '../../store/slices/modalSlice';
import { Button, Col, Row } from 'react-bootstrap';
import { useSetContractMutation } from '../../store/api/contractsApiSlice/contractsApiSlice';

interface AboutAppProps {
  data: App | undefined;
  isLoading: boolean;
}

const AboutApp: FC<AboutAppProps> = ({ data, isLoading }) => {
  const { t } = useTranslation();
  const dispatch = useAppDispatch();
  const userId = JSON.parse(localStorage.getItem('trueStore') as string).user_id;
  const isUserApp = userId === data?.ownerId;
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
  const handleDownload = () => {
    dispatch(openModal({ type: 'appLink', extra: data?.downloadLink }));
  };
  return (
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
          !data?.contractId ? (
            <Button
              variant="outline-light"
              className="mt-3"
              onClick={contractHandler}
              disabled={!data?.available}
            >
              {t('app.testing')}
            </Button>
          ) : (
            <Button variant="outline-light" className="mt-3" onClick={handleDownload}>
              {t('app.download')}
            </Button>
          )
        ) : null}
      </Col>
    </Row>
  );
};

export default AboutApp;

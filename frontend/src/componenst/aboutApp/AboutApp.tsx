import React, { FC } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useAppDispatch } from '../../hooks/defaultHooks';
import { App } from '../../models/services/app';
import { useSetContractMutation } from '../../store/api/contractsApiSlice/contractsApiSlice';
import { openModal } from '../../store/slices/modalSlice';
import { isFetchBaseQueryError } from '../../utils/helpers';
import ReportsSection from '../reportsSection';

interface AboutAppProps {
  data: Required<App> | undefined;
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
        await setContract({ appId: data.id }).unwrap();
      }
    } catch (error) {
      if (isFetchBaseQueryError(error)) {
        console.log(error);
      }
    }
  };
  const handleDownload = () => {
    dispatch(openModal({ type: 'appLink', extra: data?.downloadLink }));
  };
  const handleEdit = () => {
    dispatch(openModal({ type: 'editApp', extra: data }));
  };
  return (
    <Row>
      <Col xs={5} sm={4}>
        <img src={data?.iconImage} alt={data?.appName} className="img-fluid shadow rounded-5" />
      </Col>
      <Col xs={4} sm={6}>
        <h3 className="mb-3">{data?.appName}</h3>
        <p>
          {t('app.bugPrice')}: {t('app.cost', { count: data?.bugPrice })}
        </p>
        <p>
          {t('app.featurePrice')}: {t('app.cost', { count: data?.featurePrice })}
        </p>
        {!isLoading ? (
          !isUserApp ? (
            !data?.contractId ? (
              <Button className="mt-3" onClick={contractHandler} disabled={!data?.available}>
                {t('app.testing')}
              </Button>
            ) : (
              <Button className="mt-3" onClick={handleDownload}>
                {t('app.download')}
              </Button>
            )
          ) : (
            <Button onClick={handleEdit}>{t('app.editing')}</Button>
          )
        ) : null}
      </Col>
      {!isUserApp && data?.contractId && (
        <Col>
          <ReportsSection contractId={data?.contractId} />
        </Col>
      )}
    </Row>
  );
};

export default AboutApp;

import React, { FC } from 'react';
import { Col, Row } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';

const ReportsContainerHeader: FC = () => {
  const { t } = useTranslation();
  return (
    <Row className="w-100 border-bottom">
      <Col xs={4} sm={8}>
        <p>{t('reports.title')}</p>
      </Col>
      <Col xs={4} sm={2}>
        <p>{t('reports.type')}</p>
      </Col>
      <Col xs={4} sm={2}>
        <p>{t('reports.status')}</p>
      </Col>
    </Row>
  );
};

export default ReportsContainerHeader;

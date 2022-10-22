import React, { FC } from 'react';
import { useTranslation } from 'react-i18next';
import { Row, Col, Button } from 'react-bootstrap';

const UserBill: FC = () => {
  const { t } = useTranslation();
  return (
    <Row className="mt-5 border-bottom border-2 border-light">
      <Col>
        <h2>{t('profile.personalBilling')}</h2>
        <p>
          {t('profile.bill')}: {t('app.cost', { count: 500 })}
        </p>
        <Button variant="outline-light" className="mb-5">
          {t('profile.replenishment')}
        </Button>
      </Col>
    </Row>
  );
};

export default UserBill;

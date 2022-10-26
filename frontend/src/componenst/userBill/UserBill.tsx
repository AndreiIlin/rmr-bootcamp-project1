/* eslint-disable camelcase */
import React, { FC } from 'react';
import { useTranslation } from 'react-i18next';
import { Row, Col, Button } from 'react-bootstrap';
import { useGetBalanceQuery } from '../../store/api/moneyApiSlice/moneyApiSlice';
import { useAppDispatch } from '../../hooks/defaultHooks';
import { openModal } from '../../store/slices/modalSlice';

const UserBill: FC = () => {
  const { t } = useTranslation();
  const dispatch = useAppDispatch();
  const { data } = useGetBalanceQuery();
  const { user_id } = JSON.parse(localStorage.getItem('trueStore') ?? '');
  const handlerClick = () => {
    if (user_id) {
      dispatch(openModal({ type: 'addMoney', extra: user_id }));
    }
  };
  return (
    <Row className="mt-5 border-bottom border-2 border-light">
      <Col>
        <h2>{t('profile.personalBilling')}</h2>
        <p>
          {t('profile.bill')}: {t('app.cost', { count: data })}
        </p>
        <Button className="mb-5" onClick={handlerClick}>
          {t('profile.replenishment')}
        </Button>
      </Col>
    </Row>
  );
};

export default UserBill;

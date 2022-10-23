import React, { FC } from 'react';
import { Dropdown } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useAppDispatch } from '../../hooks/defaultHooks';
import { openModal } from '../../store/slices/modalSlice';

const ReportsSection: FC = () => {
  const { t } = useTranslation();
  const dispatch = useAppDispatch();
  const handleBugsReport = () => {
    dispatch(openModal({ type: 'suggestBug' }));
  };
  const handleFeatureReport = () => {
    dispatch(openModal({ type: 'suggestFeature' }));
  };
  return (
    <>
      <Dropdown>
        <Dropdown.Toggle variant="outline-light">{t('app.reports')}</Dropdown.Toggle>
        <Dropdown.Menu>
          <Dropdown.Item onClick={handleFeatureReport}>{t('app.suggestFeature')}</Dropdown.Item>
          <Dropdown.Item onClick={handleBugsReport}>{t('app.suggestBug')}</Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
    </>
  );
};

export default ReportsSection;

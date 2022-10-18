import React, { FC } from 'react';
import { Modal } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';

const AppRules: FC = () => {
  const { t } = useTranslation();
  return (
    <>
      <Modal.Header closeButton>
        <Modal.Title>{t('modals.storeRules.header')}</Modal.Title>
      </Modal.Header>
      <Modal.Body>{t('modals.storeRules.agreement')}</Modal.Body>
    </>
  );
};

export default AppRules;

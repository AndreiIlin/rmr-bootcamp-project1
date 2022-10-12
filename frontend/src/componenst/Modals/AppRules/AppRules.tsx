import React, { FC } from 'react';
import { Modal } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';

const AppRules: FC = () => {
  const { t } = useTranslation();
  return (
    <>
      <Modal.Header closeButton>
        <Modal.Title>{t('modals.appRules.header')}</Modal.Title>
      </Modal.Header>
      <Modal.Body>{t('modals.appRules.rules')}</Modal.Body>
    </>
  );
};

export default AppRules;

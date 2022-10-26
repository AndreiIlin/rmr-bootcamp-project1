import copy from 'copy-to-clipboard';
import React, { FC } from 'react';
import { Button, Form, InputGroup, Modal } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { IoCopyOutline } from 'react-icons/io5';
import { toast } from 'react-toastify';
import { useAppSelector } from '../../../hooks/defaultHooks';
import { modalExtra } from '../../../selectors/selectors';

const AppLink: FC = () => {
  const link = useAppSelector(modalExtra);
  const { t } = useTranslation();
  const handleCopy = () => {
    if (link) {
      copy(link);
      toast.success(t('toast.linkCopied'));
    }
  };
  return (
    <>
      <Modal.Header closeVariant={'dark'} closeButton>
        <Modal.Title>{t('modals.link')}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <InputGroup>
          <Form.Control type="text" value={link ?? ''} disabled />
          <Button onClick={handleCopy}>
            <IoCopyOutline />
          </Button>
        </InputGroup>
      </Modal.Body>
    </>
  );
};

export default AppLink;

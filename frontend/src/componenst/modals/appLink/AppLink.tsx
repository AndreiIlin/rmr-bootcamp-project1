import copy from 'copy-to-clipboard';
import React, { FC } from 'react';
import { Button, Form, InputGroup, Modal } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { IoCopyOutline } from 'react-icons/io5';
import { useAppSelector } from '../../../hooks/defaultHooks';
import { modalExtra } from '../../../selectors/selectors';

const AppLink: FC = () => {
  const link = useAppSelector(modalExtra);
  const { t } = useTranslation();
  const handleCopy = () => {
    if (link) {
      copy(link, { message: 'COPIED' });
      // toast.success(t('toast.linkCopied'));
    }
  };
  return (
    <>
      <Modal.Header className="bg-dark text-light" closeVariant={'white'} closeButton>
        <Modal.Title>{t('modals.link')}</Modal.Title>
      </Modal.Header>
      <Modal.Body className="bg-dark">
        <InputGroup>
          <Form.Control type="text" value={link ?? ''} disabled />
          <Button variant="dark" onClick={handleCopy}>
            <IoCopyOutline />
          </Button>
        </InputGroup>
      </Modal.Body>
    </>
  );
};

export default AppLink;
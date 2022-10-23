import { useFormik } from 'formik';
import React, { FC } from 'react';
import { Button, ButtonGroup, Form, Modal } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useAppDispatch } from '../../../hooks/defaultHooks';
import { closeModal } from '../../../store/slices/modalSlice';

const SuggestBug: FC = () => {
  const dispatch = useAppDispatch();
  const { t } = useTranslation();
  const handleClose = () => {
    dispatch(closeModal());
  };
  const initialValues = {
    bug: '',
  };
  const formik = useFormik({
    initialValues,
    validateOnBlur: false,
    onSubmit: (values) => {
      console.log(values);
      handleClose();
    },
  });
  return (
    <>
      <Modal.Header className="bg-dark text-light" closeVariant={'white'} closeButton>
        <Modal.Title>{t('modals.bugTitle')}</Modal.Title>
      </Modal.Header>
      <Modal.Body className="bg-dark">
        <Form onSubmit={formik.handleSubmit}>
          <Form.Control
            as="textarea"
            name="bug"
            style={{ resize: 'none' }}
            rows={4}
            id="bug"
            onChange={formik.handleChange}
            placeholder={t('modals.bugPlaceholder')}
            value={formik.values.bug}
          />
          <ButtonGroup className="mt-2 justify-content-end">
            <Button onClick={handleClose} variant="outline-danger">
              {t('modals.close')}
            </Button>
            <Button className="ms-2" variant="outline-light" type="submit">
              {t('modals.send')}
            </Button>
          </ButtonGroup>
        </Form>
      </Modal.Body>
    </>
  );
};

export default SuggestBug;

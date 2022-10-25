import { useFormik } from 'formik';
import React, { FC } from 'react';
import { Button, ButtonGroup, Form, Modal } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { toast } from 'react-toastify';
import * as yup from 'yup';
import { useAppDispatch, useAppSelector } from '../../../hooks/defaultHooks';
import { modalExtra } from '../../../selectors/selectors';
import {
  ReportType,
  useCreateReportMutation,
} from '../../../store/api/reportsApiSlice/reportsApiSlice';
import { closeModal } from '../../../store/slices/modalSlice';
import { isFetchBaseQueryError } from '../../../utils/helpers';

interface ReportsExtra {
  type: ReportType;
  header: string;
  contractId: string;
}

const AppReports: FC = () => {
  const [createReport] = useCreateReportMutation();
  const { type, header, contractId } = useAppSelector(modalExtra) as unknown as ReportsExtra;
  const dispatch = useAppDispatch();
  const { t } = useTranslation();
  const handleClose = () => {
    dispatch(closeModal());
  };
  const initialValues = {
    title: '',
    description: '',
  };
  const validationSchema = yup
    .object()
    .strict()
    .shape({
      title: yup
        .string()
        .min(3, t('formErrors.reportTitle'))
        .max(255, t('formErrors.reportTitle'))
        .required(t('formErrors.required')),
      description: yup
        .string()
        .min(3, t('formErrors.reportDescription'))
        .max(5000, t('formErrors.reportDescription'))
        .required(t('formErrors.required')),
    });
  const formik = useFormik({
    initialValues,
    validationSchema,
    validateOnBlur: false,
    onSubmit: async ({ title, description }) => {
      const report = { title, description, contractId };
      try {
        await createReport({ report, type }).unwrap();
        handleClose();
        toast.success(t('modals.successReport'));
      } catch (error) {
        if (isFetchBaseQueryError(error)) {
          console.log(error);
        }
      }
    },
  });
  return (
    <>
      <Modal.Header className="main-bg" closeVariant={'dark'} closeButton>
        <Modal.Title>{header}</Modal.Title>
      </Modal.Header>
      <Modal.Body className="bg-dark">
        <Form onSubmit={formik.handleSubmit}>
          <Form.Group className="position-relative">
            <Form.Control
              id="title"
              name="title"
              onChange={formik.handleChange}
              placeholder={t('modals.reportTitle')}
              value={formik.values.title}
              isInvalid={!!formik.errors.title}
            />
            <Form.Control.Feedback type="invalid" tooltip>
              {formik.errors.title}
            </Form.Control.Feedback>
          </Form.Group>
          <Form.Group className="position-relative my-3">
            <Form.Control
              as="textarea"
              name="description"
              style={{ resize: 'none' }}
              rows={4}
              id="description"
              onChange={formik.handleChange}
              placeholder={t('modals.reportDescription')}
              value={formik.values.description}
              isInvalid={!!formik.errors.description}
            />
            <Form.Control.Feedback type="invalid" tooltip>
              {formik.errors.description}
            </Form.Control.Feedback>
          </Form.Group>
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

export default AppReports;

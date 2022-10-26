import { useFormik } from 'formik';
import React, { FC } from 'react';
import { Button, ButtonGroup, Form } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import * as yup from 'yup';
import { useAppDispatch, useAppSelector } from '../../../../hooks/defaultHooks';
import { modalExtra } from '../../../../selectors/selectors';
import { Report, ReportType } from '../../../../store/api/reportsApiSlice/reportsApiSlice';
import { closeModal } from '../../../../store/slices/modalSlice';
import { ReportsExtra } from '../AppReports';
import useSubmitForm from './useSubmitForm';

interface ReportFormProps {
  isEditing: boolean;
  data: Report | undefined;
}

const ReportForm: FC<ReportFormProps> = ({ isEditing, data }) => {
  const { type, contractId } = useAppSelector(modalExtra) as unknown as ReportsExtra;
  const onSubmit = useSubmitForm(isEditing, type as ReportType);
  const dispatch = useAppDispatch();
  const { t } = useTranslation();
  const handleClose = () => {
    dispatch(closeModal());
  };
  const initialValues = data
    ? {
        title: data.title,
        description: data.description,
        reportId: data.id,
      }
    : ({
        title: '',
        description: '',
        contractId,
      } as Report);
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
    onSubmit,
  });
  return (
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
          {isEditing ? t('modals.change') : t('modals.send')}
        </Button>
      </ButtonGroup>
    </Form>
  );
};

export default ReportForm;
